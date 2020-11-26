package directions;

import java.sql.Date;
import java.time.LocalDateTime;

public class DirectionsDto {

	private int dirId;
	private String direction;
	private int recipeId;
	private Date createdDate;
	
	public DirectionsDto(int dirId, String direction, int recipeId, Date createdDate) {
		setDirId(dirId);
		setDirection(direction);
		setRecipeId(recipeId);
		setCreatedDate(createdDate);
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
	public int getDirId() {
		return dirId;
	}

	/**
	 * @param dirId the dirId to set
	 */
	public void setDirId(int dirId) {
		this.dirId = dirId;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
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
	
	
	
	
}
