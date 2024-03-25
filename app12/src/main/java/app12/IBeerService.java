package app12;

import java.util.List;

public interface IBeerService {

	BeerDTO getBeerPage(int page, int pagePer);

	int insert(Beer beer);

	int delete(Integer id);

	int update(Beer beer);

	List<Beer> getBetween(String low, String high);
}