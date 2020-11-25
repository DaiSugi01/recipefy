<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="recipe.RecipeDto" %>

<!DOCTYPE html>
<html>

	<% 
		ArrayList<RecipeDto> list = (ArrayList<RecipeDto>) request.getAttribute("recipes");
	%>

	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/reset.css">	
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/header.css">	
		<link rel="stylesheet" type="text/css" href="/java-group-project/css/index.css">	
	</head>
	<body>
 		<jsp:include page="header.jsp" flush="true" />

		<main class="wrapper">
			<div class="inner">

				<h2 class="latest-recipes-title">Latest Recipes</h2>			
				<div class="latest-recipe-box">			
			   		<% for (RecipeDto recipe : list) { %>
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
