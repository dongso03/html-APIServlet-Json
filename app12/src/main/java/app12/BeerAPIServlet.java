package app12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static app12.ServletUtil.readBody;

// TODO: API 전역에서 request encoding 설정 필요
@WebServlet(name = "BeerAPIServlet"
	, urlPatterns =  { "/api/beer", "/api/beer/*" })
public class BeerAPIServlet extends HttpServlet {
	private IBeerService service = new BeerServiceMapperImpl();
	private JsonMapper mapper = new JsonMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String low = req.getParameter("low");
		String high = req.getParameter("high");
		if (low != null || high != null) {
			List<Beer> list = service.getBetween(low, high);
			
			String json = mapper.writeValueAsString(list);
			writeJson(json, resp);
		} else {
			getPage(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 사용자에게 맥주 정보(json 포맷)를 전달받아
		req.setCharacterEncoding("UTF-8");
		String body = readBody(req);
		
		Beer beer = mapper.readValue(body, Beer.class); // TODO: 유효하지 않은 JSON 포맷인 경우, 응답 전송
		
		// DB 행으로 추가
		int result = service.insert(beer);
		// 결과 응답 전송
		if (result == 1) {
			resp.setStatus(201);
			String json = createJsonString("생성되었습니다.");
			writeJson(json, resp);
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		// TODO: 올바른 id 형식의 경로가 아닌 경우 적절한 응답 필요
		if (pathInfo != null) {
			String[] split = pathInfo.split("/");
			if (split.length == 2) {
				String idStr = split[1];
				Integer id = Integer.valueOf(idStr);
				
				String body = ServletUtil.readBody(req);
				Beer beer = mapper.readValue(body, Beer.class); // TODO 맥주 변환 에러 가능성
				beer.setId(id);
				
				int result = service.update(beer);
				
				if (result == 1) {
					resp.setStatus(200);
					// TODO: 수정이 되었음을 알려줄 수 있는 응답 바디가 필요함
				}
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo != null) {
			String[] split = pathInfo.split("/");
			System.out.println(split.length); // TODO: 2가 아닌 경우 적절한 응답 필요
			
			if (split.length == 2) {
				String idStr = split[1]; // TODO: Integer가 아닌 경우 적절한 응답 필요
				Integer id = Integer.valueOf(idStr);
				int result = service.delete(id);
				
				if (result == 1) {
					resp.setStatus(204);
				}
			}
		} else {
			resp.sendError(400);
			String json = createJsonString("아이디 경로를 입력해주세요");
			writeJson(json, resp);
		}
	}

	private int parsingParam(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (RuntimeException e) {
			return defaultValue;
		}
	}
	
	private void writeJson(String json, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json; charset=utf-8");
		PrintWriter pw = resp.getWriter();
		pw.println(json);
		pw.flush();
	}
	
	private void getPage(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException, IOException {
		String pageStr = req.getParameter("page");
		String pagePerStr = req.getParameter("pagePer");
		
		int page = parsingParam(pageStr, 1);
		int pagePer = parsingParam(pagePerStr, 20);
		
		BeerDTO beerPage = service.getBeerPage(page, pagePer);
		
		String json;
		if (beerPage.getItems().size() > 0) {
			json = mapper.writeValueAsString(beerPage);
		} else {
			resp.setStatus(404);
			ObjectNode objectNode = mapper.createObjectNode();
			objectNode.put("message", "not found");
			json = mapper.writeValueAsString(objectNode);
		}
		writeJson(json, resp);
	}

	private String createJsonString(String stringMessage) throws JsonProcessingException {
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("message", stringMessage);
		String json = mapper.writeValueAsString(objectNode);
		return json;
	}


}








