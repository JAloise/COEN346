import java.util.ArrayList;

public class Page {
    private int K;  //used to determine K for in LRU-K algorithm
    private int timeout;  //time out to be compared with difference of last and hist (timeStampDifference)
    private Variable var = null;   //Variable in the page
    private Integer timeStamp;  //update everytime Page is accessed
    private Integer hist;       //last time access
    private ArrayList<Integer> HistTimeStamp = new ArrayList<Integer>();    //ArrayList of integers holding last K accesses
    private int index;  

    //page class constructor
    Page(Variable var, int K, int timeout) {
        this.K = K;
        this.var = var;
        this.timeout = timeout;
        hist = null;
        index = HistTimeStamp.size();
        TimeStamp();
    }

    public Variable getVar() {
        return var;
    }

    public void setVar(Variable var) {
        this.var = var;
    }

    public int TimeStampDifference() {
        return (timeStamp - HistTimeStamp.get(index-1));
    }

    //method updated Page attributes timestamp and hist whenever page is accessed
    public void TimeStamp() {  
        if(hist == null) {  //if this is the first timestamp update of the page (when method is called in constructor of page)
            timeStamp = MyClock.getInstance().getTime();    //timestamp set to clock value
            hist = timeStamp;   //hist = timestamp
            HistTimeStamp.add(hist);    //add hist to ArrayList
            index++;    //update element index  
        } else {            
            timeStamp = MyClock.getInstance().getTime();
            if(TimeStampDifference() >= timeout) {  //if timestamp >= timeout
                HistTimeStamp.add(timeStamp);       //update using LRU-K scheme
                index++;
                hist = HistTimeStamp.get(index);
                Integer Lcp = timeStamp - hist;
                for(int i = 1; i <= K; i++)
                {
                    hist = HistTimeStamp.get(index-1) + Lcp;
                }
                HistTimeStamp.remove(index);
                HistTimeStamp.add(hist);
            }
        }  
    }
}