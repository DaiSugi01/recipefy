<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="recipe.RecipeDto" %>

<% 
	ArrayList<RecipeDto> searchedRecipeList = (ArrayList<RecipeDto>) request.getAttribute("searchedRecipes");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/reset.css">	
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/header.css">	
		
	</head>
	
	<body>

 		<jsp:include page="header.jsp" flush="true" />
		
		<main class="wrapper">
			<div class="inner">

				<div class="searched-recipe-box">			
			   		<% for (RecipeDto recipe : searchedRecipeList) { %>
						<div class="recipe">
							<p><%= recipe.getRecipeId() %></p>
							<p><%= recipe.getRecipeName() %></p>
							<p><%= recipe.getUserId() %></p>
						</div>
					<% } %>
				</div>
			</div>

		</main> 		
	</body>
	
</html>