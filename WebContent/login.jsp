<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />
	
	<div class="w-screen h-screen">
		<div class="w-full h-full flex justify-center items-center">
        	<form action="login" method="POST" class="w-3/4 md:w-1/3 xl:w-1/5">
	        	<h1 class="my-4 text-3xl font-bold">Login</h1>
			  	<label for="email" class="text-xl">Email</label><br>
			  	<input type="email" name="email" class="w-full text-gray-500 rounded p-1 my-2" required><br>
			  	<label for="password" class="text-xl">Password</label><br>
			  	<input type="password" minlength="8" name="password" class="w-full text-gray-500 rounded p-1 my-2" required>
			  	<input type="submit" value="Login" class="bg-transparent hover:bg-red-500 border rounded p-1 px-2 my-2"><br>
				<h3 class="my-2 text-lg"><a href="signup" class="hover:opacity-75">New member? <strong>Join Us Today!</strong></a></h3>
			</form>
         </div>
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />

</body>
</html>