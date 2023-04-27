<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="d-flex justify-content-center">
	<div class="w-50 mt-5">
		<div class="profile-font">Profile</div>
		<div class="mt-3 d-flex">
			<c:if test="${empty user.userImagePath}">
				<img class="profile-img"src="/static/img/user/none-img.png">
			</c:if>
			<c:if test="${not empty user.userImagePath}">
				<img class="profile-img"src="${user.userImagepath}">
			</c:if>
			<div class="profile-input-box">
				<div class="my-3">
					<label for="name">Name</label>
					<input type="text" id="name"class="form-control" value="${user.name}">
				</div>
				<label for="email">email</label>
				<input type="text" id="email"class="form-control" value="${user.email}">
			</div>
		</div>
	</div>
</div>