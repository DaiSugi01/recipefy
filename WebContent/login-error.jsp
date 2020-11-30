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
			<div class="text-center">
				<h1 class="mb-4 text-3xl font-bold">Error!</h1>
				<p>Your email and password do not match.</p>
				<button class="bg-transparent hover:bg-red-500 border rounded p-1 px-2 mt-2">
					<a href="login">Try login again</a>
				</button>
			</div>
		</div>
	</div>

	<jsp:include page="footer.jsp" flush="true" />
	
</body>
</html>