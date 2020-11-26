<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css" rel="stylesheet">
<title>Insert title here</title>

</head>

<body class="bg-cover bg-center bg-no-repeat text-white" style="background-image: url(/java-group-project/img/top.jpg)">

<!-- 	<header class="w-full flex justify-center items-center bg-gray-900 bg-opacity-50 p-4 fixed">
		<nav class="flex">
	    	<a class="mr-16 font-semibold" href="index.html">Home</a>
	        <a class="mr-16 font-semibold" href="signup.html">Sign Up</a>
		    <a class="font-semibold" href="login.html">Login</a>
		</nav>
	</header> -->
		
	<div class="w-screen h-screen bg-black bg-opacity-25">
		<div class="w-full h-full flex justify-center items-center">
        	<form action="addRecipe" method="POST" class="w-1/3" enctype="multipart/form-data">
	        	<h1 class="my-4 text-3xl font-bold">Add Your Recipe</h1>
			  	<label for="recipeName" class="text-xl">Recipe Name</label><br>
			  	<input type="text" name="recipeName" class="w-full text-gray-500 rounded p-1 my-2"><br>	
			  	<label for="recipeImage" class="text-xl">Image</label><br>
			  	<input type="file" name="recipeImage" id="example" multiple class="w-full my-2">
				<div id="preview" class="w-1/3 rounded my-2"></div>
			  	<label for="categories" class="text-xl">Category</label><br>
			  	<select name="categories" class="w-full text-gray-500 rounded p-1 my-2">
					<option value="">--Please choose an option--</option>
					<option value="category">Canadian</option>
					<option value="category">Japanese</option>
					<option value="category">Mexican</option>
				</select>
			  	<label for="ingredients" class="text-xl">Ingredients</label><br>
			  	<div class="flex justify-between">
				  	<input type="text" name="ingredients1" class="w-1/4 text-gray-500 rounded p-1 my-2">
				  	<input type="text" name="ingredients2" class="w-1/4 text-gray-500 rounded p-1 my-2">
				  	<input type="text" name="ingredients3" class="w-1/4 text-gray-500 rounded p-1 my-2">			  	
			  	</div>
			  	<label for="directions" class="text-xl">Directions</label><br>
			  	<div class="flex">
				  	<textarea name="directions" rows="2" class="w-full text-gray-500 rounded p-1 my-2"></textarea>		  	
			  	</div>
			  	<label for="times" class="text-xl">Time</label><br>
			  	<select name="times" class="w-full text-gray-500 rounded p-1 my-2">
					<option value="">--Please choose an option--</option>
					<option value="time">0 - 30 min</option>
					<option value="time">30 - 60 min</option>
					<option value="time">more than 60 min</option>
				</select>
			  	<input type="submit" value="Add Recipe" class="bg-transparent hover:bg-red-500 border rounded p-1 px-2 my-2"><br>
			</form>
         </div>
	</div>
	
	<script>
	function previewFile(file) {
		  // プレビュー画像を追加する要素
		  const preview = document.getElementById('preview');

		  // FileReaderオブジェクトを作成
		  const reader = new FileReader();

		  // ファイルが読み込まれたときに実行する
		  reader.onload = function (e) {
		    const imageUrl = e.target.result; // 画像のURLはevent.target.resultで呼び出せる
		    const img = document.createElement("img"); // img要素を作成
		    img.src = imageUrl; // 画像のURLをimg要素にセット
		    preview.appendChild(img); // #previewの中に追加
		  }

		  // いざファイルを読み込む
		  reader.readAsDataURL(file);
		}

		// <input>でファイルが選択されたときの処理
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