import java.util.*;
import java.lang.Thread;

class Test {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) 
    {
        //get inputs
        System.out.println("Enter input: username, hostname & PATH folders");

        String Username = sc.nextLine();
        String Hostname = sc.nextLine();
        String path = sc.nextLine();
        
        //System.out.println("main thread")
        Shell obj = new Shell(Username,Hostname,path);
        // Passing the object to thread in main()
        Thread myThread = new Thread(obj);   
        myThread.start();
        sc.close();    
    }
}
