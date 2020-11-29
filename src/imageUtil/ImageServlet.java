package imageUtil;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import recipe.RecipeDto;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/getImage")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("********** [ImageSevlet-doGet] start **********");

		HttpSession session = request.getSession();
		
		if (session.getAttribute("recipe") != null) {			
			RecipeDto recipeDto = (RecipeDto) session.getAttribute("recipe");
			
			InputStream inputStream = new ByteArrayInputStream(recipeDto.getRecipeImage());
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
			BufferedImage img = ImageIO.read(bufferedInputStream);
			
			response.setContentType("image/png");
			OutputStream outputStream = response.getOutputStream();
			ImageIO.write(img, "png", outputStream);
			outputStream.flush();

			session.removeAttribute("recipe");
			
		} else {
			System.out.println("[ImageSErvlet] Session(recipe) is null");
		}

		System.out.println("********** [ImageSevlet-doGet] finish **********");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
