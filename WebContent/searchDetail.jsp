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
		
	<div class="w-screen min-h-screen">
		<div class="w-full min-h-screen flex justify-center items-center">
			<div class="w-3/4 h-full lg:h-2/3 grid grid-cols-1 lg:grid-cols-2 bg-white bg-opacity-30 rounded shadow-md mt-32 mb-16 p-6">
				
				<!-- image column -->	
				<div class="flex justify-center items-center">
					<img src="getImage" class="w-full rounded">
				</div>
				<!-- description column -->
				<div class="flex justify-center items-center">
					<div class="p-6 text-lg">
						<h1 class="mb-8 text-3xl font-bold"><%= recipe.getRecipeName() %></h1>
						<p class="mb-1"><i>Created Date:</i> <%= recipe.getCreatedDate() %></p>
						<p class="mb-1"><i>Category:</i> <%= recipe.getRecipeCategory() %></p>
						<p class="mb-1"><i>Time:</i> <%= recipe.getTimeToCook() %> mins</p>
						<p><i>Ingredients:</i></p>
						<div class="ml-6 mb-1">
							<ul class="list-disc">
							<% for (IngredientsDto ing :ings) { 
								if(!ing.getIngName().isEmpty()) {
							%>
								<li><%= ing.getIngName() %></li>
								<% } %>
							<% } %>
							</ul>
						</div>
						<p><i>Directions:</i></p>
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
					</div>
				</div>	
			</div>
		</div>	
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />
	
</body>
</html>