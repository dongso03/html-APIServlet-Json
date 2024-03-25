package app12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/beer")
public class BeerServlet extends HttpServlet {
	private int parsingParam(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IBeerService service = new BeerService(); // TODO: 생성 방법 및 life-cycle 고민하기

		String pageStr = req.getParameter("page");
		String pagePerStr = req.getParameter("pagePer");
		
		int page = parsingParam(pageStr, 1);
		int pagePer = parsingParam(pagePerStr, 20);
		
		BeerDTO dto = service.getBeerPage(page, pagePer);
		
		req.setAttribute("dto", dto);
		req.getRequestDispatcher("/WEB-INF/beerlist.jsp")
			.forward(req, resp);
	}
}






