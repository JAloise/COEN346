import java.util.*;
import java.io.*;
import java.lang.Thread;

class Test {
    public static void main(String[] args) throws IOException 
    {
        FileReader fr = new FileReader("inputs.txt");      // Reading inputs from file called "input.txt" in same folder as src
        BufferedReader br = new BufferedReader(fr);
        String str;
        List<String> fileText = new ArrayList<String>();   // placing each line of the input file in a list; 
        while ((str = br.readLine()) != null) {            
            fileText.add(str);
        }
        br.close();
        //accessing each list element using it index
        String Username = fileText.get(0);
        String Hostname = fileText.get(1);
        String path = fileText.get(2);

        //create object of shell class passing inputs to constructor
        Shell shell = new Shell(Username,Hostname,path);
        // Passing the object of calls Shell to thread in main()
        Thread ShellThread = new Thread(shell);
        ShellThread.start();   
        try {
            ShellThread.join(); //joining thread will wait for the shell thread to finish executing before resuming the main function
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}