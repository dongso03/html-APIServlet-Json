package app12.person;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import app12.listener.MyWebContextListener;

public class PersonService {
	public List<Person> getAll() {
		try (SqlSession sqlSession = MyWebContextListener.getSqlSession()) {
			PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
			
			return personMapper.getAll();
		}
	}

	public Person getByPk(String name) {
		try (SqlSession sqlSession = MyWebContextListener.getSqlSession()) {
			PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
			
			return personMapper.getByPK(name);
		}
	}
	
	public int insert(Person p) {
		try (SqlSession sqlSession = MyWebContextListener.getSqlSession()) {
			PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
			
			Person name = personMapper.getByPK(p.getName());
			if (name != null) {
				throw new PersonAPIException("중복된 아이디", 409, null);
			}
			
			int result = personMapper.insert(p);
			
			sqlSession.commit();
			return result;
		}
	}
}
