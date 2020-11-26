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
<head>
<meta charset="UTF-8">
	<jsp:include page="common-title.jsp" flush="true" />
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
	</div>
</body>
</html>