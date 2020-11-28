<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="recipe.RecipeDto" %>
<%@ page import="directions.DirectionsDto" %>
<%@ page import="ingredients.IngredientsDto" %>

<% 
	RecipeDto recipe = (RecipeDto) session.getAttribute("recipe");
	ArrayList<IngredientsDto> ings = (ArrayList<IngredientsDto>) session.getAttribute("ings");
	ArrayList<DirectionsDto> dires = (ArrayList<DirectionsDto>) session.getAttribute("dires");
%>


<!DOCTYPE html>
<html>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
		
	<div class="w-screen h-screen">
		<div class="w-full h-full flex justify-center items-center">
			<div class="w-2/3 h-2/3 grid grid-cols-2 bg-white bg-opacity-30 rounded shadow-md mt-20 p-6">
				
				<!-- image column -->	
				<div class="h-full bg-cover bg-center bg-no-repeat rounded" style="background-image: url(getImage)">
				</div>
				<!-- description column -->
				<div class="flex justify-center items-center">
					<div class="p-6">
						<% if (recipe != null) { %>
							<h1 class="mb-8 text-3xl font-bold"><%= recipe.getRecipeName() %></h1>
							<p>Created Date: <%= recipe.getCreatedDate() %></p>
							<p>Category: <%= recipe.getRecipeCategory() %></p>
							<p>Time: <%= recipe.getTimeToCook() %> mins</p>
	
							<p>Ingredients:</p>
							<div class="ml-6">
								<ul class="list-disc">
								<% for (IngredientsDto ing :ings) { 
									if(!ing.getIngName().isEmpty()) {
								%>
									<li><%= ing.getIngName() %></li>
									<% } %>
								<% } %>
								</ul>
							</div>
							
							<p>Directions:</p>
							<div class="ml-6">
								<ul class="list-decimal">
								<% for (DirectionsDto dire :dires) {
									if(!dire.getDirection().isEmpty()) {
								%>
									<li><%= dire.getDirection() %></li>
									<% } %>
								<% } %>
								</ul>
							</div>
						<% } else {  %>	
							<p>Sorry. something wrong</p>
						<% } %>
					</div>
				</div>
				
			</div>
		</div>	
	</div>

</body>
</html>