import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MMU extends Thread{

    private int timeout;
    private int K;
    private int NumOfPages;
    private ArrayList<Command> commands;
    private ArrayList<Variable> Pages;

    MMU(int timeout, int K, int NumOfPages, ArrayList<Command> commands) {
        this.timeout = timeout;
        this.K = K;
        this.NumOfPages = NumOfPages;
        this.commands = commands;
    }

    public void APICall(Command c) {
        String command = c.getCommands();
        switch(command){
            case "Store" : 
                Store(c);
                break;
            case "Lookup" :
                Lookup(c);
                break;
            case "Release" :
                Release(c);
                break;
        }
    }

    public void Store(Command c) {
        int id = c.getVarID();
        int val = c.getVarValue();
        
        if(Pages.size() <= NumOfPages) 
        {
            for ( Variable v : Pages) 
            {
                if(id == v.getID()) {
                    v.setValue(val);
                }
                else {
                    Pages.add(new Variable(id, val));
                }
            }
        } else {
            String ev = "[('"+id+"','"+val+")]";
            try {
                writeToFile(ev);
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void Release(Command c) {
        int val = c.getVarValue();
        int id = c.getVarID();
        Boolean released = false;
        for(Variable v: Pages){
            if(v.getID() == id){
                Pages.remove(v);
                released = true;
            }
        }
        if(!released){
            String input = null;
            Scanner sc;
            try {
                sc = new Scanner(new File("output.txt"));
                StringBuffer sb = new StringBuffer();
                while (sc.hasNextLine()) {
                    input = sc.nextLine();
                    sb.append(input);
                }
                String result = sb.toString();
                result = result.replaceAll("('"+id+"','"+val+"')", "");
            } catch (FileNotFoundException e) { e.printStackTrace(); }
        }
    }

    public int Lookup(Command c) {
        boolean pageFault = false;
        // for(Variable v : HIST) {
        //     if(v.getID() == VarId) {
        //         return v.getValue();
        //     }
        //     else { 
        //         pageFault = true; 
        //         //remove content from output txt.file
        //         //swap variable with a variable in the main memory pages
        //         //swap variable is done using LRU-k
        //     }
        // }
        return -1;
    }

    public void writeToFile(String s) throws IOException {
        FileWriter out = new FileWriter("output.txt",true);
        out.write(s);
        out.flush();
        out.close();
    }

    public void run() {

        for(Command c : commands) {
            String commandType = c.getCommands();
            switch(commandType){
                case "Store" : 
                    Store(c);
                    break;
                case "Lookup" :
                    Lookup(c);
                    break;
                case "Release" :
                    Release(c);
                    break;
            }
        }
    }
}