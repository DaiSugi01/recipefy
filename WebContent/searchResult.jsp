<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>

<%@ page import="recipe.SearchResult" %>
<%@ page import="recipe.RecipeDto" %>

<% 
	ArrayList<RecipeDto> searchedRecipeList = (ArrayList<RecipeDto>) session.getAttribute("searchedRecipes");
	session = request.getSession();
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
				<h1 class="mb-10 text-3xl font-bold">Search Result</h1>
				<div>
 					<% 
			   			if (searchedRecipeList != null && searchedRecipeList.size() > 0) {			   			
 							for (int i = 0; i < searchedRecipeList.size(); i++) {
								session.setAttribute("recipe", searchedRecipeList.get(i));
				   				String recipeNameWithComma = searchedRecipeList.get(i).getRecipeName().trim().replaceAll(" ", ",");
					%>

					   	<form action="searchDetail">
							<button class="mb-6">
						   		<input type="hidden" name="recipe_id" value=<%= searchedRecipeList.get(i).getRecipeId() %>>
								<div class="bg-white bg-opacity-30 rounded p-6 shadow-md hover:bg-opacity-40">
			 						<img src=<%= URL + recipeNameWithComma.toLowerCase() %> class="w-full rounded mb-3">
			 						<p><i>Recipe Name:</i> <%= searchedRecipeList.get(i).getRecipeName() %></p>
			 						<p><i>Category:</i> <%= searchedRecipeList.get(i).getRecipeCategory() %></p>
			 						<p><i>Time:</i> <%= searchedRecipeList.get(i).getTimeToCook() %></p>
								</div>
							</button>					   	
					   	</form>
						<% 
							} 
						%>

					<% } else { %>
						<p class="mt-8">Oops! We don't have any results.</p>						
					<% } %>
				</div>
			</div>
		</div>
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />
	
</body>
	
</html>