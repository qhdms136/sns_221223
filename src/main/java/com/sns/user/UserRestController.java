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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@Tag(name="UserRestController")
@RestController
public class UserRestController {
	
	@Autowired
	private UserBO userBO;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping("/is_duplicated_id")
	@Operation(summary = "아이디 중복 체크", description = "loginId 값을 받아와 아이디 중복 체크 기능")
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
	
	@Operation(summary = "회원가입", description = "loginId, password, name email 값을 받아 저장하고 회원가입하는 기능")
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
	@Operation(summary = "로그인", description = "loginId, password 값을 받아 체크 후 로그인 기능")
	@PostMapping("/sign_in")
	public Map<String, Object> singIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session){
		
		// 비밀번호 암호화
		// Bcrypt는 단방향 해시 알고리즘이기 때문에 복호화가 불가능하다.
		
		Map<String, Object> result = new HashMap<>();
		User user = userBO.getUserByLoginId(loginId);
		
		if(user == null) {
			result.put("code", 501);
			result.put("errorMessage", "존재하지 않는 사용자 입니다.");
		} 
		if(user != null) {
			boolean	check = passwordEncoder.matches(password, user.getPassword());
			if(check == true) {
				result.put("code", 1);
				result.put("result","성공");
				String encodedPassword = passwordEncoder.encode(password);
				userBO.updateUserByPassword(loginId, encodedPassword);
				session.setAttribute("userLoginId", user.getLoginId());
				session.setAttribute("userName", user.getName());
				session.setAttribute("userId", user.getId());
			} else {
				result.put("code", 500);
				result.put("errorMessage", "비밀번호가 일치하지 않습니다.");
			}
		} 
		return result;
	}
}
