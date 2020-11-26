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
			<div class="w-full h-2/3 grid grid-cols-2 bg-white bg-opacity-30 rounded shadow-md m-12 mt-20 p-6">
				
				<!-- image column -->	
				<div class="flex items-center">
					<img src="getImage" alt="image" class="w-full rounded" width="1000" height="700">
<!-- 					<img src="https://source.unsplash.com/1000x700/?dish" class="rounded"  width="1000" height="700"> -->
				</div>
				<!-- description column -->
				<div class="flex justify-center items-center">
					<div class="p-6">
						<h1 class="mt-4 mb-8 text-3xl font-bold"><%= recipe.getRecipeName() %></h1>
						<p>User: <%= recipe.getUserId() %></p>
						<p>Created Date: <%= recipe.getCreatedDate() %></p>
						<p>Category: <%= recipe.getRecipeCategory() %></p>
						<p>Time: <%= recipe.getTimeToCook() %> mins</p>
						<% for (IngredientsDto ing :ings) { 
							if(!ing.getIngName().isEmpty()) {
						%>
								<p>Ingredients: <%= ing.getIngName() %></p>
							<% } %>
						<% } %>
						
						<% for (DirectionsDto dire :dires) { %>
							<p>Directions: <%= dire.getDirection() %></p>
						<% } %>
						
					</div>
				</div>
				
			</div>
		</div>	
	</div>
	

<%-- 	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />

	<div class="w-screen">
		<div class="w-full flex justify-center items-center">
			<div class="w-1/2 text-center mt-24">
					<% if (recipe != null) { %>
						<div class="bg-white bg-opacity-30 rounded p-6 mb-6">
							<img src="getImage" alt="image" class="w-full rounded mb-3">
							<p><%= recipe.getRecipeName() %></p>
							<p><%= recipe.getRecipeCategory() %></p>
							<p><%= recipe.getCreatedDate() %></p>
							<p><%= recipe.getTimeToCook() %></p>
							
							<p>---------------Ingredients----------------</p>
							<c:foreach var="ing" items="${ings} ">
								<c:out value="${ing.name}"/>
							</c:foreach>

							<p>---------------Directions----------------</p>
							<c:foreach var="dir" items="${dires} ">
								<c:out value="${dir.name}"/>
							</c:foreach>
						</div>
					<% } %>
			</div>
		</div>
	</div> --%>

</body>
</html>