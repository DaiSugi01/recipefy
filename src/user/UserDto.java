package user;

public class UserDto {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	
	//constructor
	
	public UserDto(int id, String firstName, String lastName, String email, String password) {
		setId(id);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setPassword(password);
	}
	
	
	//getter
	//setter
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	/*
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/*
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/*
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/*
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/*
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	/*
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/*
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/*
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
