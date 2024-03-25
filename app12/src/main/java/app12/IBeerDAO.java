package app12;

import java.util.List;

public interface IBeerDAO {

	int countAll();

	List<Beer> getAll(int limit, int offset);

	List<Beer> getAll();

	int insert(Beer beer);

	int delete(Integer id);

	int update(Beer beer);

}