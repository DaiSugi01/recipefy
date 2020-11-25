package directions;

import java.sql.Date;
import java.time.LocalDateTime;

public class DirectionsDto {

	private int dirId;
	private String direction;
	private Date createdDate;
	
	public DirectionsDto(int dirId, String direction, Date createdDate) {
		super();
		this.dirId = dirId;
		this.direction = direction;
		this.createdDate = createdDate;
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
