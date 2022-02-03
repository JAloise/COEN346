import java.util.*;

public class Shell extends Thread {

    private String User;
    private String Host;
    private String PATH;
    public static Scanner sc = new Scanner(System.in);


    public Shell(String user, String host, String path) {
        User = user;
        Host = host;
        PATH = path;
    }

    public void run() {
        boolean cond = true;
    	while(cond == true) {
            System.out.print(User+"@"+Host+"$ ");
            String input = sc.nextLine();
            
            String command = "";
            for(int i = 0 ; i < 4 ;i++) {
                char c = input.charAt(i);
                command = command + c;
            }
            
        

            switch (command) {
                
            	case "echo":  
            		String toAdd = input;
            		String toDisplay = "";
            		for(int i = 6 ; i < toAdd.length()-1 ; i++) {
            			char c = toAdd.charAt(i);
            			toDisplay += c;
            		}
            		System.out.println(toDisplay);
            		continue;
                    
                case "exit":  
                         cond = false;
                         break;

                default: 
                		command = "Invalid command";
                        continue;
            }       
          }  
        }
}