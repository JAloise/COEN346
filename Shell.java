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
        while(true) {
            System.out.print(User+"@"+Host+"$ ");
            String input = sc.nextLine();
            
            String command = "";
            for(int i = 0 ; i < 4 ;i++) {
                char c = input.charAt(i);
                command = command + c;
            }

            switch (command) {
                case "echo":  
                String message = "";
                for(int i = 0 ; i < input.length() ;i++) {
                    char c = input.charAt(i); 
                    if(c == '"') {
                        for(int j = i ; j < input.length() ;j++) { 
                            if(c != '"') {
                                message = message + c;
                            }
                        }
                    }
                }
                System.out.print(message);        
                    continue;
                    
                case "exit":  command = "exit";
                         break;

                default: command = "Invalid command";
                         continue;
            }       
            //"echo" command
            // if(command== "echo") {
            //     String message = "";
            //     for(int i = 0 ; i < input.length() ;i++) {
            //         char c = input.charAt(i); 
            //         if(c == '"') {
            //             for(int j = i ; j < input.length() ;j++) { 
            //                 if(c != '"') {
            //                     message = message + c;
            //                 }
            //             }
            //         }
            //     }
            //     System.out.print(message);
            // }
            // //"exit" command
            // if (command == "exit"){
            //     return;
            // }   
        }  
    }
}