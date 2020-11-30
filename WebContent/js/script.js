// preview recipe image 

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


// Javascript to toggle the menu

		document.getElementById('nav-toggle').onclick = function(){
			document.getElementById("nav-content").classList.toggle("hidden");
		}
