<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@page import="user.UserDto"%>
<%@ page import="recipe.RecipeDto" %>

<% 
	ArrayList<RecipeDto> latestRecipeList = (ArrayList<RecipeDto>) request.getAttribute("recipes");
	UserDto userDto = (UserDto) session.getAttribute("user");
	String URL = "https://source.unsplash.com/1200x800/?";
%>


<!DOCTYPE html>
<html>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
		
	<div class="w-screen">
		<div class="w-full flex justify-center items-center">
			<div class="w-1/2 text-center my-24">
				<h1 class="text-6xl font-bold">Welcome to Recipefy.</h1>
				<% if (userDto == null) { %>
					<button class="bg-transparent hover:bg-red-500 text-xl border rounded p-1 px-2">
						<a href="signup">Join Us Today!</a>
					</button>
				<% } %>		
			
				<div>
					<h1 class="mt-4 mb-8 text-3xl font-bold">Latest Recipes</h1>
			   		<%
			   			if (latestRecipeList != null && latestRecipeList.size() > 0) {			   			
				   			for (RecipeDto recipe : latestRecipeList) {
				   				String recipeNameWithComma = recipe.getRecipeName().trim().replaceAll(" ", ",");
				   	%>
							   	<form action="searchDetail">
							   		<button>
								   		<input type="hidden" name="recipe_id" value=<%= recipe.getRecipeId() %>>
										<div class="bg-white bg-opacity-30 rounded p-6 mb-6 shadow-md hover:bg-opacity-40">
											<!-- <img src="https://source.unsplash.com/1200x800/?thai,curry" class="w-full rounded mb-3"> -->
				 							<img src=<%= URL + recipeNameWithComma.toLowerCase() %> class="w-full rounded mb-3">
											<p>Recipe Name: <%= recipe.getRecipeName() %></p>
											<p>Category: <%= recipe.getRecipeCategory() %></p>
											<p>Time: <%= recipe.getTimeToCook() %> mins</p>
										</div>
									</button>
							   	</form>
							<% } %>
					<% } else { %>
						<p class="my-8">We don't have any recipes yet.</p>						
					<% } %>
				</div>
			</div>
		</div>
	</div>
	
</body>

</html>
