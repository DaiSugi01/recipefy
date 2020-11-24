<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<html>

	<% 
		ArrayList<String> list = (ArrayList<String>) request.getAttribute("recipes");
	%>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/reset.css">	
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/index.css">	
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/header.css">	
	</head>
	<body>
 		<jsp:include page="header.jsp" flush="true" />

   		<% for (String recipeName : list) { %>
			<p><%= recipeName %></p>
		<% } %>
		
	</body>
</html>
