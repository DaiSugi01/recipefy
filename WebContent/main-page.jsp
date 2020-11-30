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
	
	<div class="w-screen min-h-screen">
		<div class="w-full min-h-screen flex justify-center items-center">
			<div class="w-3/4 lg:w-1/2 text-center mt-32 mb-16">
				<h1 class="text-6xl font-bold">Welcome to Recipefy.</h1>
				<% if (userDto == null) { %>
					<button class="bg-transparent hover:bg-red-500 text-2xl border rounded py-2 px-4 mt-10">
						<a href="signup">Join Us Today!</a>
					</button>
				<% } %>
			
				<div>
					<h1 class="mt-8 mb-10 text-3xl font-bold">Latest Recipes</h1>
			   		<%
			   			if (latestRecipeList != null && latestRecipeList.size() > 0) {			   			
				   			for (RecipeDto recipe : latestRecipeList) {
				   				String recipeNameWithComma = recipe.getRecipeName().trim().replaceAll(" ", ",");
				   	%>
							   	<form action="searchDetail">
							   		<button class="mb-6">
								   		<input type="hidden" name="recipe_id" value=<%= recipe.getRecipeId() %>>
										<div class="bg-white bg-opacity-30 rounded p-6 shadow-md hover:bg-opacity-40">
				 							<img src=<%= URL + recipeNameWithComma.toLowerCase() %> class="w-full rounded mb-3">
											<p><i>Recipe Name:</i> <%= recipe.getRecipeName() %></p>
											<p><i>Category:</i> <%= recipe.getRecipeCategory() %></p>
											<p><i>Time:</i> <%= recipe.getTimeToCook() %> mins</p>
										</div>
									</button>
							   	</form>
							<% } %>
					<% } else { %>
						<p>We don't have any recipes yet.</p>						
					<% } %>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />
	
</body>

</html>
