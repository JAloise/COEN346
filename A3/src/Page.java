import java.util.ArrayList;

public class Page {
    private int K;
    private int timeout;
    private Variable var;
    private Integer timeStamp;
    private Integer hist;
    private ArrayList<Integer> HistTimeStamp = new ArrayList<Integer>();
    private int index;

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

    public void TimeStamp() {  
        if(hist == null) {
            timeStamp = MyClock.getInstance().getTime();
            hist = timeStamp;
            HistTimeStamp.add(hist);
            index++;
        } else {
            timeStamp = MyClock.getInstance().getTime();
            if(TimeStampDifference() >= timeout) {
                HistTimeStamp.add(timeStamp);
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