<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="user.UserDto"%>
<%
	UserDto userDto = (UserDto) session.getAttribute("user");
%>
	
    <header>
        <nav class="w-full flex flex-wrap items-center justify-between bg-gray-900 bg-opacity-50 p-4 fixed">
            <div class="flex items-center flex-shrink-0">
				<form action="searchResult" class="flex text-sm md:text-base">			
					<select name="filter" class="w-full text-gray-500 rounded p-1 mr-2">
						<option value="">--Search Options--</option>	
						<option value="recipeName">Recipe Name</option>
						<option value="ingredients">Ingredients</option>
					</select>
					<input type="search" name="search" placeholder="Find a Recipe" class="w-full text-gray-500 rounded p-1 mr-2">
					<input type="submit" value="Search" class="bg-transparent hover:bg-red-500 border rounded p-1 px-2">
				</form>
            </div>
    
            <div class="block lg:hidden">
                <button id="nav-toggle" class="flex items-center px-3 py-2 border rounded hover:bg-red-500">
                    <svg class="fill-current h-4 w-4" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    	<title>Menu</title><path d="M0 3h20v2H0V3zm0 6h20v2H0V9zm0 6h20v2H0v-2z"/>
                    </svg>
                </button>
            </div>
    
            <div class="w-full flex-grow lg:flex lg:items-center lg:w-auto hidden lg:block pt-4 lg:pt-0" id="nav-content">
                <ul class="list-reset lg:flex justify-end flex-1 items-center">
                	<% if (userDto != null) { %>
                    <li class="mr-3">
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="home">Home</a>
                    </li>
                    <li class="mr-3">
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="addRecipe">Add Recipe</a>
                    </li>
                    <li>
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="logout">Logout</a>
                    </li>
               		 <% } else { %>	
                    <li class="mr-3">
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="home">Home</a>
                    </li>
                    <li class="mr-3">
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="signup">Sign Up</a>
                    </li>
                    <li>
                        <a class="inline-block font-semibold hover:opacity-75 py-2 px-4" href="login">Login</a>
                    </li>
                    <% } %>
                </ul>
            </div>
        </nav>
    </header>
    
	<!-- JavaScript to toggle the menu -->
	<script>
		document.getElementById('nav-toggle').onclick = function(){
			document.getElementById("nav-content").classList.toggle("hidden");
		}
	</script>
	