public class Process extends Thread {
	// Attributes
	private String PID;
	private int arrival_time;
	private int burst_time;
	private int priority;
	private String state = "Arrived";		// can use boolean but we have more than 2 states
	private int count = 0;
	private int exec_time = 0;
	private Clock clk;
	
	public Process(String id, int a, int b, int p, Clock clk) {
		this.PID = id;
		this.arrival_time = a;
		this.burst_time = b;
		this.priority = p;
		this.clk = clk;
	}
	
	public int getClk() {
		return clk.getValue();
	}

	public int getExec_time() {
		return exec_time;
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

	public int GetPriority() {
		return priority;
	}

	public String GetState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void IncrementCount()
	{
		count++;
	}
    
    public void run() {
		
		while(true)
		{
			try {
				Thread.sleep(10);
				getClk();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
