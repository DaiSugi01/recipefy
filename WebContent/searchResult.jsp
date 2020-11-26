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
		
	<div class="w-screen">
		<div class="w-full flex justify-center items-center">
			<div class="w-1/2 text-center mt-24">
				<h1 class="mt-4 mb-8 text-3xl font-bold">Search Result</h1>
				<div>
 					<% 
			   			if (searchedRecipeList != null && searchedRecipeList.size() > 0) {			   			
 							for (int i = 0; i < searchedRecipeList.size(); i++) {
								System.out.println("JSP run");
								session.setAttribute("recipe", searchedRecipeList.get(i));
				   				String recipeNameWithComma = searchedRecipeList.get(i).getRecipeName().trim().replaceAll(" ", ",");
					%>

<%-- 				   	<a href="searchDetail.jsp">
						<div class="bg-white bg-opacity-30 rounded p-6 mb-6 shadow-md hover:bg-opacity-40">
							<img src="https://source.unsplash.com/1200x800/?dish" class="w-full rounded mb-3">
							<p>Recipe Name: <%= recipe.getRecipeName() %></p>
							<p>Category: <%= recipe.getRecipeCategory() %></p>
							<p>Time: <%= recipe.getTimeToCook() %> mins</p>
						</div>
					</a> --%>
								
<%-- 					<% } %> --%>

						<a href=<%="searchDetail/" + searchedRecipeList.get(i).getRecipeId() %>>
							<div class="bg-white bg-opacity-30 rounded p-6 mb-6">
		 						<!-- <img src="getImage" alt="image" class="w-full rounded mb-3"> -->
		 						<img src=<%= URL + recipeNameWithComma.toLowerCase() %> class="w-full rounded mb-3">
		 						<!-- <img src="https://source.unsplash.com/1200x800/?dish" class="w-full rounded mb-3"> -->
		 						<p>Recipe Name: <%= searchedRecipeList.get(i).getRecipeName() %></p>
		 						<p>Recipe Category: <%= searchedRecipeList.get(i).getRecipeCategory() %></p>
		 						<p>Recipe : <%= searchedRecipeList.get(i).getTimeToCook() %></p>
							</div>
						</a>
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
	
</body>
	
</html>