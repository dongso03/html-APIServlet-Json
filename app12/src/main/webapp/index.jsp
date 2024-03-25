<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>INDEX</title>
</head>
<body>
	<a href="${ pageContext.request.contextPath }/beer">맥주 목록보기</a>
	<c:url var="url" value="/beer"></c:url>
	<a href="${ url }">맥주 목록보기</a>
	<hr>
	<a href="person.html">person list service page</a>
</body>
</html>

