package app12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app12.listener.MyWebContextListener;

public class BeerDAO implements IBeerDAO {
	@Override
	public int countAll() {
		String sql = "SELECT COUNT(*) AS CNT FROM beer";
		
		try (Connection conn = MyWebContextListener.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			if (rs.next()) {
				return rs.getInt("CNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	@Override
	public List<Beer> getAll(int limit, int offset) {
		String sql = "SELECT * FROM beer LIMIT ? OFFSET ?";
		List<Beer> list = new ArrayList<>();
		
		try (Connection conn = MyWebContextListener.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				) {
			stmt.setInt(1, limit);
			stmt.setInt(2, offset);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					list.add( resultMapping(rs) );
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Beer> getAll() {
		String sql = "SELECT * FROM beer";
		List<Beer> list = new ArrayList<>();
		
		try (Connection conn = MyWebContextListener.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				list.add( resultMapping(rs) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Beer resultMapping(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		int price = rs.getInt("price");
		
		return new Beer(id, name, price);
	}

	@Override
	public int insert(Beer beer) {
		String sql = "INSERT INTO beer (name, price) VALUES (?, ?)";
		try (Connection conn = MyWebContextListener.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, beer.getName());
			stmt.setInt(2, beer.getPrice());
			
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(Integer id) {
		String sql = "DELETE FROM beer WHERE id = ?";
		try (Connection conn = MyWebContextListener.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int update(Beer beer) {
		String sql = "UPDATE beer SET name = ?, price = ? WHERE id = ?";
		try (Connection conn = MyWebContextListener.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, beer.getName());
			stmt.setInt(2, beer.getPrice());
			stmt.setInt(3, beer.getId());
			
			return stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}




