import java.util.*;
import java.io.*;


public class Shell extends Thread {		//Shell extends thread because this class will execute in its own thread

	// input variables for read from input file
    private String User;		
    private String Host;
    private String PATH;

	// Scanner that reads input
    public static Scanner sc = new Scanner(System.in);

	// class constructor
    public Shell(String user, String host, String path) {
        User = user;
        Host = host;
        PATH = path;
    }

    public static String echo1(String input) {		// used for simple echo command
	    String toReturn = "";
	    for(int i = 6 ; i < input.length()-1 ; i++)		// save everything between the quotations in a new string (what the user wants to display)
	    {
	        char c = input.charAt(i);
	        toReturn += c;
	    }
	    return toReturn;
	}
	
    
   // FUNCTIONS USED FOR "->"
	public static String echo2(String input, int index) {		// echo variation used for single arrow command
	    String toReturn = "";
	    for(int i = 6 ; i < index-3 ; i++) {		// save everything between the quotations in a new string (what the user wants to write to the file)
	        char a = input.charAt(i);
	        toReturn += a;
	    }
	    return toReturn;
	}
	
	public static void SingleArrow(String echoOutput, String fileName) throws IOException {		// writing the user input to file
	    FileWriter WriteToFile = new FileWriter(fileName);
	    WriteToFile.write(echoOutput);
	    WriteToFile.flush();
	    WriteToFile.close();
	}
	
	
	// FUNCTIONS USED FOR "->>"
	public static String echo3(String input, int index) {		// echo variation used for double arrow command
	    String toReturn = "";
	    for(int i = 6 ; i < index-4 ; i++) {		// save everything between the quotations in a new string (what the user wants to append to the file)
	        char a = input.charAt(i);
	        toReturn += a;
	    }
	    return toReturn;
	}
	
	public static void DoubleArrow(String echoOutput, String fileName) throws IOException {		// appending to file
	    FileWriter toAppend = new FileWriter(fileName, true);
	    toAppend.write(echoOutput);
	    toAppend.flush();
	    toAppend.close();
	}
	
	
	
	// FUNCTION TO KNOW WHICH COMMAND TO RUN OUT OF "echo", "->", and "->>"
    public void EchoCommand(String input) throws IOException{

        if(input.contains("echo") && !input.contains(" ->") && !input.contains(" ->>")) {		// if simple "echo"
		    
        	String toBeDisplayed = echo1(input);		// save the user input String into a variable
		    System.out.println(toBeDisplayed);			// display the input
		}
		
        
		else if (input.contains("echo") && input.contains(" ->") && !input.contains(" ->>") ){ 		// if "->"
		    
			int index = input.indexOf(">");				// find the index of the arrow and save the text file name in a new string
		    String filestring = "";
		    for(int i = index+2 ; i < input.length() ; i++) {
		        char a = input.charAt(i);
		        filestring += a;
		    }
		   
		    String toBeDisplayed = echo2(input, index);		// save the user input String into a variable
		    SingleArrow(toBeDisplayed, filestring);			// write that string into a file determined by the user input
		}
		
		else if(input.contains("echo") && !input.contains("-> ") && input.contains(" ->>")) {		// if "->>"
		    
			int index = input.lastIndexOf(">");		// find the index of the 2nd arrow and save the text file name in a new string
		    String filestring = "";
		    for(int i = index+2 ; i < input.length() ; i++) {
		        char a = input.charAt(i);
		        filestring += a;
		    }
		    
		    String toBeDisplayed = echo3(input, index);		// save the user input String into a variable
		    DoubleArrow(toBeDisplayed, filestring);			// append that string into the file determined by the user input
		}
    }


    public void run() {		//implementing run() method of the thread which will run when thread is started
        
    	boolean condition = true;			// used to continuously run the CLI
        
    	while(condition) {					// while condition is true, keep asking for inputs (keep shell running)
           
    		System.out.print(User+"@"+Host+"$ ");
            String input = sc.nextLine();
            
            String command = "";
            for(int i = 0 ; i < 4 ;i++) {
                char c = input.charAt(i);
                command = command + c;
            }

            switch (command) {
                
            	case "echo":  
                    try {
                        EchoCommand(input);	// if the command is "echo", run the function to find out which version of "echo" to run
                    } catch (IOException e) {								// (simple, single-arrow, double-arrow)
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            		continue;		// go back to checking loop condition
                    
                case "exit":     
                    condition = false;	// if the command in "exit", set condition to false, it breaks the loop and exits the CLI
                        break;      

                        
                
                	
                default: 	// if the input is none of the internal commands, default case will invoke external/included command
                    
                	Execute obj = new Execute(input, PATH);   //created Object of class execute to invoke external/included command
                    Thread myThread = new Thread(obj);  // start new thread using newly created object
                    myThread.start(); 	//start thread execution
                    if (input.charAt(input.length()-2) != '&') //checking if last char of input is & or not; 
                    {										   //if last char is & the thread will execute in the background
                        input = input.substring(0, input.length() - 1);		//removing & from input
                        try {
                            myThread.join();		//joining thread will execute thread in foreground
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }  
                    continue; // go back to checking loop condition
                    
                    
                    
             }     
        }  
    }
}
