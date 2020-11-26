package ingredients;

import java.sql.Date;

public class RecipeIngredientsDto {

	private int id;
	private int recipeId;
	private int ingId;
	private Date createdDate;
	
	public RecipeIngredientsDto(int id, int recipeId, int ingId, Date createdDate) {
		setId(id);
		setRecipeId(recipeId);
		setIngId(ingId);
		setCreatedDate(createdDate);
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the dirId
	 */
	public int getIngId() {
		return ingId;
	}
	/**
	 * @param dirId the dirId to set
	 */
	public void setIngId(int ingId) {
		this.ingId = ingId;
	}
	
	
}
