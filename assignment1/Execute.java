import java.io.*;

public class Execute extends Thread {   //Shell extends thread because this class will execute in its own thread

    private String Filename;
    private String InputString;             
    private boolean ExecutableFound = false;    //variable that will check if input string is a file name or a file's absolute path

    public Execute(String Filename, String InputString){
        this.Filename = Filename;
        this.InputString = InputString;
    }

    public void run() {
        //implementing the included command
        String[] folders = InputString.split(",");//comma-separated list of directories will be split into list of paths and put in an array
        for(int i=0;i<folders.length;i++)       // loop through array of string and get absolut path from relative path
        {                                       // we are using relative path since folder structure is in the same directory as src
            String basePath = new File(folders[i]).getAbsolutePath();    //function that gets absolute path using relative path
            File directory = new File(basePath);                        
            String[] flist = directory.list();      //listing content of each directory and put in an array of strings
            
            //Linear search in the array to see if the Filename variable can be found from the array of strings of each folders's content
            for (int j = 0; j < flist.length; j++) {
                String filename = flist[j];          
                if (filename.equalsIgnoreCase(Filename)) { 
                    System.out.println("file found in: "+basePath); //if file is found; this line will show it's absolute path
                    // The lines of code below are responsible for invoking the executable using its absolute path = "basepath"/"Filename"
                    Runtime runtime = Runtime.getRuntime();        
                    try {
                        Process process = runtime.exec(basePath+"/"+Filename);
                        InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
                        BufferedReader bReader = new BufferedReader(streamReader);
                        bReader.lines().forEach(System.out::println); 
                    } catch(Exception e) {
                        System.out.println("File not Found!");
                    }
                    ExecutableFound = true; //if included command in ran then we change this value to prevent external command from running
                }
            }
        }
        //implementing external command by giving the Filename which is the string containing the absolute path
        if (ExecutableFound == false) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process process = runtime.exec(Filename);
                InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
                BufferedReader bReader = new BufferedReader(streamReader);
                bReader.lines().forEach(System.out::println); 
            } catch(Exception e) {
                System.out.println("File not Found!");
            }
        }                   
    }
}
