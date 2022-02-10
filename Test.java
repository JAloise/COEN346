import java.util.*;
import java.io.*;
import java.lang.Thread;

class Test {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException 
    {
        FileReader fr = new FileReader("inputs.txt");
        BufferedReader br = new BufferedReader(fr);
        String str;
        List<String> fileText = new ArrayList<String>();
        while ((str = br.readLine()) != null) {
            fileText.add(str);
        }
        br.close();

        String Username = fileText.get(0);
        String Hostname = fileText.get(1);
        String path = fileText.get(2);

        //System.out.println("main thread")
        Shell obj = new Shell(Username,Hostname,path);
        // Passing the object to thread in main()
        Thread myThread = new Thread(obj);
        myThread.start();   
        try {
            myThread.join();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sc.close();    
    }
}