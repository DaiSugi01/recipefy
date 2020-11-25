package recipe;

public class RecipeDto {

	private int recipeId;
	private String recipeName;
	private int userId;
	
	public RecipeDto(Integer recipeId, String recipeName, int userId) {
		setRecipeId(recipeId);
		setRecipeName(recipeName);
		setUserId(userId);
	}

	/**
	 * @return the recipeId
	 */
	public int getRecipeId() {
		return recipeId;
	}

	/**
	 * @param recipeId the recipeId to set
	 */
	public void setRecipeId(int recipeId) {
		this.recipeId = recipeId;
	}

	/**
	 * @return the recipeName
	 */
	public String getRecipeName() {
		return recipeName;
	}

	/**
	 * @param recipeName the recipeName to set
	 */
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
