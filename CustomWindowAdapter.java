//Import Java window adapter event class, allows for the window adapter events functionality
import java.awt.event.WindowAdapter;

// **** START MAIN CLASS **** \\

//(Main Class Extends Windows Adapter this is so it gets all the functionality)
public class CustomWindowAdapter extends WindowAdapter
{
	//Declare backEnd variable
    public BackEnd backEnd;
    
    //pass the backEnd into the custom window adapter (created by front end GTerm)
    public CustomWindowAdapter(BackEnd backEnd)
    {
    	//call super() to make sure everything is loaded correctly from the window adapter parent
    	super();
    	//set this.backEnd to the backEnd instanced passed from GTerm
    	this.backEnd = backEnd;
    	
    	
    	// This class solely exists to i can inject the this.backEnd into the window listener, this allow me to call the backEnd save functions on a window close
       
    }
}