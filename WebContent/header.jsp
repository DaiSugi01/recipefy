<%@page import="user.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("user");
%>
    
	<header class="w-full bg-gray-900 bg-opacity-50 p-4 fixed">
		<nav class="flex justify-between items-center px-4">
			<div class="flex">
				<select name="filter" class="text-gray-500 rounded p-1 mr-2">
					<option value="">--Search Options--</option>	
					<option value="ingredients">Ingredients</option>
					<option value="categories">Categories</option>
				</select>
				<form action="searchResult" class="flex">
					<input type="search" name="search" placeholder="Find a Recipe" class="w-full text-gray-500 rounded p-1 mr-2">
					<input type="submit" value="Search" class="bg-transparent hover:bg-red-500 border rounded p-1 px-2">
				</form>
			</div>
			
			<div class="flex">
				<% if (userDto != null) { %>
		    	<a class="mr-16 font-semibold hover:opacity-75" href="home">Home</a>
		        <a class="mr-16 font-semibold hover:opacity-75" href="addRecipe">Add Recipe</a>
			    <a class="font-semibold hover:opacity-75" href="logout">Logout</a>
				<% } else { %>	
		    	<a class="mr-16 font-semibold hover:opacity-75" href="home">Home</a>
		        <a class="mr-16 font-semibold hover:opacity-75" href="signup">Sign Up</a>
			    <a class="font-semibold hover:opacity-75" href="login">Login</a>
			    <% } %>
			</div>
		</nav>
	</header>