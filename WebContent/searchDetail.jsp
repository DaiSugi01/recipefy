<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="recipe.RecipeDto" %> 
<%@ page import="ingredients.IngredientsDto" %> 
<%@ page import="directions.DirectionsDto" %> 
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="recipe.SearchResult" %>

<% 
	ArrayList<RecipeDto> searchedRecipeList = (ArrayList<RecipeDto>) request.getAttribute("searchedRecipes");
	ArrayList<IngredientsDto> searchedIngList = (ArrayList<IngredientsDto>) request.getAttribute("searchedRecipes");
	ArrayList<DirectionsDto> searchedDirList = (ArrayList<DirectionsDto>) request.getAttribute("searchedRecipes");
	OutputStream outputStream = (OutputStream)request.getAttribute("outputStream");
%>
 
<!DOCTYPE html>
<html>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
		
	<div class="w-screen h-screen">
		<div class="w-full h-full flex justify-center items-center">
			<div class="w-full h-2/3 grid grid-cols-2 bg-white bg-opacity-30 rounded shadow-md m-12 mt-20 p-6">
			
 				<% 
			   		if (searchedRecipeList != null && searchedRecipeList.size() > 0) {			   			
						for (RecipeDto recipe : searchedRecipeList) { 
				%>
				
				<!-- image column -->	
				<div class="flex items-center">
					<img src="https://source.unsplash.com/1000x700/?dish" class="rounded"  width="1000" height="700">
				</div>
				<!-- description column -->
				<div class="flex justify-center items-center">
					<h1 class="mt-4 mb-8 text-3xl font-bold"><%= recipe.getRecipeName() %></h1>
					<div class="p-6 mb-6">
						<p>User: <%= recipe.getUserId() %></p>
						<p>Created Date: <%= recipe.getCreatedDate() %></p>
						<p>Category: <%= recipe.getRecipeCategory() %></p>
						<p>Time: <%= recipe.getTimeToCook() %> mins</p>
						<p>Ingredients: <%= ingredients.getIngName() %></p>
						<p>Directions: <%= directions.getDirection() %> mins</p>
					</div>
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