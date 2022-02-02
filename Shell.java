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

    String UserInput(){
        //Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        sc.close();
        return command;
    }

    public void run() {
        while(true) {
            System.out.print(User+"@"+Host+"$ ");
            //"exit" command
            if (UserInput() == "exit") 
                break;
            //"echo" command
            if(UserInput()== "echo") {
                String message = "";
                for(int i = 0 ; i < UserInput().length() ;i++) {
                    char c = UserInput().charAt(i); 
                    if(c == '"') 
                        for(int j = i ; j < UserInput().length() ;j++) 
                            if(c != '"')
                                message = message + c;
                }
                System.out.print(message);
            }   
        }
        System.out.println("exiting shell thread");   
    }
}