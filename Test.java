import java.util.*;
import java.lang.Runnable;
import java.lang.Thread;

class Test implements Runnable{

    private String User;
    private String Host;
    private String PATH;   

    public Test(String user, String host, String path)
    {
        User = user;
        Host = host;
        PATH = path;
    }

    public void run() 
    {
        System.out.println("executing shell");
        String prompt = User+"@"+Host+"$ ";
        System.out.print(prompt);
        // Scanner Buffer = new Scanner(System.in);
        // while(Buffer.hasNextLine())
        // {
        //     String Command = Buffer.nextLine();
        //     if(Command == "echo") {
        //         String message = "";
        //         for(int i = 0 ; i < Command.length() ;i++)
        //         {
        //             char c = Command.charAt(i); 
        //             if(c == '"')
        //             {
        //                  for(int j = i ; j < Command.length() ;j++)
        //                 {
        //                     if(c != '"')
        //                         message = message + c;
        //                 }
        //             }
        //         }
        //         System.out.print(prompt + message);
        //         System.out.println("shell has executed echo command");
        //     }    
        // }
        System.out.println("exiting shell thread");
        //Buffer.close();   
    }
    public static void main(String[] args) 
    {
        //get inputs
        System.out.println("Enter input: username, hostname & PATH folders");

        Scanner sc = new Scanner(System.in);
        String Username = sc.nextLine();
        String Hostname = sc.nextLine();
        String path = sc.nextLine();
        sc.close();
        
        //System.out.println("main thread")
        Test obj = new Test(Username,Hostname,path);
        // Passing the object to thread in main()
        Thread myThread = new Thread(obj);     
        myThread.start();    
    }
}
