package app12.person;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import static app12.ServletUtil.*;

@WebServlet(name = "PersonAPIServlet",urlPatterns = "/api/person")
public class PersonAPIServlet extends HttpServlet {
	private PersonService service = new PersonService();
	private JsonMapper mapper = new JsonMapper();
	private PersonValidator validator = new PersonValidator();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nameParam = req.getParameter("name");
		
		if(nameParam != null) {
			Person p = service.getByPk(nameParam);
			if(p!=null) {
			sendJsonBody(mapper.writeValueAsString(p), resp);
			}else {
				sendNotFound(resp);
			}
		}else {
			getAll(resp);
		}
	}

	private void getAll(HttpServletResponse resp) throws JsonProcessingException, IOException {
		List<Person> list = service.getAll();
		String json = mapper .writeValueAsString(list);
		
		sendJsonBody(json, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = readBody(req);
		
		Person p = mapper.readValue(body, Person.class);
		Map<String, String> errors = validator.validate(p);
		
		if(errors.size() > 0) {
			//응답 구성
			throw new PersonAPIException("입력값 유효 확인 중 예외 발생", 400, errors);
		}
		
		int result = service.insert(p);
		if(result == 1) {
			resp.setStatus(201);
		}
	}
	
	
}
