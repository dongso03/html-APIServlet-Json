package app12;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BeerDTO { // Data Transfer Object
	private int currentPage;
	private int itemsPerPage;
	private int totalPages;
	private List<Beer> items;
}
