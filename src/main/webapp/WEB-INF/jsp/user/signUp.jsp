<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="sign-up-box">
		<h1 class="m-4 font-weight-bold">Sign Up</h1>
		<form id="signUpForm" action="/user/sign_up" method="post">
		<div class="d-flex">
			<div class="form-style1">
				<input type="text" id="loginId" name="loginId" autocomplete="off" required>
				<label for="loginId"><span>아이디</span></label>
			</div>
			<%-- 인풋 옆에 중복확인 버튼을 옆에 붙이기 위해 div 만들고 d-flex --%>
				<button type="button" id="loginIdCheckBtn" class="btn btn-success btn-xs">중복확인</button>
		</div>		
			<%-- 아이디 체크 결과 --%>
			<div class="hidden-box">
				<div id="idCheckLength" class="small text-danger d-none">ID를 4자 이상 입력해주세요.</div>
				<div id="idCheckDuplicated" class="small text-danger d-none">이미 사용중인 ID입니다.</div>
				<div id="idCheckOk" class="small text-success d-none">사용 가능한 ID 입니다.</div>
			</div>
			<div class="form-style1">
				<input type="password" id="password" name="password" autocomplete="off" required>
				<label for="password"><span>Password</span></label>
			</div>
			<div class="form-style1">
				<input type="password" id="confirmPassword" name="confirmPassword" autocomplete="off" required>
				<label for="confirmPassword"><span>Confirm password</span></label>
			</div>
			
			<div class="form-style1">
				<input type="text" id="name" name="name" autocomplete="off" required>
				<label for="name"><span>Name</span></label>
			</div>
			
			<div class="form-style1">
				<input type="text" id="email" name="email" autocomplete="off" required>
				<label for="email"><span>이메일</span></label>
			</div>
			<br>
			<div class="d-flex justify-content-center m-3">
				<button type="submit" id="signUpBtn" class="btn btn-info">가입하기</button>
			</div>
		</form>
	</div>
</div>

<script>
$(document).ready(function(){
	// 중복확인 버튼 클릭
	$('#loginIdCheckBtn').on('click',function(){
		// 경고 문구 초기화
		$('#idCheckLength').addClass("d-none");
		$('#idCheckDuplicated').addClass("d-none");
		$('#idCheckOk').addClass("d-none");
		
		let loginId = $('#loginId').val().trim();
		
		// 4자 미만 경고문
		if(loginId.length < 4){
			$('#idCheckLength').removeClass("d-none");
			return;
		}
		
		// ajax 통신
		$.ajax({
			//request
			url:"/user/is_duplicated_id"
			,data:{"loginId":loginId}
			
			//response
			,success:function(data){
				if(data.result){
					// 중복
					$('#idCheckDuplicated').removeClass("d-none");
				} else{
					$('#idCheckOk').removeClass("d-none");
				}
			}
			
		});
	});
	
	// 회원가입
	$('#signUpForm').on('submit', function(e){
		e.preventDefault();
		
		//validation
		let loginId = $('#loginId').val().trim();
		let password = $('#password').val();
		let confirmPassword = $('#confirmPassword').val();
		let name = $('#name').val().trim();
		let email = $('#email').val().trim();
		
		if(password != confirmPassword){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
		
		if($('#idCheckOk').hasClass("d-none")){
			alert("아이디 중복 확인을 다시 해주세요");
			return false;
		}
		
		// ajax
		let url = $(this).attr("action");
		console.log(url);
		let params = $(this).serialize();
		console.log(params);
		
		$.post(url, params)	//request
		.done(function(data){
			if(data.code == 1){
				alert("가입을 환영합니다! 로그인을 해주세요.");
				location.href="/user/sign_in_view";
			} else{
				alert(data.errorMessage);
			}
		});
	});
});
</script>