<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="d-flex justify-content-center">
	<div class="contents-box">
		<%-- 글쓰기 영역: 로그인 된 상태에서만 보여짐 --%>
		<c:if test="${not empty userId}">
		<div class="write-box border rounded m-3">
			<textarea id="writeTextArea" placeholder="내용을 입력해주세요" class="w-100 border-0"></textarea>

			<%-- 이미지 업로드를 위한 아이콘과 업로드 버튼을 한 행에 멀리 떨어뜨리기 위한 div --%>
			<div class="d-flex justify-content-between">
				<div class="file-upload d-flex">
					<%-- file 태그는 숨겨두고 이미지를 클릭하면 file 태그를 클릭한 것처럼 이벤트를 줄 것이다. --%>
					<input type="file" id="file" class="d-none" accept=".gif, .jpg, .png, .jpeg">
					<%-- 이미지에 마우스 올리면 마우스커서가 링크 커서가 변하도록 a 태그 사용 --%>
					<a href="#" id="fileUploadBtn"><img width="35" src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-512.png"></a>

					<%-- 업로드 된 임시 파일 이름 저장될 곳 --%>
					<div id="fileName" class="ml-2">
					</div>
				</div>
				<button id="writeBtn" class="btn btn-info">게시</button>
			</div>
		</div>
		</c:if>
		<%--// 글쓰기 영역 끝 --%>

		<%-- 타임라인 영역 --%>
		<c:forEach items="${cardList}" var="card">
		<div class="timeline-box my-5">
			<%-- 카드1 --%>
			<div class="card border rounded mt-3">
				<%-- 글쓴이, 더보기(삭제) --%>
				<div class="p-2 d-flex justify-content-between">
					<span class="font-weight-bold">${card.user.name}</span>

					<%-- 더보기(내가 쓴 글일 때만 노출) --%>
					<c:if test="${userId eq card.post.userId}">
					<a href="#" class="more-btn">
						<img src="/static/img/timeline/more-icon.png" width="30">
					</a>
					</c:if>
				</div>

				<%-- 카드 이미지 --%>
				<div class="card-img">
					<img src="${card.post.imagePath}" class="w-100" alt="본문 이미지">
				</div>

				<%-- 좋아요 --%>
				<div class="card-like m-3">
					<%-- 좋아요가 눌려있지 않을 때 (비워진 하트) --%>
					<c:if test="${card.filledLike eq false}">
					<a href="#" class="like-btn" data-post-id="${card.post.id}">
						<img src="/static/img/timeline/heart-icon.png" width="18px" height="18px" alt="filled heart">
					</a>
					</c:if>
					<%-- 좋아요가 눌려졌을 때 (채워진 하트) --%>
					<c:if test="${card.filledLike}">
					<a href="#" class="like-btn" data-post-id="${card.post.id}">
						<img src="https://www.iconninja.com/files/527/809/128/heart-icon.png" width="18px" height="18px" alt="filled heart">
					</a>
					</c:if>
					좋아요 ${card.likeCount}개
				</div>

				<%-- 글 --%>
				<div class="card-post m-3">
					<span class="font-weight-bold">${card.user.loginId}</span>
					<span>${card.post.content}</span>
				</div>

				<%-- 댓글 --%>
				<div class="card-comment-desc border-bottom">
					<div class="ml-3 mb-1 font-weight-bold">댓글</div>
				</div>

				<%-- 댓글 목록 --%>
				<div class="card-comment-list m-2">
				<c:forEach items="${card.commentList}" var="comments">
					<%-- 댓글 내용 --%>
					<div class="card-comment m-1">
						<c:if test="${card.post.id eq comments.comment.postId}">
						<span class="font-weight-bold">${comments.user.name} : </span>
						<span>${comments.comment.content}</span>
						
						<%-- 댓글 삭제 버튼 --%>
							<c:if test="${not empty comments.comment.content and (userId eq comments.comment.userId)}">
							<a href="#" class="commentDelBtn" data-comment-id="${comments.comment.id}">
								<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10px" height="10px">
							</a>
							</c:if>
						</c:if>
					</div>
				</c:forEach>
					<%-- 댓글 쓰기 --%>
					<%-- 로그인 상태에서만 나오게 --%>
					<c:if test="${not empty userId}">
					<div class="comment-write d-flex border-top mt-2">
						<input type="text" class="form-control border-0 mr-2 comment-input" placeholder="댓글 달기"/>
						<button type="button" class="comment-btn btn btn-light" data-post-id="${card.post.id}">게시</button>
					</div>
					</c:if>
				</div>
				<%--// 댓글 목록 끝 --%>
			</div>
			<%--// 카드1 끝 --%>
		</div>
		</c:forEach>
		<%--// 타임라인 영역 끝  --%>
	</div>
</div>

<script>
$(document).ready(function(){
	// 파일 업로드 이미지 클릭 => 숨겨져있는 file 태그를 동작 시킨다.
	$('#fileUploadBtn').on('click', function(e){
		e.preventDefault(); // a 태그의 올라가는 현상 방지
		$('#file').click();	// input file을 클릭한 것과 같은 효과
	});
	
	// 사용자가 이미지를 선택했을 때 유효성 확인 및 업로드 된 파일명 노출
	$('#file').on('change', function(e){
		let fileName = e.target.files[0].name;	// sig2.png
		console.log(fileName);
		
		// 확장자 유효성 확인
		let ext = fileName.split(".").pop().toLowerCase();
		if (ext != "jpg" && ext != "png" && ext != "jpeg" && ext != "gif"){
			alert("이미지 파일만 업로드 할 수 있습니다.");
			$("#file").val(""); // 파일 태그에 파일 제거
			$('#fileName').text("");	// 파일 이름 비우기
			return;
		}
		
		// 유효성 통과한 이미지는 상자에 업로드 된 파일 이름 노출
		$('#fileName').text(fileName);
	});
	
	// 글쓰기
	$('#writeBtn').on('click', function(){
		
		// validation
		let content = $('#writeTextArea').val();
		if (content.length < 1) {
			alert("글 내용을 입력해주세요");
			return;
		}
		
		let file = $('#file').val();
		if (file == '') {
			alert('파일을 업로드 해주세요');
			return;
		}
		
		// 파일이 업로드 된 경우 확장자 체크
		let ext = file.split('.').pop().toLowerCase(); // 파일 경로를 .으로 나누고 확장자가 있는 마지막 문자열을 가져온 후 모두 소문자로 변경
		if ($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
			alert("gif, png, jpg, jpeg 파일만 업로드 할 수 있습니다.");
			$('#file').val(''); // 파일을 비운다.
			return;
		}
		
		// 폼태그를 자바스크립트에서 만든다.
		let formData = new FormData();
		formData.append("content", content);
		formData.append("file", $('#file')[0].files[0]); // $('#file')[0]은 첫번째 input file 태그를 의미, files[0]는 업로드된 첫번째 파일
		
		// AJAX form 데이터 전송
		$.ajax({
			type: "post"
			, url: "/post/create"
			, data: formData
			, enctype: "multipart/form-data"    // 파일 업로드를 위한 필수 설정
			, processData: false    // 파일 업로드를 위한 필수 설정
			, contentType: false    // 파일 업로드를 위한 필수 설정
			, success: function(data) {
				if (data.code == 1) {
					location.reload();
				} else if (data.code == 500) { // 비로그인 일 때
					location.href = "/user/sign_in_view";
				} else {
					alert(data.errorMessage);
				}
			}
			, error: function(e) {
				alert("글 저장에 실패했습니다. 관리자에게 문의해주세요.");
			}
		});  // --- ajax 끝
	});
	
	// 댓글 게시 버튼 클릭
	$('.comment-btn').on('click',function(){
		let postId = $(this).data("post-id");
		// alert(postId);
		
		// 1) 댓글 내용 가져오기
		/* let comment = $(this).prev().val();
		alert(comment); */
		
		// 2) 댓글 내용 가져오기
		let content = $(this).siblings('input').val().trim();
		//alert(comment);
		console.log(content);
		console.log(postId);
		if(content == ''){
			alert("댓글을 입력해주세요");
			return;
		}
		
		// ajax
		$.ajax({
				type:"POST"
				,url:"/comment/create"
				,data:{"postId":postId, "content":content}
			,success:function(data){
				if(data.code == 1){
					location.reload();	// 새로고침
				} else if(data.code == 500){
					alert("로그인을 해주세요.");
					location.href="/user/sign_in_view";
				}
			}
			,error:function(jqXHR, textStatus, errorThrown) {
				//  jqXHR.status는 http 오류 번호를 반환하며 케이스별 오류 메시지 판정에 사용하면 유용
				// errorThrown : "Inter Server Error", "Not Found" 등의 HTTP 오류 메시지가 출력
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		});
	});
	
	// 좋아요 버튼 클릭
	$('.like-btn').on('click', function(e){
		e.preventDefault(); // a 태그의 올라가는 현상 방지
		
		let postId = $(this).data("post-id");
		// alert(postId);
		
		$.ajax({
			// request
			url:"/like/" + postId
		
			// response
			,success:function(data){
				if(data.code == 1){
					location.reload();
				}else if(data.code == 300){
					// 비로그인
					alert("로그인을 해주세요.");
					location.href="/user/sign_in_view";
				} 
				else{
					alert(data.errorMasseage);
				}
			}
			,error:function(jqXHR, textStatus, errorThrown){
				var errorMsg = jqXHR.responseJSON.status;
				alert(errorMsg + ":" + textStatus);
			}
		});
	});
	
	// 댓글 삭제 버튼
	$('.commentDelBtn').on('click', function(e){
		e.preventDefault();	// // // a 태그의 올라가는 현상 방지
		
		let commentId = $(this).data("comment-id");
		alert(commentId);
		
		$.ajax({
			// request
			url:"/comment/delete/" + commentId
			
			// response
			,success:function(data){
				if(data.code == 1){
					location.reload();
				} else if (data.code == 300){
					// 비로그인
					alert(data.errorMessage);
					location.href="/user/sign_in_view";
				}
			}
			,error:function(request, status, error){
				alert("댓글 삭제 중 오류가 발생했습니다.");
			}
		});
		
	});
});
</script>