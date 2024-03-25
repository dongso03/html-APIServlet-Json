package app12.person;

import java.util.Map;

import lombok.Getter;

@Getter
public class PersonAPIException extends RuntimeException {
	private int code;
	private Map<String, String> errors;
	
	public PersonAPIException(String message, int code, Map<String, String> errors) {
		super(message);
		this.code = code;
		this.errors = errors;
	}
}
