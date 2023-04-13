package com.sns.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sns.user.bo.UserBO;
import com.sns.user.model.User;

@RequestMapping("/user")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("/is_duplicated_id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId){
		Map<String, Object> result = new HashMap<>();
		
		// select
		User user = userBO.getUserByLoginId(loginId);
		if(user != null) {
			result.put("code", 1);
			result.put("result", true);
		} else {
			result.put("code", 500);
			result.put("result", false);
		}
		return result;
	}
	
	@PostMapping("sign_up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email){
		// 비밀번호 암호화
		// PasswordEncoder bcrypt 
		String encodedPassword = passwordEncoder.encode(password);
		
		Map<String, Object> result = new HashMap<>();
		
		// db insert
		int rowCount = userBO.addUser(loginId, encodedPassword, name, email);
		if(rowCount > 0) {
			result.put("code", 1);
			result.put("result", "성공");			
		} else {
			result.put("code", 500);
			result.put("errorMessage", "회원 가입 중 오류가 발생했습니다.");
		}
		return result;
	}
}
