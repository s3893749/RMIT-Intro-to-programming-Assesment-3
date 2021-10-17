//import java data utility, used for setting the date when the ticket is created
import java.util.Date;

//**** START MAIN CLASS ****\\
public class Ticket {

	//declare class variables 
	//ticket name stored as string
	private String name;
	//ticket description stored as string
	private String description;
	//ticket priority stored as integer
	private int priorty;
	//ticket user stored as string
	private String user;
	//ticket resolved stored as boolean value
	private Boolean resolved;
	//ticket user created data stored as string
	private String created;
	//ticket last modified user stored as string
	private String lastModified;
	
	//create constructor class and setup variables required for the creation of the object
	public Ticket(String name, String description, int priorty, Boolean resolved,String lastModified,String DataCreated,String user) {
		
		//Set constructor creation variables to instance object variables
		this.name = name;
		this.description = description;
		this.priorty = priorty;
		this.user = user;
		this.resolved = resolved;
		this.lastModified = lastModified;
		//before a current data is set check to see if the date is null, if so set the date
		if(created == null) {
			
		// Create the current Date instance needed to get the date for the time created time stamp
		Date currentDate = new Date();
		
		//set created to be the string of the currentDate
		this.created = String.valueOf(currentDate);
		
		//else set it to the inputed value, important when loading tickets from CSV
		}else {
			this.created = DataCreated;
		}
	}
	
	//***** --------------------------- Object Data Getters ------------------------------ ******\\
	
	//Get name getter, used when the backEnd or a frontEnd needs the name
	public String getName() {
		//return the name when called
		return this.name;
	}
	//Get user getter, used when the backEnd or a frontEnd needs the user
	public String getUser() {
		//return the user when called
		return this.user;
	}
	//Get description getter, used when the backEnd or a frontEnd needs the description
	public String getDescription() {
		//returns the description when called
		return this.description;
	}
	//Get priority getter, used when the backEnd or a frontEnd needs the priority value as an integer
	public int getPriorty() {
		//returns the priority value
		return this.priorty;
	}
	//Get last modified getter, used when the backEnd or a frontEnd needs the last modified value
	public String getLastModifiedDate() {
		//returns the last modified value
		return this.lastModified;
	}
	//Get created data value getter, used when the backEnd or a frontEnd needs the date the ticket was created
	public String getCreatedDate() {
		//returns the created date
		return this.created;
	}
	//Get resolved status getter, used when the backEnd or a frontEnd needs the resolved status boolean
	public Boolean getResolvedStatus() {
		//returns the true or false value that is the resolved status
		return this.resolved;
	}
	
	//***** --------------------------- Object Data Setters ------------------------------ ******\\
	
	//set name setter, used when updating tickets
	public void setName(String name) {
		//sets this.name to the new name
		this.name = name;
	}
	//set description setter, used when updating tickets
	public void setDescription(String description) {
		//sets this.description to the new value from the backEnd
		this.description = description;
	}
	//set Priority setter, used when updating tickets, gets value in String and then converts it to integer
	public void setPriorty(String priorty) {
		//gets new value as string and performs a value of conversion, integer checking is performed by the front end when the user enters the inital data, so we dont need to check that again here
		this.priorty = Integer.parseInt(priorty);
	}
	//set lastModified setter, used when updating tickets
	public void setLastModified(String user) {
		//sets the new lastModified value to the user value from the backEnd
		this.lastModified =  user;
	}
	//resolved status setters, get a called by backEnd to set the resolved status, receives status as Boolean
	public void setResolvedStatus(Boolean status) {
		//sets this.resolved to the new status
		this.resolved = status;
	}
}
