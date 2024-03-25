package example.mapper;

import example.model.Books;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface BooksMapper {

	@Delete({ "delete from books", "where id = #{id,jdbcType=INTEGER}" })
	int deleteByPrimaryKey(Integer id);

	@Insert({ "insert into books (id, title, ", "authorId)",
			"values (#{id,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ", "#{authorid,jdbcType=INTEGER})" })
	int insert(Books row);

	@Select({ "select", "id, title, authorId", "from books", "where id = #{id,jdbcType=INTEGER}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
			@Result(column = "authorId", property = "authorid", jdbcType = JdbcType.INTEGER) })
	Books selectByPrimaryKey(Integer id);

	@Select({ "select", "id, title, authorId", "from books" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
			@Result(column = "authorId", property = "authorid", jdbcType = JdbcType.INTEGER) })
	List<Books> selectAll();

	@Update({ "update books", "set title = #{title,jdbcType=VARCHAR},", "authorId = #{authorid,jdbcType=INTEGER}",
			"where id = #{id,jdbcType=INTEGER}" })
	int updateByPrimaryKey(Books row);
}