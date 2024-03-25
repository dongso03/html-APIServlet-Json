package app12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import app12.listener.MyWebContextListener;

public class BeerServiceMapperImpl implements IBeerService {
	@Override
	public BeerDTO getBeerPage(int page, int pagePer) {
		try (SqlSession sqlSession = MyWebContextListener.getSqlSession();) {
			BeerMapper mapper = sqlSession.getMapper(BeerMapper.class);
			int totalCount = mapper.countAll();
			int totalPage = totalCount / pagePer;
			totalPage += totalCount % pagePer == 0 ? 0 : 1;
			
			Map<String, Integer> params = new HashMap<>();
			params.put("limit", pagePer);
			params.put("offset", (page - 1) * pagePer);
			
			List<Beer> all = mapper.getPage(params);
			
			BeerDTO dto = BeerDTO.builder()
					.totalPages(totalPage)
					.currentPage(page)
					.itemsPerPage(pagePer)
					.items(all)
					.build();
			
			return dto;
		}
	}

	@Override
	public int insert(Beer beer) {
		try (SqlSession session = MyWebContextListener.getSqlSession()) {
			BeerMapper mapper = session.getMapper(BeerMapper.class);
			int result = mapper.insert(beer);
			
			session.commit();
			return result;
		}
	}

	@Override
	public int delete(Integer id) {
		try (SqlSession session = MyWebContextListener.getSqlSession()) {
			BeerMapper mapper = session.getMapper(BeerMapper.class);
			Beer beer = mapper.getById(id);
			
			if (beer != null) {
				int result = mapper.delete(id);
				session.commit();
				return result;
			}
		}
		return 0;
	}

	@Override
	public int update(Beer beer) {
		try (SqlSession session = MyWebContextListener.getSqlSession()) {
			BeerMapper mapper = session.getMapper(BeerMapper.class);
			Beer beerRow = mapper.getById(beer.getId());
			
			if (beerRow != null) {
				int result = mapper.update(beer);
				session.commit();
				return result;
			}
		}
		return 0;
	}

	@Override
	public List<Beer> getBetween(String low, String high) {
		try (SqlSession session = MyWebContextListener.getSqlSession()) {
			BeerMapper mapper = session.getMapper(BeerMapper.class);
			
			Integer lowInteger = parseOrNull(low);
			Integer highInteger = parseOrNull(high);
			
			return mapper.getBetween(lowInteger, highInteger);
		}
	}
	
	private Integer parseOrNull(String value) {
		if (value == null) {
			return null;
		}
		
		return Integer.valueOf(value);
	}
}








