<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header class="header">

	<div class="header-inner">
		<div class="header-top">
			<div class="filter-container">
				<select class="filter-items" name="filter">
					<option value="ingredients">Ingredients</option>
					<option value="category">Category</option>
				</select>
			</div>
			
			<form class="form">
				<input class="search-box" type="search" placeholder="Find a Recipe">
				<input class="submit-button" type="submit" value="Search">
			</form>
		</div>
	
		<nav class="nav">
			<ul class="nav-items">
			<% if (false) { %>
				<li class="nav-item">
					<a class="nav-link" href="#">Add Recipe</a>
				</li>				
				<li class="nav-item">
					<a class="nav-link" href="#">Logout</a>
				</li>
			<% } else { %>	
				<li class="nav-item login">
					<a class="nav-link login" href="#">Login</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">Sign up</a>
				</li>
			<% } %>
			
			</ul>
		</nav>
	</div>
</header>