public class Scheduler extends Thread{

    private int StoreSize;
    private int ReleaseSize;
    private int LookupSize;

    //check in with TA; may I remove " = new Variable[Size];" and do assignment in constructor
    private Variable[] Store = new Variable[StoreSize];
    private Variable[] Release = new Variable[ReleaseSize];
    private Variable[] Lookup = new Variable[LookupSize];;

    private Clock clock;

    Scheduler(int StoreSize,int ReleaseSize,int LookupSize,Variable[] Store,Variable[] Release,Variable[] Lookup,Clock clock) {
        this.StoreSize = StoreSize;
        this.ReleaseSize = ReleaseSize;
        this.LookupSize = LookupSize;
        this.Store = Store;
        this.Release = Release;
        this.Lookup = Lookup;
        this.clock = clock;
    }

    void Store(String VariableID, int Value) {}

    void Release(String VariableID) {}

    void Lookup(String VariableID) {}

    public void run() {
        int time = clock.getValue();
        
    }
}
