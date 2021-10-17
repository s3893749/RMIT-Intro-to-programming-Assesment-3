//Java Package Imports (Used to add extra functions)

//Buffered Reader adds core functionality to our input buffer when reading from CSV files
import java.io.BufferedReader;
//Buffered Writer adds core functionality to our written files by creating a buffer to use before processing writing.
import java.io.BufferedWriter;
//File not found exception is thrown if a file can't be found and as such isn't auto created
import java.io.FileNotFoundException;
//file reader us used to read CSV files (used in congestion with Buffered Reader)
import java.io.FileReader;
//File Writer is used to write files and is used in conjunction with the Print Writer and Buffered Writer
import java.io.FileWriter;
//IO exceptions are thrown by methods called in the back-end, this provides functionality for it
import java.io.IOException;
//Print writer is used in conjunction with FIle Writer and Buffered writer to provide CSV file creation functionality.
import java.io.PrintWriter;

//**START MAIN CLASS**\\
public class BackEnd {
	
	//***** --------------------------- Class Wide Variable Declarations ------------------------------ ******\\
	//User array used to load users in from CSV for storage when the program is running
	private User[] user;
	//UserMaxCurrent integer is a numerical value that indicates the amount of users currently created in the system
	private int userCurrentMax;
	//Ticket object array, used to store ticket objects when they are created or loaded from CSV
	private Ticket[] ticket;
	//CurrentTicketMax, used to keep track of the current tickets in the 
	private int ticketCurrentMax;
	
	
	//***** --------------------------- BackEnd Constructor Class ------------------------------ ******\\
	
	public BackEnd() {
		
		//Create the ticket array with a starting size of 1 tickets (2 tickets as 0 is the first - 0 & 1)
		this.ticket = new Ticket[1];
		//set the current ticket count at 0 to indicate no tickets exist on the system
		this.ticketCurrentMax =0;
		//load all tickets from CSV storage file
		this.loadTickets("tickets.csv");
		
		// Creates User Array with starting size of 1 users (2 Users as 0 is the first - 0 & 1)
		this.user = new User[10];
		// Sets the current number of users to 0 indicating 0 users exist on the system.
		this.userCurrentMax = 0;
		//load the users from the user.csv
		this.loadUsers("users.csv");
	}
	
	//***** --------------------------- Getters & Setters ------------------------------ ******\\
	
	//Main get tickets function, this requires the user requesting the tickets to perform a user check for administrator privileges
	public Ticket[] getTickets(User user) {
		
		//create the return array of tickets being the same size as the current ticket array length
		Ticket[] returnArray = new Ticket[ticket.length];
		
		//check if the user administrator status is true, if so return the whole ticket array
		if(user.getAdminStatus()) {
			
		//set the return array to the current main ticket array.
		returnArray = this.ticket;
		
		//else if the user is not an administrator then process this code to retrieve only the tickets created by the normal user
		}else{
			
			// create counting variables, i & j, i will be to count up the main this.tickets array & j will could up the new array 
			int i =0;
			int j =0;
			
			//while loop to check all the records in this.tickets main array
			while(i < this.ticketCurrentMax) {
				
				//inside the while loop check if the current ticket.getuser matches the user that is logged in on the front end, is so then add it to the array
				if(this.ticket[i].getUser().matches(user.getUsername())) {
					//if the user matches then add it to the array with the j counter and increase  j by 1.
					returnArray[j] = this.ticket[i];
					j++;
				}
				
				//now the if statement check has processed for that ticket increase i and move onto the next
				i++;
			}
		}
		//once that check is done the while loop will end and the code will proceed to return the array
		return returnArray;
		
	}
	
	//Main get tickets current max function, this requires the user requesting the tickets to perform a user check for administrator privileges
	public Integer getTicketCurrentMax(User user) {
		
		//create a internal return variable with a default value of 0
		int returnInt = 0; 
		
		//now check this the current user as logged in is an administrator, if so proceed with this code
		if(user.getAdminStatus()) {
			
		//if the user is an administrator then return the main this.tickCurrentMax count
		returnInt = this.ticketCurrentMax;
		}else {
			
			//else if the user is not an administrator and is just a standard user only return the count of tickets that he has created
			//to achieve this we create a i counter integer number used to count thought all the tickets in the system
			int i =0;
			
			//while i is less than the systems max tickets perform this while loop
			while(i < this.ticketCurrentMax) {
				
				//inside the loop we check if the current ticket user variable matches the current user name, if it does we increase the return int variable by 1
				if(this.ticket[i].getUser().matches(user.getUsername())) {
					returnInt++;
				}
				
				//once that if check is completed for this ticket increase the ticket count by 1 and check the next.
				i++;
			}
		}
		
		//once that function has been completed return the returnInt variable to the requester.
		return returnInt;
	}
	
	
	//***** --------------------------- User Login Method ------------------------------ ******\\
	
	//User login method, accepts the user name and password of a user and checks if a user entry matches those variables, check is performed first for user name and secondly for password, if the user name check fails the method will not proceed to check the password.
	public User login(String username, String password) {
		
		//create i integer variable to count up in while loop
		int i = 0;
		//Logged in user
		User user = null;
		
		//while loop that performs the check for all users currently in the system
		while (i < this.userCurrentMax) {
			
			//step one check if the user name  matches the user name of the entered login details
			if(this.user[i].getUsername().matches(username)) {
				//if the user name matches now check if the password matches the one entered by the user attempting the login
				if(this.user[i].getPassword().matches(password)) {
					//if both checks pass and its the correct user name and password return that user to the front end
					user = this.user[i];
				}
			}
			//i++ counting up each user in the while loop
			i++;
		}
		//return null if all checks fail
		return user;

				
	}
	
	//***** --------------------------- Creating, Editing & Deleting Tickets ------------------------------ ******\\
	
	//public method used by the FrontEnd & BackEnd when loading tickets to load tickets into the program
	public boolean createTicket(String name, String description, String priorty, String resolved,String modified,String created,String user) {
		
		//success variable returns true if the method executed
		boolean success = false;
		
		//check if the current ticket counter is equal to the array length
		if(this.ticketCurrentMax == ticket.length) {
			
			//create a new array that is the same size as the current user array but increase by 10
			Ticket[] newTicket = new Ticket[this.ticket.length+10];
			
			//create a counting variable called i
			int i =0;
			
			//count up every ticket and transfer them to the new ticket array with a while loop
			while(i < this.ticketCurrentMax) {
				
				//every time the loop triggers set an old ticket to the the new array position
				newTicket[i] = this.ticket[i];
				//increase i (ticket counter) so we can process all ticket entries
				i++;
			}
			
			//once that while loop is completed set the original ticket array to the value of the new expanded one
			this.ticket = newTicket;
			
			//create a ticket at the current ticket count number represented by ticketCurrentMax
			ticket[this.ticketCurrentMax] = new Ticket(name, description, Integer.parseInt(priorty),Boolean.valueOf(resolved),modified,created, user);
			
			//once that ticket has been created increase the current ticket counter.
			this.ticketCurrentMax++;
			
			//set the success value to true to show the method succeeded
			success = true;
		
			//else if the max ticket counter is not equal to the array length add a ticket like normal
		}else {
			//create a ticket at the current ticket count number represented by ticketCurrentMax
			ticket[this.ticketCurrentMax] = new Ticket(name, description, Integer.parseInt(priorty),Boolean.valueOf(resolved),modified,created, user);
			
			//once that ticket has been created increase the current ticket counter.
			this.ticketCurrentMax++;
			//set the success value to true to show the method succeeded
			success = true;
		}
	
	//finally return success so the calling user interface knows if the method succeeded or failed
	return success;
		
	}
	
	//public method used by the front end to submit user edits back to update a ticket
	public boolean editTicket(Ticket ticket,String name, String description, String priorty, User user) {
		
		//edit user tickets does not require user authentication and a ticket a user will only be able to get tickets from the front end that they have access too
		
		//update the ticket name
			ticket.setName(name);
		//update the ticket description
			ticket.setDescription(description);
		//update the ticket priority
			ticket.setPriorty(priorty);
		//update the last modified date
			ticket.setLastModified(user.getUsername());
		
	
		//if all works and the correct user is logged in then process the change and return true
		return true;
	
}
	//public method delete ticket, this method checks the user logged in the owner of the ticket then proceeds to rebuild the array excluding the ticket marked for deletion
	public boolean deleteTicket(Ticket ticket, User user) {

		//Delete Success status
		Boolean success = false;
		
		//Step one, check the user is an administrator
		if(user.getAdminStatus()) {

		//create a new array that is the same size as the current ticket array
				Ticket[] newTicket = new Ticket[this.ticket.length];
				
				//create 2 variables, one to track the original array, and the second to track the entries in the new ticket array, this was the record we want to delete can be excluded.
				//integer i = original array
				int i =0;
				//integer ii = new array
				int ii =0;
				
				//now process the loop to grab all the tickets that are not the selected one and copy them to the new array
				while(i < this.ticketCurrentMax) {
					//check to see if the current ticket "i" has the same value as the id of the ticket we wish to delete, if not copy it
					if (!ticket.getName().matches(this.ticket[i].getName())) {
						// if this ticket is not the same as the selected one copy it into the new array and increase the array integer count "ii"
						newTicket[ii] = this.ticket[i];
						ii++;
					}
					//now increase the normal array count
					i++;
				}
				//once this copy process is completed remove 1 from the global ticket counter
				this.ticketCurrentMax -=1;
				
				//now reset the old array to the value off the new array (with the deleted entry gone)
				this.ticket = newTicket;
				//if this all proceeds then return true, else return false
				success = true;
				
		}else {
			//else if this fails set success to false;
			success = false;
		}
				//finally  return the value of success
				return success;
		
	}
	
	//Change status processing function, grabs the selected record as a input and will flip the status from true to false ***\\ 
	
		public boolean changeStatus(Ticket ticket,User user) {
			
			//create the success variable, is set if the program succeeds or fails and is returned to the requester
			boolean success;
			
			//firstly check if the user logged in is an administrator
			if(user.getAdminStatus()) {
				
				//if they are and the resolved status of the ticket is true then set the resolved status to false
				if (ticket.getResolvedStatus()) {
					
					//if currently true update status to be false
					ticket.setResolvedStatus(false);
					
					//finally update the lastModified UserName record.
					ticket.setLastModified(user.getUsername());
				}else {
					//else if the current resolve status is false set the status to true
					ticket.setResolvedStatus(true);
					
					//then update the lastModified record
					ticket.setLastModified(user.getUsername());
				}
				//if that is successful set success to true!
				success =  true;
			}else {
				//else if the user is not logged in as an administrator then that will fail and success will = false
				success = false;
			}
			
			//finally return the value of success back to the requester (front end)
			return success;
			
		}
		
		
	//***** --------------------------- Creating, & Deleting Users ------------------------------ ******\\
	
	public boolean createUser(String username, String firstname, String lastname, String password, Boolean isAdmin) {
		
		//success return variable, used to tell the front end if the call failed or worked!
		boolean success = false;
		
		//check if the current user max is equal to the user array length, if so run this code to expand the array
		if(this.userCurrentMax == user.length) {
			
			//create a new user array that is the size of the old one + 10 
			User[] newUser = new User[user.length+10];
			
			//create a integer counter variable used to count all all the users
			int i = 0;
			
			//create a while loop to count up i and trigger a call for every user entry
			while (i < this.userCurrentMax) {
				
				//set the value of a user in the new array equal to the user from the old array
				newUser[i] = this.user[i];
				
				//now increase i again to continue counting
				i++;
			}
			
			//once all users are transfered set the old array to our new expanded one
			this.user = newUser;
			
			//process the user creation at the current max user count value and pass it all the information
			this.user[this.userCurrentMax] = new User(username, firstname, lastname, password, isAdmin);
			
			//increase the current max user count
			this.userCurrentMax++;
			
			//set the return value to true to show the method has successfully processed
			 success = true;
			 
			 //else if it does not need to expand the array then just create a user normally
		}else {
			
			//process the user creation at the current max user count value and pass it all the information
			this.user[this.userCurrentMax] = new User(username, firstname, lastname, password, isAdmin);
			
			//increase the current max user count
			this.userCurrentMax++;
			
			//set the success return value to true to show this was successful
			 success = true;
		}
	
		//return the success value, true or false.
		return success;
	}
	
	public boolean deleteUser(User target, User user) {
		//create success value holding.
		boolean success = false;
		
		if(user.getAdminStatus()) {
		
		//create a new array that is the same size as the current user array
		User[] newUser = new User[this.user.length];
		
		//create 2 variables, one to track the original array, and the second to track the entries in the new user array, this was the record we want to delete can be excluded.
		//integer i = original array
		int i =0;
		//integer ii = new array
		int ii =0;
		
		//now process the loop to grab all the tickets that are not the selected one and copy them to the new array
		while(i < this.ticketCurrentMax) {
			//check to see if the current ticket "i" has the same value as the userName of the user we wish to delete, if not copy it
			if (!target.getUsername().matches(this.user[i].getUsername())) {
				// if this userName is not the same as the selected one copy it into the new array and increase the array integer count "ii"
				newUser[ii] = this.user[i];
				ii++;
			}
			//now increase the normal array count
			i++;
		}
		//once this copy process is completed remove 1 from the global user counter
		this.userCurrentMax -=1;
		
		//now reset the old array to the value off the new array (with the deleted entry gone)
		this.user = newUser;
		//if this all proceeds then return true, else return false
		success = true;
		}else {
			//else if the user is not an administrator and the code fails return false
			success = false;
		}
		//now return the success value, true / false
		return success;
	}
	
	
	//***** --------------------------- Saving Arrays to CSV ------------------------------ ******\\
	
	//public function saveUsers is called when the GTerm window is closed by a user and saves all the users to a .CSV file- (Throws exception)
	public void saveUsers() throws IOException{
		
		//create new file called users.csv represented by userFile variable in java
		FileWriter fileWriter = new FileWriter("users.csv");
		
		//create a new instance of the buffered writer
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		//create a new instance of the print writer called out and assign it to the new file
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		//create integer variable i used to count up the records
		int i=0;
		
		//while loop to grab all data from the users array and write it out to the user.csv file, process this loop while i representing the counter is less than the current max users variable.
		while(i < this.userCurrentMax) {
			
			//process the line output for every users, grab the user data as displayed below and separate them by ","
			printWriter.println(user[i].getUsername()+","+user[i].getFirstname()+","+user[i].getLastname()+","+user[i].getPassword()+","+user[i].getAdminStatus());
			
			//increase the current variable
			i++;
		}
		
		//Finally close the file with out (Print Writer) . close, the file is now ready to be used the next time the program is loaded
		printWriter.close();
	}
	
	//public function SaveTickets is called when the GTerm window is closed by a user and saves all the= tickets to a .CSV file- (Throws exception)
	public void saveTickets() throws IOException{
		
		//create new file called tickets.csv represented by ticketFile variable in java
		FileWriter fileWriter = new FileWriter("tickets.csv",false);
		
		//create a new instance of the print writer called out and assign it to the new file
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		//create a new instance of the print writer
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		//create integer variable i used to count up the records
		int i=0;
		
		//while loop to grab all data from the ticket array and write it out to the user.csv file, process this loop while i representing the counter is less than the current max tickets variable.
		while(i < this.ticketCurrentMax) {
			
			//process the line output for every ticket variable, grab the variable data as displayed below and separate them by ","
			printWriter.println(this.ticket[i].getName()+","+this.ticket[i].getDescription()+","+this.ticket[i].getPriorty()+","+this.ticket[i].getResolvedStatus()+","+this.ticket[i].getLastModifiedDate()+","+this.ticket[i].getCreatedDate()+","+this.ticket[i].getUser());
			
			//increase the current variable
			i++;
		}
		
		//Finally close the file with out (file writer) . close, the file is now ready to be used the next time the program is loaded
		printWriter.close();
	}
	
	//***** --------------------------- Loading Arrays to CSV ------------------------------ ******\\
	
	//Load users function, called at the start of the program to load all the users from a CSV file
	public void loadUsers(String path) {
		//load path for file system
		String line ="";
		//create new buffered reader inside try catch 
		try {
			//create new buffered reader and pass it the file path variable
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			
			//while loop needed to get the line input, only occurs while the readLine is not null
			while((line = bufferedReader.readLine()) != null) {
				
				//get values and convert them into an array splitting at the comer
				String[] values = line.split(",");
				
				//set output values to the current value of the values array from the loaded CSV file
				String username = values[0];
				String firstname = values[1];
				String lastname = values[2];
				String password = values[3];
			    Boolean isAdmin = Boolean.valueOf(values[4]);

				this.createUser(username, firstname, lastname, password, isAdmin);
			}
			
			//Catch Exception print statements, needed for this code to function and provide a error if it occurs
		} catch (FileNotFoundException e) {
			//  Auto-generated catch block
			e.printStackTrace();
			try {
				//if no file is found call the save users function to create the file in the current directory
				//create the default administrator and user accounts for the system
				this.saveUsers();
				this.createUser("admin","admin","admin","123",true);
				this.createUser("user", "user", "user", "", false);
				
			} catch (IOException e1) {
				// DO NOT PRINT TRACE 
				//e1.printStackTrace();
			}
		} catch (IOException e) {
			// DO NOT PRINT TRACE 
			//e.printStackTrace();
		}
	}
	
	//Load tickets function, loads all tickets from the ticket .CSV file and imports them into the program
	public void loadTickets(String path) {
		//load path for file system
		String line ="";
		//create new buffered reader inside try catch 
		try {
			//create a new bufferedReader and pass it the file path that we specified
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
			
			//while loop needed to get the line input, only occurs while the readLine is not null
			while((line = bufferedReader.readLine()) != null) {
				
				//get values and convert them into an array splitting at the comer
				String[] values = line.split(",");
				
				//set output values to the current value of the values array from the loaded CSV file
				String name = values[0];
				String description = values[1];
				String priorty = values[2];
				String resolved = values[3];
				String modified = values[4];
				String created = values[5];
				String user = values[6];
				
				//finally call the createTicket method and pass it all the variables from the .CSV
				this.createTicket(name, description,  priorty, resolved,modified,created,user);
				//Create ticket method 
			}
			
			//Catch Exception print statements, needed for this code to function and provide a error if it occurs
		} catch (FileNotFoundException e) {
			//  Auto-generated catch block
			e.printStackTrace();
			try {
				//if no file is found call the save tickets function to create the file in the current directory
		
				this.saveTickets();
				

			} catch (IOException e1) {
				// DO NOT PRINT TRACE 
				//e1.printStackTrace();
			}
		} catch (IOException e) {
			// DO NOT PRINT TRACE 
			//e.printStackTrace();
		}
	}


	
	
}
