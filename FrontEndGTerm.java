
//Imports Java AWT color (used for fonts)
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.IOException;

//START MAIN CLASS FILE
public class FrontEndGTerm{

	// *** Private Variables declared for later use in the program ***\\

	// sets the main GTerm interface width & height
	private int interfaceWidth;
	private int interfaceHeight;
	// sets the login screen interface width & height
	private int popUpGtInterfaceWidth;
	private int popUpGtInterfaceHeight;
	// Declares the GTerm instances
	private GTerm gt;
	private GTerm popUpGt;
	// Declares the Backed Instance
	private BackEnd backEnd;
	// Declares the ticket table
	private int ticketTable;
	private Ticket[] ticket;
	// Declares the User name and password input for the login screen
	private int inputUsername;
	private int inputPassword;
	// declares the main user instance
	private User user;
	// Ticket Creation Inputs
	private int issueNameInput;
	private int issueDescriptionInput;
	private int issuePriortyInput;
	

	// *** FrontEnd Constructor Class - Runs once at the start of each instance creation of an object ***\\

	public FrontEndGTerm(BackEnd backEnd) {

		// set this.backEnd to the back-end instance received from the main application
		// class
		this.backEnd = backEnd;

		// set the interface width and height for the application
		this.interfaceWidth = 1285;
		this.interfaceHeight = 725;

		// set login interface size
		this.popUpGtInterfaceWidth = 305;
		this.popUpGtInterfaceHeight = 410;

		// create a instance of GTerm with the correct width and height
		this.popUpGt = new GTerm(this.popUpGtInterfaceWidth, this.popUpGtInterfaceHeight);
		
		// *** Draw login, draws the login GUI ***\\
		
		// Step 1 Set the XY position of the field title
		this.popUpGt.setXY(20, 60);
		// Step 2 Print the Title Name
		this.popUpGt.println("Username:");
		// Step 3 set the XY position of the Text Field Input
		this.popUpGt.setXY(20, 80);
		// Step 4 Create the InpuField
		this.inputUsername = this.popUpGt.addTextField("", 200);
		// ___________________________________________________________________\\
		// Step 1 Set the XY position of the field title
		this.popUpGt.setXY(20, 120);
		// Step 2 Print the Title Name
		this.popUpGt.println("Password:");
		// Step 3 set the XY position of the Text Field Input
		this.popUpGt.setXY(20, 140);
		// Step 4 Create the InpuField
		this.inputPassword = this.popUpGt.addPasswordField("", 200);
		// ___________________________________________________________________\\
		// ___________________________________________________________________\\
		// Step 1 Set the XY position of text
		this.popUpGt.setXY(15, 180);
		// Step 2 Print the Title Name
		this.popUpGt.println("If this is your first time running the");
		this.popUpGt.println("program please user the default login!");
		this.popUpGt.println("");
		this.popUpGt.println("username: admin, Password: 123");
		this.popUpGt.println("username: user, Password: ");
		this.popUpGt.println("");
		this.popUpGt.println("Please edit the users.csv to create your");
		this.popUpGt.println("own user accounts and passwords");
		// ___________________________________________________________________\\
		// Create the "Create Ticket Button"
		this.popUpGt.setXY(110, 340);
		this.popUpGt.addButton("Login", this, "loginButton");
		this.popUpGt.setXY(20, 0);

		// *** GUI / Art ***\\
		// Print the Top Heading Title & Set Font Color,Text Size & GUI Position.
		this.popUpGt.setFontColor(255, 255, 255);
		this.popUpGt.setFontSize(24);
		this.popUpGt.setXY(75, 10);
		this.popUpGt.println("Staff Login");
		// Load the Top Banner art work for (Loaded Last so all items are drawn in front of it)
		this.popUpGt.setXY(0, 0);
		this.popUpGt.addImageIcon("popup-banner.png");
	}

	
	// *** Main LoginButton Function - gets user input from the login fields and checks to to see if a user match those details***\\

	public void loginButton() {
		
		//set the UserName variable, this is grabbed from the inputUsername text entry field
		String username = this.popUpGt.getTextFromEntry(this.inputUsername);
		
		//set the password variable, this is grabbed from the inputPassword text entry field
		String password = this.popUpGt.getTextFromEntry(this.inputPassword);
		
		//finally set the user for this instance of GTerm to the returned user from the back-end, if no user is found then this will return null
		this.user = this.backEnd.login(username, password);
		
		//Check if the user is not null, if no user matches the login details null is returned from the back-end
		if (this.user != null) {
			
			//if a user is found and this.user is not null then close the pop up GTerm window and create the main this.gt interface window
			this.popUpGt.close();
			this.gt = new GTerm(this.interfaceWidth, this.interfaceHeight);
			
			//now run the load the staff interface
			this.loadStaffInterface();
			
			//now the staff interface is loaded update the ticket entry table with the this.update command
			this.update();
			
			//finally create a custom window listener instance to allow us to inject the this.backend instance into i add finally it to the main GTerm window, this allows us to call a back-end function on close.
			this.gt.addWindowListener(new CustomWindowAdapter(this.backEnd) {

				@Override
				public void windowClosing(WindowEvent e) {
					// Try and Catch loop is required as the back end methods called to save functions need try and catch enclosure as they may throw file not found errors.
					try {
						//run this.backEnd.saveUsers method is called this means saving occurs when the GTerm window closed
						this.backEnd.saveUsers();
						//run this.backEnd.saveTickets method, this allows us to automaticly save all the tickets on the window close function
						this.backEnd.saveTickets();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		
		//finally now we have performed the login functions is the user that is returned is not full we can process what happens if it is null, in this case we show an error and ask the user to try the login.
		}else {
			//else if that fails and the user returned is null then show the following error:
			this.popUpGt.showErrorDialog("Login Failed, Please Check Your Details");
		}
	}

	// *** Create Record Interface, Function Triggered by the button to open up the GTerm PopUp Window for the creation of a new entry ***\\

	public void createRecordInterface() {

		// Create the GTerm PopUp window and pass it the width and height variables as
		// defined in the constructor
		this.popUpGt = new GTerm(this.popUpGtInterfaceWidth, this.popUpGtInterfaceHeight);

		// User Inputs for new ticket creation
		// The following inputs are the GTerm Text Fields and Titles where the user
		// enters the data that they wish to pass to he new issue ticket.
		// These follow 5 Steps, 1. set the XY values for the field title, 2. print the
		// field title name, 3. set the XY value for the field input, 4. Create the
		// input field 5. repeat step 1-4

		// Step 1 Set the XY position of the field title
		this.popUpGt.setXY(20, 60);
		// Step 2 Print the Title Name
		this.popUpGt.println("Issue Name:");
		// Step 3 set the XY position of the Text Field Input
		this.popUpGt.setXY(20, 80);
		// Step 4 Create the InpuField
		this.issueNameInput = this.popUpGt.addTextField("", 200);
		// ___________________________________________________________________\\
		// Step 1 Set the XY position of the field title
		this.popUpGt.setXY(20, 120);
		// Step 2 Print the Title Name
		this.popUpGt.println("Issue Description:");
		// Step 3 set the XY position of the Text Field Input
		this.popUpGt.setXY(20, 140);
		// Step 4 Create the InpuField
		this.issueDescriptionInput = this.popUpGt.addTextArea("", 200, 100);
		// ___________________________________________________________________\\
		// Step 1 Set the XY position of the field title
		this.popUpGt.setXY(20, 260);
		// Step 2 Print the Title Name
		this.popUpGt.println("Priorty / Severity:");
		// Step 3 set the XY position of the Text Field Input
		this.popUpGt.setXY(20, 280);
		// Step 4 Create the InpuField
		this.issuePriortyInput = this.popUpGt.addTextField("", 200);
		// Step 4A (The Priority has a extra line of text and so requires 2 additional
		// print-line statements to facilitate this.)
		this.popUpGt.println("");
		this.popUpGt.println("Enter a Number From 1 - 10");
		// ___________________________________________________________________\\
		// Create the "Create Ticket Button"
		this.popUpGt.setXY(20, 340);
		this.popUpGt.addButton("Create Ticket", this, "createRecordButton");
		this.popUpGt.setXY(20, 0);

		// *** GUI / Art ***\\

		// Print the Top Heading Title & Set Font Color,Text Size & GUI Position.
		this.popUpGt.setFontColor(255, 255, 255);
		this.popUpGt.setFontSize(24);
		this.popUpGt.setXY(45, 10);
		this.popUpGt.println("Create Ticket");

		// Load the Top Banner art work for (Loaded Last so all items are drawn in front
		// of it)
		this.popUpGt.setXY(0, 0);
		this.popUpGt.addImageIcon("popup-banner.png");

	}

	// *** Create Record Button Function, Used to grab the data from the input
	// values and pass to the processing function ***\\

	public void createRecordButton() {

		// firstly check that the priority is a numerical value, if it does not throw a
		// error process the ticket creation, else display a error
		if (!intChecker(this.popUpGt.getTextFromEntry(this.issuePriortyInput))) {

			// display message if the priority was not a numerical value
			this.gt.showWarningDialog("Priorty Must Be a Numerical Value");

		} else {

			// Call the "createRecord" function for handling the ticket creation, and pass
			// the input values to the backend for processing
			this.backEnd.createTicket(this.popUpGt.getTextFromEntry(issueNameInput),
					this.popUpGt.getTextFromEntry(this.issueDescriptionInput),
					this.popUpGt.getTextFromEntry(this.issuePriortyInput), "false", null, null,
					this.user.getUsername());

			// Finally now close the gtPopUp window, this returns the user seamlessly to the
			// main interface screen.
			this.popUpGt.close();

			this.update();
		}

	}

	public void editRecordInterface() {

		// Firstly we create a selectedRecord integer variable and set it to the value
		// of the row that the user has selected
		int selectedRecord = selectRecord();

		// Next we perform a check to ensure this is a valid Ticket, if no row form the
		// table is selected then -1 is returned so we check to see if it is -1, if it
		// is then throw a GTerm pop-up warning else continue the code.
		if (selectedRecord == -1) {
			// Throw Warning is no table row is selected
			this.gt.showWarningDialog("Please Select a Valid Ticket");
		} else {

			// Create the GTerm PopUp window and pass it the width and height variables as
			// defined in the constructor
			this.popUpGt = new GTerm(this.popUpGtInterfaceWidth, this.popUpGtInterfaceHeight);

			// User Inputs Editing Ticket Data
			// This is a similar process to creating a new ticket with the same steps used,
			// the only difference is when we add the text fields we grab the content from
			// the associated arrays and populate the arrays with them by default.

			// Step 1, Set the XY position of the field title
			this.popUpGt.setXY(20, 60);
			// Step 2, Print the field title
			this.popUpGt.println("Issue Name:");
			// Step 3, Set the TextField XY GUI cords.
			this.popUpGt.setXY(20, 80);
			// Step 4, This step differs from creating a new ticket as we can see it doesn't
			// leave the field blank but instead fetch the data from the assorted arrays to
			// populate the field.
			this.issueNameInput = this.popUpGt.addTextField(this.ticket[selectedRecord].getName(), 200);
			// ___________________________________________________________________\\
			// Step 1, Set the XY position of the field title
			this.popUpGt.setXY(20, 120);
			// Step 2, Print the field title
			this.popUpGt.println("Issue Description:");
			// Step 3, Set the TextField XY GUI cords.
			this.popUpGt.setXY(20, 140);
			// Step 4, This step differs from creating a new ticket as we can see it doesn't
			// leave the field blank but instead fetch the data from the assorted arrays to
			// populate the field.
			this.issueDescriptionInput = this.popUpGt.addTextArea(this.ticket[selectedRecord].getDescription(), 200,
					100);
			// ___________________________________________________________________\\
			// Step 1, Set the XY position of the field title
			this.popUpGt.setXY(20, 260);
			// Step 2, Print the field title
			this.popUpGt.println("Priorty / Severity:");
			// Step 3, Set the TextField XY GUI cords.
			this.popUpGt.setXY(20, 280);
			// Step 4, This step differs from creating a new ticket as we can see it doesn't
			// leave the field blank but instead fetch the data from the assorted arrays to
			// populate the field.
			this.issuePriortyInput = this.popUpGt.addTextField(String.valueOf(this.ticket[selectedRecord].getPriorty()),
					200);
			// (The Priority has a extra line of text and so requires 2 additional
			// print-line statements to facilitate this.)
			this.popUpGt.println("");
			this.popUpGt.println("Enter a Number From 1 - 10");
			// ___________________________________________________________________\\
			// Create the "Create Ticket Button"
			this.popUpGt.setXY(20, 340);
			this.popUpGt.addButton("Save Ticket", this, "editRecordButton");
			// ___________________________________________________________________\\
			// Finally on the edit ticket screen we also display the last time the ticket
			// was edited at the bottom of the ticked edit window"
			// Set XY cords to bottom of window
			this.popUpGt.setXY(20, 380);
			// Set Font Size to small 8 (normal = 12)
			this.popUpGt.setFontSize(8);
			// Print the last edited text and time stamp to the window
			this.popUpGt.println("Last Edited: " + this.ticket[selectedRecord].getLastModifiedDate());
			this.popUpGt.setXY(20, 0);

			// *** GUI / Art ***\\

			// Print the Top Heading Title & Set Font Color,Text Size & GUI Position.
			this.popUpGt.setFontColor(255, 255, 255);
			this.popUpGt.setFontSize(24);
			this.popUpGt.setXY(45, 10);
			this.popUpGt.println("Edit Ticket");

			// Print the top banner art work to the window, this is printed last so all
			// other items are drawn ontop of it in case of overlap
			this.popUpGt.setXY(0, 0);
			this.popUpGt.addImageIcon("popup-banner.png");
		}

	}
	
	//*** Edit Record processing button, this grabs the changes from the editRecordInterface and combines them into an array to be processed by the "editRecord" processing function ***\\ 
	
		public void editRecordButton() {
			
			// Firstly check that the priority is a numerical value, if not throw a error, else process the ticket creation
			if (!intChecker(this.popUpGt.getTextFromEntry(issuePriortyInput))) {
				
				// Display message if the priority was not a numerical value
				this.gt.showWarningDialog("Priorty Must Be a Numerical Value");
				
				// If it is a numerical value for priority continue saving the changes.
				}else {
					
					// Get the selected record from the selectRecord function 
					// (The editRecordInterface will perform a check to ensure that a valid table row is select so we don't need to perform the check again here)
					int selectedRecord = selectRecord();
					
					// create a new array called output, set the fist value to the record number that we are editing.
					// Next add all the values into the next 3 inputs.
					String[] output = new String[4];
					output[0] = String.valueOf(selectedRecord);
					output[1] = this.popUpGt.getTextFromEntry(this.issueNameInput);
					output[2] = this.popUpGt.getTextFromEntry(this.issueDescriptionInput);
					output[3] = this.popUpGt.getTextFromEntry(this.issuePriortyInput);
					
					//get ticket list
					Ticket[] ticketTemp = this.backEnd.getTickets(this.user);
					
					// Pass current output array to createRecord function for Processing
					boolean result = this.backEnd.editTicket(ticketTemp[Integer.valueOf(output[0])],output[1], output[2], output[3],this.user);
					if (result) {
						this.gt.showMessageDialog("Ticket Update");
					}else {
						this.gt.showErrorDialog("ERROR: You do not have permission to edit that ticket");
					}
					//close the pop-up window now we have collected all the user data an sent it to processing function
					this.popUpGt.close();
					this.update();
				}
			

			
		}
		
		//*** Remove Record Button, this function is sued to process the user input for what record to be removed and passes it to the back-end function removeRecord ***\\
		
		public void removeRecordButton() {
			
			// Create a selectedRecord variable and grab the user row selection from the function that handles it
			int selectedRecord = selectRecord();
			
			//get ticket list
			Ticket[] ticketTemp = this.backEnd.getTickets(this.user);
			
			// Check if no row was selected, if this is true then -1 is returned and the system will throw the user a error
			if (selectedRecord == -1) {
				// Throw GTerm warning dialog error if no row was selected (-1 return value)
				this.gt.showWarningDialog("Please Select a Valid Ticket");
			}else {

				// Else process the "removeRecord" Function and pass it the row that the user has selected
			//Boolean result = this.backEnd.deleteTicket(selectedRecord);
				Boolean result = this.backEnd.deleteTicket(ticketTemp[selectedRecord], this.user);
			if(result) {
				this.gt.showMessageDialog("Ticket Deleted");
				this.update();
			}
			}
		}
		
		//***  Change Status Button, this button will grab the selected record and check that we have selected a table row ***\\ 
		
		public void changeStatusButton() {
			
			// Grab the selected row from the selectRecord function
			int selectedRecord = selectRecord();
			
			// Perform a check to ensure a row is selected, if no row is selected it will return -1, if -1 then display GTerm Error
			if (selectedRecord == -1) {
				// Show GTerm error when no row is selected
				this.gt.showWarningDialog("Please Select a Valid Ticket");
			}else {
				
				//get ticket list
				Ticket[] ticketTemp = this.backEnd.getTickets(this.user);
				
				//if a row is selected then pass that to the change status processing function below
				Boolean result = this.backEnd.changeStatus(ticketTemp[selectedRecord],this.user);
				if(result) {
					this.gt.showMessageDialog("Status Updated");
					this.update();
				}else{
					gt.showErrorDialog("ERROR: You do not have permission to update that ticket");
				}
			}
			
		}
		
		
	// *** Record Selection Function, Used to grab the ID of the row that the user
	// has selected from the report table ***\\

	private int selectRecord() {

		// Create a selected variable and set it to the index of the row that the user
		// has selected
		int selected = this.gt.getIndexOfSelectedRowFromTable(this.ticketTable);

		// Return the selected variable, this means that in a separate function the user
		// can simply call selectRecord() to receive the row input.
		return selected;
	}
	
	//staff interface (user & administrator)

	private void loadStaffInterface() {
		// *** Draw the main interface elements to the screen (Drawn in constructor as
		// this only needs to occur once) ***\\

		// Draw Main Title & Set Font Size
		this.gt.setXY(85, 20);
		this.gt.setFontSize(26);
		this.gt.println("Facility Manager");
		this.gt.setFontSize(12);
		this.gt.setXY(0, 0);

		// Draw Welcome User
		this.gt.setXY(950, 20);
		this.gt.setFontSize(26);
		this.gt.setFontColor(Color.white);
		this.gt.println("Welcome " + this.user.getFirstname() + " " + this.user.getLastname());
		this.gt.setFontSize(12);
		this.gt.setFontColor(Color.black);
		this.gt.setXY(0, 0);
		
		// Draw Main Reporting Table that contains all the issue ticket records
		this.gt.setXY(20, 160);
		this.ticketTable = this.gt.addTable(1220, 500,
				"Issue	Description	Priorty	Resolved Status	Last Modified	Created	User");
		this.gt.setXY(0, 0);

		// Draw the user Bottoms used to access each of the functions (User Button get
		// passed a name, the object calling the button "This" and the function name)
		this.gt.setXY(20, 100);
		this.gt.addButton("Add Ticket", this, "createRecordInterface");
		this.gt.setXY(0, 0);
		this.gt.setXY(120, 100);
		this.gt.addButton("Edit Ticket", this, "editRecordInterface");
		this.gt.setXY(0, 0);
		if(this.user.getAdminStatus()) {
		this.gt.setXY(230, 100);
		this.gt.addButton("Change Status", this, "changeStatusButton");
		this.gt.setXY(0, 0);
		this.gt.setXY(350, 100);
		this.gt.addButton("Delete Ticket", this, "removeRecordButton");
		this.gt.setXY(0, 0);
		this.gt.setXY(470, 100);
		this.gt.addButton("Import Tickets", this, "importTickets");
		this.gt.setXY(0, 0);
		}

		// Draw Credits Jack Harris
		this.gt.setXY(960, 680);
		this.gt.println("Created By Jack Harris | 22/05/2021");
		this.gt.setXY(0, 0);
		this.gt.setXY(960, 700);
		this.gt.println("ITP Assesment 3 RMIT / OUA");
		this.gt.setXY(0, 0);

		// Finally draw the top banner art work, this occurs last so all other text and
		// buttons are layered on top on the banner
		this.gt.addImageIcon("gui.png");
	}
	
	// *** Import Tickets from .CSV ***\\
	//CSV ticket import function
	public void importTickets() {
		//get the file path
		String path = this.gt.getFilePath();
		//pass the file path to the load tickets backEnd
		this.backEnd.loadTickets(path);
		//update the main window
		this.update();
	}

	// *** True or False Integer Checker (Checks if a string is a numerical value)
	// via input string ***\\

	private boolean intChecker(String input) {

		// Create boolean value for is numerical and set it to true;
		boolean dataTypeNumberIsNumerical = true;

		// Create try catch test to see if the Integer conversion throws a error
		try {
			// Suppress warning used due to the local variables not being called again
			@SuppressWarnings("unused")
			int convertedInteger = Integer.parseInt(input);
		} catch (NumberFormatException e) {
			// If a error has been cached it will set dataTypeNumber to false else it will
			// leave it true
			dataTypeNumberIsNumerical = false;
		}
		// Check what the boolean value of dataTypeNumber is, true or false
		if (dataTypeNumberIsNumerical) {

			// If it is still true that means the string can be converted to a integer and
			// return true!
			return true;

		} else
			// else it means that its is letters that are not numbers that can be converted
			// and return false.
			return false;
	}

	// *** Main Table Update Function ***\\

	private void update() {

		// Firstly clear the current table of all data
		this.gt.clearRowsOfTable(this.ticketTable);
		int ticketCurrentMax = this.backEnd.getTicketCurrentMax(this.user);

		this.ticket = this.backEnd.getTickets(this.user);
		
		
		// Create a record number variable and use it to count up to the currentRecord
		// (Max) starting from 0
		int i = 0;

		// while the recordNumber is less than the currentRecord (Max) add the row to
		// table and populate it with data for that record number
		while (i < ticketCurrentMax) {
				
			//if the user is an Administrator then add all tickets to the table
			this.gt.addRowToTable(this.ticketTable,
					this.ticket[i].getName() + "\t" + this.ticket[i].getDescription() + "\t"
							+ this.ticket[i].getPriorty() + "\t" + this.ticket[i].getResolvedStatus() + "\t"
							+ this.ticket[i].getLastModifiedDate() + "\t" + this.ticket[i].getCreatedDate() + "\t"
							+ this.ticket[i].getUser());
			
			//finally increase the i variable representing the current ticket count, i will count up from 0 to the ticketCurrentMax
			i++;
		}
		
	}


}
