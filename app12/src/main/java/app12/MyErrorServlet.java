package app12;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;

@WebServlet("/myerrorpage")
public class MyErrorServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("예외 처리 서블릿입니다.");
	
		Exception e = (Exception)req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		System.out.println(e.getMessage());
		
		if( e instanceof JsonParseException) {
			//분기설정해서 ..
		}

		resp.setStatus(400);
		resp.setContentType("text/plain; charset= utf-8");
		PrintWriter pw = resp.getWriter();
		pw.println("잘못된 Json 형식 입니다. 다시 확인해 주세요");
		pw.flush();
		
		
	}
	
}
