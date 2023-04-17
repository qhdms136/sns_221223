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
		<div class="timeline-box my-5">
			<%-- 카드1 --%>
			<div class="card border rounded mt-3">
				<%-- 글쓴이, 더보기(삭제) --%>
				<div class="p-2 d-flex justify-content-between">
					<span class="font-weight-bold">사용자</span>

					<%-- 더보기(내가 쓴 글일 때만 노출) --%>
					<a href="#" class="more-btn">
						<img src="/static/img/timeline/more-icon.png" width="30">
					</a>
				</div>

				<%-- 카드 이미지 --%>
				<div class="card-img">
					<img src="https://cdn.pixabay.com/photo/2023/04/03/09/27/flowers-7896390_960_720.jpg" class="w-100" alt="본문 이미지">
				</div>

				<%-- 좋아요 --%>
				<div class="card-like m-3">
					<a href="#" class="like-btn" >
						<img src="https://www.iconninja.com/files/527/809/128/heart-icon.png" width="18px" height="18px" alt="filled heart">
					</a>
					좋아요 10개
				</div>

				<%-- 글 --%>
				<div class="card-post m-3">
					<span class="font-weight-bold">사용자</span>
					<span>내용</span>
				</div>

				<%-- 댓글 --%>
				<div class="card-comment-desc border-bottom">
					<div class="ml-3 mb-1 font-weight-bold">댓글</div>
				</div>

				<%-- 댓글 목록 --%>
				<div class="card-comment-list m-2">

					<%-- 댓글 내용 --%>
					<div class="card-comment m-1">
						<span class="font-weight-bold">댓글사용자:</span>
						<span>댓글내용</span>

						<%-- 댓글 삭제 버튼 --%>
						<a href="#" class="commentDelBtn">
							<img src="https://www.iconninja.com/files/603/22/506/x-icon.png" width="10px" height="10px">
						</a>
					</div>

					<%-- 댓글 쓰기 --%>
					<div class="comment-write d-flex border-top mt-2">
						<input type="text" class="form-control border-0 mr-2 comment-input" placeholder="댓글 달기"/> 
						<button type="button" class="comment-btn btn btn-light">게시</button>
					</div>
				</div>
				<%--// 댓글 목록 끝 --%>
			</div>
			<%--// 카드1 끝 --%>
		</div>
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
		let writeTextArea = $('#writeTextArea').val().trim();
		let file = $('#file').val();
		
		// 글내용, 이미지, 확장자 체크
		// alert(file);
		if(file != ""){
			let ext = file.split(".").pop().toLowerCase();
			if($.inArray(ext, ['jpg', 'jpeg', 'png', 'gif']) == -1){
				alert("이미지 파일만 업로드 할 수 있습니다.");
				$('#file').val("");	// 파일을 비운다.
				return;
			}
		}
		
		
		// AJAX 전송
		let formData = new FormData();
		formData.append("writeTextArea", writeTextArea);
		formData.append("file", $('#file')[0].files[0]);
		
		$.ajax({
			type:"POST"
			, url:""
		});
	});
});
</script>