public class Process extends Thread {

    //process attributes
    private Clock clock;   // shared variable: clock
	private String PID;    // process ID
	private int arrival_time; //process arrival time; the time at which process becomes available to the scheduler
    private int burst_time;     //process burst time: how long process soends time executing in the CPU
    private int priority;       // process priiority of execution
    private String state;       // state of processes that is manipulated by run implementation of each process thread
    private int timeSlot;       // timeslot allocated to process everytime process is given CPU resources
	private int Start_time;     //the time at which process starts executing
    private int count = 0;      //keeps count of the number of times process has entered CPU
    private int resume_time;    //the time at which a paused process is resumed
	private int exec_time = 0;  //amount of time process has spent in the CPU: to track accumulative execution time
	
    //process constructor
    Process( String PID, int arrival_time, int burst_time,int priority, Clock clock)
    {
        this.PID = PID;
		this.arrival_time = arrival_time;
		this.burst_time = burst_time;
		this.priority = priority;
        this.clock = clock;
        state = "";
    }

    //All Get methods

    //method gets clock value
    public int getClk() {
        return clock.getValue();
    }
    //method gets Process iD
    public String getPID() {
        return PID;
    }        
    //method gets Arrival time
    public int getArrival_time() {
        return arrival_time;
    }
    //method gets Priority
    public int GetPriority() {
        return priority;
    }
    //method gets State
    public String GetState() {
        return state;
    }    
    //method gets Timeslot
    public int getTimeSlot() {
        return timeSlot;
    }
    //method gets Start time
    public int GetStart_time(){
		return Start_time;
	}
    //method gets count
    public int getcount(){
        return count;
    }
    //method gets Resume time
    public int getResume_time() {
        return resume_time;
    }
    //method gets Execution time
    public int getExecTime() {
        return exec_time;
    }

    //All Set methods

    //method sets process state
    public void setState(String state) {
        this.state = state;
    }
    //method Sets Timeslot
    public void setTimeSlot(int timeslot) {
        timeSlot = timeslot;
    }
    //method Sets Start Time
	public void SetStart_time(int start){
		Start_time = start;
	}
    //method Sets Resume Time
    public void setResume_time(int resume_time) {
        this.resume_time = resume_time;
    }
    
    //method Increments count
    public void IncrementCount() {
        count++;
    }
    //execution time is updated by the timeslot given to process everytime process is executing in CPU
    public void UpdateExec_time(int time_slot) {
		exec_time = exec_time + time_slot;
	}

    //run implementation: count is incremented everytime process enters CPU
    public void run() {
        while(true)
		{
			IncrementCount();
			try {
				Thread.sleep(10);
				if(getClk() == (timeSlot + Start_time) ) {  //once the timeslot allocated to a process expires;	
					if(exec_time == burst_time){   //if process execution time is equals the burst time of the proccess
						setState("finished");       //process is finished
					} else {
						setState("paused");         //otherwise process is paused
					}	
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //System.out.println( "Time " + getClk() + ", " + getPID() + ", " + GetState() );
		}
    }
}
