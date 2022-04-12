import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MMU extends Thread{

    private int timeout; // memory access time
    private int K; // used for the LRU-K algorithm
    private int NumOfPages; // number of pages 
    private ArrayList<Page> Pages = new ArrayList<Page>(); // ArrayList that holds all the pages in memory
    private Command command; // command being/to be executed

    MMU(int timeout, int K, int NumOfPages) {
        this.timeout = timeout;
        this.K = K;
        this.NumOfPages = NumOfPages;
    }

    public void setCommand(Command c) {
        command = c;
    }

    public void writeToFile(String s){ // method used to write to file
        try {
            FileWriter out = new FileWriter("vm.txt",true);
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void APICall(Command c) { // method used to execute the memory management operation
    	// the getCommands() method returns a string. This string is the name of the command (store, lookup, release)
        String command = c.getCommands();
        // we use a switch case to perform the correct operation based on the string obtained
        switch(command){
            case "Store" : 	// if command = "Store", then perform the store operation
                Store(c);
                break;
            case "Lookup" : // if command = "Lookup", then perform the lookup operation
                Lookup(c);
                break;
            case "Release" : // if command = "Release", then perform the release operation
                Release(c);
                break;
        }
    }

    // Store operation implementation
    public void Store(Command c) {	// receives a command object as an argument
        int id = c.getVarID(); // get the ID of the variable to be stored
        int val = c.getVarValue(); // get the value of the variable to be stored
        
        // check if that variable is already in the memory (Pages ArrayList)
        boolean updated = false;
        if(Pages.size() <= NumOfPages) 
        {
            for ( Page p : Pages) 
            {
                if(id == p.getVar().getID()) {
                    p.getVar().setValue(val);
                    p.TimeStamp();
                    updated = true;
                }
            }
            if(!updated){	// if it is not already in the memory, create a new Page with the variable and add it to the ArrayList
                Page p = new Page(c.variable, K, timeout);
                Pages.add(p);
            }

        } else {	// write to "vm.txt" file
            String ev = "[('"+id+"','"+val+")]";
            writeToFile(ev);
        }
    }

    // Release operation implementation
    public void Release(Command c) { 	// received a command object as an argument
        int id = c.getVarID(); // get the ID of the variable to be released
        Boolean released = false;
        for(Page p: Pages){
            if(p.getVar().getID() == id){ // if variable is found in memory
                Pages.remove(p);    //remove variable from main memory
                released = true;    //variable is removed from main memory
            }
            if(!released)   //if variable is not released from memory: variable in vm.txt file    
            //read vm.txt file and remove variable
            try {
                Scanner sc = new Scanner(new File("vm.txt"));
                StringBuffer sb = new StringBuffer();
                while (sc.hasNextLine()) {
                    String input = sc.nextLine();
                    sb.append(input);
                }
                String result = sb.toString();
                result = result.replaceAll("('"+p.getVar().getID()+"','"+p.getVar().getValue()+"')", "");
                //maybe clear file before overwriting
                //writeToFile(result);
            } catch (FileNotFoundException e) { e.printStackTrace(); }
        }
    }   
    
    public int Lookup(Command c) {
        int index = -1;
        int val = c.getVarValue();
        int id = c.getVarID();
        boolean pageFault = true;
        boolean varExists = true;
        for(Page p : Pages) {
            if(p.getVar().getID() == id) {
                pageFault = false;
                varExists = true;
                index = p.getVar().getValue();
                p.TimeStamp();
            }
        }
        //remove content from output txt.file
        if(pageFault) {
            String input = null;
            Scanner sc;
            try {
                sc = new Scanner(new File("vm.txt"));
                StringBuffer sb = new StringBuffer();
                while (sc.hasNextLine()) {
                    input = sc.nextLine();
                    sb.append(input);
                }
                String result = sb.toString();
                index = sb.indexOf("('"+id+"','"+val+"')");
                if(index == -1) {
                    varExists = false;
                } else {
                    result = result.replaceAll("('"+id+"','"+val+"')", " ");
                }
            } catch (FileNotFoundException e) { e.printStackTrace(); } 
        } 
        ReplacePage();
        if(!varExists) {
            index = -1;
        }
        return index;
    }

    public void ReplacePage() {
        for(Page p : Pages) {
            if(p.TimeStampDifference() > timeout) {
                Pages.remove(0);
            }
        }
    }
    
    // run method simply calls the APICall method
    public void run() {
        APICall(command);
    }
}