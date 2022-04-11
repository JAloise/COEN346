import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MMU extends Thread{

    private int timeout;
    private int K;
    private int NumOfPages;
    private ArrayList<Page> Pages = new ArrayList<Page>();

    MMU(int timeout, int K, int NumOfPages) {
        this.timeout = timeout;
        this.K = K;
        this.NumOfPages = NumOfPages;
    }

    public void writeToFile(String s){
        try {
            FileWriter out = new FileWriter("vm.txt",true);
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) { e.printStackTrace(); }
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
            if(!updated){
                Page p = new Page(c.variable, K, timeout);
                Pages.add(p);
            }

        } else {
            String ev = "[('"+id+"','"+val+")]";
            writeToFile(ev);
        }
    }

    public void Release(Command c) {
        int id = c.getVarID();
        Boolean released = false;
        for(Page p: Pages){
            if(p.getVar().getID() == id){
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
            if(p.TimeStampDifference() > timeout){
                Pages.remove(0);
            }
        }
    }

}
