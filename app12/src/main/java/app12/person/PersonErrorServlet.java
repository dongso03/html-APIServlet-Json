package app12.person;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@WebServlet("/personerror")
public class PersonErrorServlet extends HttpServlet {
	private JsonMapper mapper = new JsonMapper();
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PersonAPIException e = (PersonAPIException) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		
		resp.setStatus(e.getCode());
		resp.setContentType("application/json; charset=utf-8");
		ObjectNode obj = mapper.createObjectNode();
		obj.put("message", e.getMessage());
		
		if (e.getErrors() != null) {
			for (Entry<String,String> entry : e.getErrors().entrySet()) {
				obj.put(entry.getKey(), entry.getValue());
			}
		}
		
		PrintWriter pw = resp.getWriter();
		pw.print(obj.toString());
		pw.flush();
	}
}





