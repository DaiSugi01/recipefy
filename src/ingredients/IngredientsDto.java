package ingredients;

import java.sql.Date;

public class IngredientsDto {

	private int ingId;
	private String ingName;
	private Date createdDate;
	
	public IngredientsDto(int ingId, String ingName, Date createdDate) {		
		setIngId(ingId);
		setIngName(ingName);
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
	 * @return the ingId
	 */
	public int getIngId() {
		return ingId;
	}
	/**
	 * @param ingId the ingId to set
	 */
	public void setIngId(int ingId) {
		this.ingId = ingId;
	}
	/**
	 * @return the ingName
	 */
	public String getIngName() {
		return ingName;
	}
	/**
	 * @param ingName the ingName to set
	 */
	public void setIngName(String ingName) {
		this.ingName = ingName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof IngredientsDto) {
			IngredientsDto other = (IngredientsDto) obj;
			//変数ageと変数nameが等しければtrueを返す。(同じ値を持っているとみなす)
			return other.ingName.equals(this.ingName);
		}
		return false;
	}
	
	
}
