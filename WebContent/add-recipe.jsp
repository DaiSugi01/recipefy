<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<jsp:include page="common-title.jsp" flush="true" />
	<jsp:include page="header.jsp" flush="true" />

	<div class="w-screen min-h-screen">
		<div class="w-full min-h-screen flex justify-center items-center">
        	<form action="addRecipe" method="POST" class="w-3/4 lg:w-1/3 my-24" enctype="multipart/form-data">
	        	<h1 class="my-4 text-3xl font-bold">Add Your Recipe</h1>
			  	<label for="recipeName" class="text-xl">Recipe Name</label><br>
			  	<input type="text" name="recipeName" class="w-full text-gray-500 rounded p-1 my-2" required><br>	
			  	<label for="recipeImage" class="text-xl">Image</label><br>
			  	<input type="file" name="recipeImage" id="example" multiple class="w-full my-2" required>
				<div id="preview" class="w-1/3 rounded my-2"></div>
			  	<label for="categories" class="text-xl">Category</label><br>
			  	<select name="categories" class="w-full text-gray-500 rounded p-1 my-2" required>
					<option value="">--Please choose an option--</option>
					<option value="American">American</option>
					<option value="Canadian">Canadian</option>
					<option value="Japanese">Japanese</option>
					<option value="Korean">Korean</option>
					<option value="Mexican">Mexican</option>
					<option value="Others">Others</option>
				</select>
			  	<label for="ingredients" class="text-xl">Ingredients</label><br>
			  	<div class="flex justify-between">
				  	<input type="text" name="ingredients1" class="w-1/4 text-gray-500 rounded p-1 my-2" required>
				  	<input type="text" name="ingredients2" class="w-1/4 text-gray-500 rounded p-1 my-2">
				  	<input type="text" name="ingredients3" class="w-1/4 text-gray-500 rounded p-1 my-2">			  	
			  	</div>
			  	<label for="directions" class="text-xl">Directions</label><br>
			  	<div>
				  	<textarea name="directions1" rows="1" class="w-full text-gray-500 rounded p-1 my-2" required></textarea>		  	
				  	<textarea name="directions2" rows="1" class="w-full text-gray-500 rounded p-1 my-2"></textarea>		  	
				  	<textarea name="directions3" rows="1" class="w-full text-gray-500 rounded p-1 my-2"></textarea>		  	
			  	</div>
			  	<label for="times" class="text-xl">Time</label><br>
			  	<select name="times" class="w-full text-gray-500 rounded p-1 my-2" required>
					<option value="">--Please choose an option--</option>
					<option value="0 - 30 ">0 - 30 mins</option>
					<option value="30 - 60">30 - 60 mins</option>
					<option value="more than 60">more than 60 mins</option>
				</select>
			  	<input type="submit" value="Add Recipe" class="bg-transparent hover:bg-red-500 border rounded p-1 px-2 my-2"><br>
			</form>
         </div>
	</div>
	
	<jsp:include page="footer.jsp" flush="true" />
	
	<script>
		/* preview recipe image */ 
		function previewFile(file) {
			  const preview = document.getElementById('preview');

			  const reader = new FileReader();

			  reader.onload = function (e) {
			    const imageUrl = e.target.result;
			    const img = document.createElement("img");
			    img.src = imageUrl;
			    preview.appendChild(img);
			  }

			  reader.readAsDataURL(file);
			}

			const fileInput = document.getElementById('example');
			const handleFileSelect = () => {
			  const files = fileInput.files;
			  for (let i = 0; i < files.length; i++) {
			    previewFile(files[i]);
			  }
			}
			fileInput.addEventListener('change', handleFileSelect);
	</script>
	
</body>
</html>