//**** START CLASS ****\\
public class Application {
	
	//Declare new class instances for backEnd
	private BackEnd backEnd;
	
	//Declare instances for front ends, uiGT0 & uiGT1
	@SuppressWarnings("unused")
	private FrontEndGTerm uiGT0;
	@SuppressWarnings("unused")
	private FrontEndGTerm uiGT1;
	//Declare front end console instance
	
	@SuppressWarnings("unused")
	private FrontEndConsole frontEndConsole;
	
	//Run Application Constructor
	public Application () {
		
		//Create instance of back-end
		this.backEnd = new BackEnd();
		
		//create new instances of front end and pass it the back-end
		this.uiGT0 = new FrontEndGTerm(this.backEnd);
		//create new instances of front end and pass it the back-end
		this.uiGT1 = new FrontEndGTerm(this.backEnd);
		//create the instance of the console front end and pass it back-end
		this.frontEndConsole = new FrontEndConsole(this.backEnd);
		
	}
	
	//main run function
	public static void main(String[] args) {
		//create instance of self
		@SuppressWarnings("unused")
		Application app = new Application();
	}

}