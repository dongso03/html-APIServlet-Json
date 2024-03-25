package app12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ServletUtil {
	private static JsonMapper mapper = new JsonMapper();
	
	public static void sendNotFound(HttpServletResponse resp) throws IOException {
		resp.setStatus(404);
		
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("code", 404);
		objectNode.put("message", "NOT FOUND");
		
		sendJsonBody(objectNode.toString(), resp);
	}
	
	public static String readBody(HttpServletRequest req) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader reader = req.getReader();) {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		}
		return sb.toString();
	}
	
	public static void sendJsonBody(String json, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.println(json);
		writer.flush();
	}
}
