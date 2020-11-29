package recipe;

import java.sql.Date;

public class RecipeDto {

	private int recipeId;
	private String recipeName;
	private byte[] recipeImage;
	private String recipeCategory;
	private String timeToCook;
	private int userId;
	private Date updatedDate;
	private Date createdDate;
	
	public RecipeDto(int recipeId, String recipeName, byte[] recipeImage ,
			String recipeCategory, String timeToCook, int userId, Date updatedDate, Date createdDate) {
		setRecipeId(recipeId);
		setRecipeName(recipeName);
		setRecipeImage(recipeImage);
		setRecipeCategory(recipeCategory);
		setTimeToCook(timeToCook);
		setUserId(userId);
		setCreatedDate(createdDate);
	}

	
	/**
	 * @return the recipeCategory
	 */
	public String getRecipeCategory() {
		return recipeCategory;
	}

	/**
	 * @param recipeCategory the recipeCategory to set
	 */
	public void setRecipeCategory(String recipeCategory) {
		this.recipeCategory = recipeCategory;
	}

	/**
	 * @return the timeToCook
	 */
	public String getTimeToCook() {
		return timeToCook;
	}

	/**
	 * @param timeToCook the timeToCook to set
	 */
	public void setTimeToCook(String timeToCook) {
		this.timeToCook = timeToCook;
	}

	/**
	 * @return the image
	 */
	public byte[] getRecipeImage() {
		return recipeImage;
	}

	/**
	 * @param image the image to set
	 */
	public void setRecipeImage(byte[] recipeImage) {
		this.recipeImage = recipeImage;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
