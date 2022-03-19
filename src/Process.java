public class Process extends Thread {
	// Attributes
	private String PID;
	private int arrival_time, burst_time, priority, timeSlot, waiting_time, resume_time;
	private String state = "";		// can use boolean but we have more than 2 states
	private int Start_time,count = 0; 
	private int exec_time = Start_time - timeSlot;	//exec time = start time - timeslot
	private Clock clk;

	public Process(String id, int a, int b, int p, Clock clk,int timeSlot) {
		this.PID = id;
		this.arrival_time = a;
		this.burst_time = b;
		this.priority = p;
		this.timeSlot = timeSlot;
		this.clk = clk;
	}

	public Process(String id, int a, int b, int p, Clock clk) {
		this.PID = id;
		this.arrival_time = a;
		this.burst_time = b;
		this.priority = p;
		this.clk = clk;
	}
	
	public int GetStart_time(){
		return Start_time;
	}

	public void SetStart_time(int start){
		Start_time = start;
	}

	public int getClk() {
		return clk.getValue();
	}

	public int getExec_time() {
		return exec_time;
	}

	public void setExec_time(int exec) {
		exec_time = exec;
	}

	public void UpdateExec_time(int time_slot) {
		exec_time = exec_time - time_slot;
	}

	// Setters and Getters
	public String getPID() {
		return PID;
	}

	public int getArrival_time() {
		return arrival_time;
	}

	public int getBurst_time() {
		return burst_time;
	}

	public void setBurst_time(int burst) {
		burst_time = burst;
	}

	public void UpdateBurst_time(int exec_time) {
		burst_time = burst_time - exec_time;
	}

	public int GetPriority() {
		return priority;
	}

	public String GetState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getcount() {
		return count;
	}

	public void IncrementCount() {
		count = count +1;
	}
	
    public int getWaiting_time() {
		return waiting_time;
	}

	public void setWaiting_time(int waiting_time) {
		this.waiting_time = waiting_time;
	}

	public int getResume_time() {
		return resume_time;
	}

	public void setResume_time(int resume_time) {
		this.resume_time = resume_time;
	}

	public void run() {
		IncrementCount();
		while(true)
		{
			SetStart_time(getClk());
			try {
				Thread.sleep(10);
				if(getClk() == timeSlot){
					UpdateBurst_time(exec_time);
					setExec_time(timeSlot);
					if(exec_time == burst_time){
						setState("finished");
					} else {
						setState("paused");
					}	
				}
				System.out.println( "Time " + getClk() + ", " + getPID() + ", " + getState() );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}