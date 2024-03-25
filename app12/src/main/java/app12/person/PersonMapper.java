package app12.person;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonMapper {
	@Select("SELECT name, age FROM person")
	@Results(id = "personResult",
		value = {
				@Result(column = "name", property = "name", id = true)
				, @Result(column = "age", property = "age")
		}
	)
	List<Person> getAll();

	@Select("SELECT name, age FROM person WHERE name = #{name}")
	@ResultMap("personResult")
	Person getByPK(String name);
	
	@Insert("INSERT INTO person (name, age) VALUES (#{name}, #{age})")
	int insert(Person p);
}





