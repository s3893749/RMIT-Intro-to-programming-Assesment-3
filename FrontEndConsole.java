//Import Scanner
import java.io.IOException;
import java.util.Scanner;

//Start Class File
public class FrontEndConsole {
	
	//declaring the BackEnd Variable
	private Scanner Scanner;
	
	// Declares the Backed Instance
	private BackEnd backEnd;
	//Declare Ticket array 
	private Ticket[] ticket;
	//Declare max ticket counter
	private int ticketCurrentMax;
	// Declares the User name and password input for the login screen
	private String inputUsername;
	private String inputPassword;
	// declares the main user instance
	private User user;

	
	
	//***** --------------------------- Main Constructor Class ------------------------------ ******\\
	
	public FrontEndConsole(BackEnd backEnd) {
		
		//Sets BackEnd Class Variable to the pass through Back-end
		this.backEnd = backEnd;
		//Creates and instance of the scanner and passes System.in
		Scanner Scanner = new Scanner(System.in);
		//Sets this.scanner to our new Scanner
		this.Scanner = Scanner;
		
		//Print Welcome Screen
		System.out.println("Maintanace Manager | RMIT Assesment 3");
		System.out.println("");
		System.out.println("Welcome to Maintanace Manager!");
		System.out.println("");
		System.out.println("To Get Started Please Login");
		// Step 2 Print the Title Name
		System.out.println("");
		System.out.println("If this is your first time running the");
		System.out.println("program please user the default login!");
		System.out.println("");
		System.out.println("username: admin, Password: 123");
		System.out.println("username: user, Password: ");
		System.out.println("");
		System.out.println("Please edit the users.csv to create your");
		System.out.println("own user accounts and passwords");
		System.out.println("");
		//call the login function
		//i know the constructor should not call functions but this was the most efficient way to right the code, if the login fails the login function is recalled until the user successful logins in, having login code here would mean adding more duplicate lines of code only for login to be called later
		this.login();
	
	
	}
	
	
	//***** --------------------------- Login Method ------------------------------ ******\\
	private void login() {
		
		//Print request for UserName
		System.out.println("Username:");
		
		//sets this.inputUsername to the scanner.nextline value that the user as typed
		this.inputUsername = this.Scanner.nextLine();
		
		//check if the UserName is not null != """(Blank)
		if(this.inputUsername != "") {
			
			//if the UserName has a value ask for the password
			System.out.println("Password:");
			
			//set the this.inputPassword to the scanner.nextline value that the user has typed in
			this.inputPassword = this.Scanner.nextLine();
			
			//now set the this.user variable to the this.backend.login() and pass the UserName & Password (The next code checks if it succeeds or failed)
			this.user = this.backEnd.login(this.inputUsername, this.inputPassword);
		}
		
		//check this the current user is null, if not then proceed to run the program
		if(this.user != null) {
			
		//welcome the user to the program by printing welcome and the first and last name
		System.out.println("User Logged in as "+this.user.getFirstname()+" "+this.user.getLastname());
		
		//now run the main update function, this prints the table and other UI elements
		this.update();
		
		//else if the user is null then proceed with this code
		}else {
			//print login failed please try again
			System.out.println("Login Failed, Please Try Again");
			
			//recall this login function
			this.login();
			
		}
	}
	
	
	//***** --------------------------- Login Method ------------------------------ ******\\
	private void update() { 
		
		//Print out ticket table to show all the current tickets
		System.out.println("_____________________________________________________________________________________________________________");
		System.out.format("%12s | %12s | %12s | %12s | %12s | %12s | %12s \n", "Issue","Description", "Priorty", "Resolved Status", "Last Modified", "Created","User");
		
		//get the current ticket max from the backEnd and pass the user back (User is needed so the system can check what tickets to provide, is he an administrator?)
		this.ticketCurrentMax = this.backEnd.getTicketCurrentMax(this.user);
		//now get the tickets from the backEnd, again pass the current user (tickets array is depended on administrator status)
		this.ticket = this.backEnd.getTickets(this.user);

		//create i integer variable used for counting up to the current tickets
		int i = 0;
		
		//while our i count is less than the total amount of tickets returned to the user run this while loop
		while (i < this.ticketCurrentMax) {
			
		//system out print format command allows us to print a line and change the formating to inject our ticekt infromation from the ticket array
		System.out.format("%12s | %12s | %12s | %12s | %12s | %12s | %12s %12s \n",i,this.ticket[i].getName(), this.ticket[i].getDescription(), this.ticket[i].getPriorty(),this.ticket[i].getResolvedStatus(),this.ticket[i].getLastModifiedDate(), this.ticket[i].getCreatedDate(), this.ticket[i].getUser());
		
		//finally increase the i counting variable
		i++;
		}
		System.out.println("");
		System.out.println("Type: 'add' To Get Started Adding a New Ticket");
		System.out.println("Type: 'edit' To Edit a Ticket!");
		System.out.println("Type: 'import' To Import Tickets from .CSV");
		System.out.println("");
		System.out.println("Type: 'exit' To Leave The Program");
		
		//get user input for next command
		String userInput = this.Scanner.nextLine();
		
		//check what the user has entered and call the correct function
		if (userInput.contentEquals("add")) {
			//process new item function
			this.createTicket();
		}else if(userInput.contentEquals("edit")) {
			//process new sale function
			this.editTicket();
		}else if(userInput.contentEquals("import")){
			//get the file path from the user
			String path = this.Scanner.nextLine();
			//pass the file path to the load tickets function
			this.backEnd.loadTickets(path);
			//update the GUI
			this.update();
		}else if(userInput.contentEquals("exit")) {
			//exit program function
			try {
				this.backEnd.saveUsers();
				this.backEnd.saveTickets();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
			
		}
		
	}
	
	//create new item function
	private void createTicket() {
		//create a array with system outputs
		String[] systemOutputs = new String[3];
		//set system outputs in the array
		systemOutputs[0] = "Please Enter The Issue Name";
		systemOutputs[1] = "Please Enter The Issue Description";
		systemOutputs[2] = "Please Enter The priorty 1-10";
		
		//i is used for counting
		int i = 0;
		//create user input array
		String[] userInput = new String[3];

		//while loop to print the system outputs and get the user inputs
		while(i < userInput.length) {
			System.out.println(systemOutputs[i]);
			userInput[i] = this.Scanner.nextLine();
			i++;
		}
		
		//adding new item confirmation
		System.out.println("Creating New Ticket!");
		//pass user-input to back-end
		this.backEnd.createTicket(userInput[0], userInput[1], userInput[2],"false", null, null,this.user.getUsername());
		//recall update function
		update();
		
	}
	
	private void editTicket(){
		//first of all get the ticket id
		System.out.println("Please Enter The Ticket ID");
		String ticketId = this.Scanner.nextLine();

		if (ticketId != null) {

		//create a array with system outputs
				String[] systemOutputs = new String[3];
				//set system outputs in the array
				systemOutputs[0] = "Please ReEnter the New Issue Name";
				systemOutputs[1] = "Please ReEnter The Issue Description";
				systemOutputs[2] = "Please ReEnter The priorty 1-10";
				
				String[] currentTicket = new String[3];
				currentTicket[0] = this.ticket[Integer.parseInt(ticketId)].getName();
				currentTicket[1] = this.ticket[Integer.parseInt(ticketId)].getDescription();
				currentTicket[2] = String.valueOf(this.ticket[Integer.parseInt(ticketId)].getPriorty());
				//i is used for counting
				int i = 0;
				//create user input array
				String[] userInput = new String[3];

				//while loop to print the system outputs and get the user inputs
				while(i < userInput.length) {
					System.out.println(systemOutputs[i]);
					System.out.println(currentTicket[i]);
					userInput[i] = this.Scanner.nextLine();
					i++;
				}
				
				//create array called my tickets and fill it with backEnd tickets for this user.
				Ticket[] myTickets = this.backEnd.getTickets(this.user);
				
				// Pass current output array to createRecord function for Processing
				boolean result = this.backEnd.editTicket(myTickets[Integer.valueOf(ticketId)],userInput[0], userInput[1], userInput[2],this.user);
				if (result) {
					System.out.println("Updating Ticket");
				}else {
					System.out.println("ERROR: You do not have permission to edit that ticket");
				}
				update();	
		}
		
	}
	}
