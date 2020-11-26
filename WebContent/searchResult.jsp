<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="recipe.RecipeDto" %>

<% 
	ArrayList<RecipeDto> searchedRecipeList = (ArrayList<RecipeDto>) request.getAttribute("searchedRecipes");
%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
		
	<div class="w-screen">
		<div class="w-full flex justify-center items-center">
			<div class="w-1/2 text-center mt-24">
				<h1 class="my-4 text-3xl font-bold">Search Result</h1>
				<div>
 					<% 
			   			if (searchedRecipeList != null && searchedRecipeList.size() > 0) {			   			
							for (RecipeDto recipe : searchedRecipeList) { 
					%>
					<div class="bg-white bg-opacity-30 rounded p-6 mb-6">
						<img src="https://source.unsplash.com/1200x800/?dish" class="w-full rounded mb-3">
						<p>Recipe Name: <%= recipe.getRecipeName() %></p>
						<p>Recipe Name: <%= recipe.getRecipeName() %></p>
						<p>Recipe Name: <%= recipe.getRecipeName() %></p>
						<%-- <p>User Name: <%= recipe.getUserName() %></p> --%>
					</div>				
					<% } %>
					<% } else { %>
						<p>Sorry, we have 0 result.</p>						
					<% } %>
				</div>
			</div>
		</div>
	</div>
	
</body>
	
</html>