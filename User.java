// **** START MAIN CLASS **** \\
public class User {

	//Declare instance variables 
	//UserName holds the userName of the user (Used for login)
	private String username;
	//FirstName of the user (Used by GTerm to welcome them)
	private String firstname;
	//LastName of user (Used by GTerm to welcome then)
	private String lastname;
	//Administrator status (True or False)
	private Boolean admin;
	//User password (Used by login method in backEnd to login user)
	private String password;
	
	//***** --------------------------- Main Constructor Class ------------------------------ ******\\
	
	//The main constructor class receives all the data required by the user instance, this is loaded from a CSV or added when a user is created in the system
	public User(String username, String firstname, String lastname, String password, Boolean admin){
		//Set new the UserName to this.username
		this.username = username;
		//Set the new password to this.password
		this.password = password;
		//Set the new FirstName to this.firstname
		this.firstname = firstname;
		//Set the new lastTime to this.lastname
		this.lastname = lastname;
		//Set the administrator status (True or False)
		this.admin = admin;
			
	}
	
	//***** --------------------------- Object Data Getters & Setters ------------------------------ ******\\
	
	//getUsername method returns the string of the UserName (Used in the front end and backEnd for updating and creating tickets)
	public String getUsername() {
		//Returns this.username
		return this.username;
	}
	
	//getPassword method returns the current user password (used when logging in to the system, currently not encrypted)
	public String getPassword() {
		//returns this.password as a string
		return this.password;
	}
	//getFirstname method returns this.firstname (used by frontEnd)
	public String getFirstname() {
		//Returns this.firstname
		return this.firstname;
	}
	//getLastname method returns this.lastname (used by frontEnd)
	public String getLastname() {
		return this.lastname;
	}
	//getAdminStatus returns the true or false status of the user
	public Boolean getAdminStatus() {
		//returns True or False
		return this.admin;
	}
}

