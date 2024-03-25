package app12;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(servletNames = {"BeerAPIServlet", "PersonAPIServlet"})
public class CORSFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		
		resp.setHeader("Access-Control-Allow-Origin", "*"); // ex) "127.0.0.1:5500"
		resp.setHeader("Access-Control-Allow-Methods", "*"); // "POST, GET, OPTIONS ..."
		resp.setHeader("Access-Control-Allow-Headers", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		
		chain.doFilter(request, response);
	}
}







