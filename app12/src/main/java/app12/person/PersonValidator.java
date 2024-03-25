package app12.person;

import java.util.HashMap;
import java.util.Map;

public class PersonValidator {
	public Map<String, String> validate(Person p) {
		Map<String, String> errors = new HashMap<>();
		
		checkName(p.getName(), errors);
		
		checkAge(p.getAge(), errors);
		
		return errors;
	}

	private void checkAge(int age, Map<String, String> errors) {
		if (age < 0) {
			errors.put("ageNegative", "나이 값은 0 이상이여야 합니다.");
		}
	}

	private void checkName(String name, Map<String, String> errors) {
		if (name == null || name.trim().length() == 0) {
			errors.put("idEmpty", "아이디 값이 필요합니다.");
			return;
		}
		
		if (name.length() > 15) {
			errors.put("idTooLong", "이름은 15자를 초과할 수 없습니다.");
		}
		
		if (name.contains(" ")) {
			errors.put("idSpace", "이름은 공백을 포함할 수 없습니다.");
		}
		
		// ... 등등등
	}
	
}





