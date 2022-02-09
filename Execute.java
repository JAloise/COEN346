import java.io.*;

public class Execute extends Thread {

    private String Filename;
    private String InputString;
    private boolean ExecutableFound = false;

    String GetFileName(){
        return Filename;
    }

    String GetFilePath(){
        return InputString;
    }

    public Execute(String Filename, String directories){
        this.Filename = Filename;
        InputString = directories;
    }

    public void run() {

        String[] folders = InputString.split(",");
        for(int i=0;i<folders.length;i++)
        {
            String basePath = new File(folders[i]).getAbsolutePath();
            File directory = new File(basePath);
            String[] flist = directory.list();
            
            //Linear search in the array
            for (int j = 0; j < flist.length; j++) {
                String filename = flist[j];
                if (filename.equalsIgnoreCase(Filename)) {
                    System.out.println("file found in: "+basePath);
                    Runtime runtime = Runtime.getRuntime();
                    try {
                        Process process = runtime.exec(basePath+"/"+Filename);
                        InputStreamReader streamReader = new InputStreamReader(process.getInputStream());
                        BufferedReader bReader = new BufferedReader(streamReader);
                        bReader.lines().forEach(System.out::println); 
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    ExecutableFound = true;
                }
            }
        }
        if (ExecutableFound == false) 
                System.out.println("File Not Found");            
    }
}