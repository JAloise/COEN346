public class Process extends Thread {

    private Clock clock;
	private String PID;
	private int arrival_time; 
    private int burst_time; 
    private int priority;
    private String state;
    private int timeSlot;
	private int Start_time,count = 0;
    private int resume_time; 
	private int exec_time = 0;
	
    Process( String PID, int arrival_time, int burst_time,int priority, Clock clock)
    {
        this.PID = PID;
		this.arrival_time = arrival_time;
		this.burst_time = burst_time;
		this.priority = priority;
        this.clock = clock;
        state = "";
    }

    public int getResume_time() {
        return resume_time;
    }

    public void setResume_time(int resume_time) {
        this.resume_time = resume_time;
    }

    public int GetStart_time(){
		return Start_time;
	}

	public void SetStart_time(int start){
		Start_time = start;
	}

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeslot) {
        timeSlot = timeslot;
    }

    public int getExecTime() {
        return exec_time;
    }

    public void UpdateExec_time(int time_slot) {
		exec_time = exec_time + time_slot;
	}

    public int getcount(){
        return count;
    }
    
    public void IncrementCount() {
        count++;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String GetState() {
        return state;
    }

    public int getClk() {
        return clock.getValue();
    }

    public String getPID() {
        return PID;
    }
    
    public int getArrival_time() {
        return arrival_time;
    }

    public int getBurst_time() {
        return burst_time;
    }

    public void UpdateBurst_time(int exec_time) {
		burst_time = burst_time - exec_time;
	}

    public int GetPriority() {
        return priority;
    }

    public void run() {
        while(true)
		{
			IncrementCount();
			try {
				Thread.sleep(10);
				if(getClk() == (timeSlot + Start_time) ){					
					if(exec_time == burst_time){
						setState("finished");
					} else {
						setState("paused");
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
