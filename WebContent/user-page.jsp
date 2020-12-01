<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
		
	<div class="w-screen min-h-screen">
		<div class="w-full min-h-screen flex justify-center items-center">
			<div class="w-3/4 h-full lg:h-2/3 grid grid-cols-1 lg:grid-cols-2 bg-white bg-opacity-30 rounded shadow-md mt-32 mb-16 p-6">
				
				<!-- image column -->	
				<div class="flex justify-center items-center p-16">
					<img src="https://source.unsplash.com/500x500/?people" class="w-full rounded-full">
				</div>
				<!-- description column -->
				<div class="flex justify-center items-center">
					<div class="p-6 text-lg">
						<h1 class="mb-8 text-3xl font-bold">Hello, User Name</h1>
						<p class="mb-1"><i>User ID</i></p>
						<p class="mb-1"><i>User Email</i></p>
						<p class="mb-1"><i>User Created Date</i></p>
						<p class="mb-1"><i>Your Recipes</i></p>
						<p><i>Favorite Recipes</i></p>
					</div>
				</div>	
			</div>
		</div>	
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />
	
</body>
</html>