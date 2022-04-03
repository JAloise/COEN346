interface Command {
	
	// Interface for "Store", "Lookup", "Release" classes
	
	public int getID();
	public void setID(int i);
	

	
	public String getCommandInfo();
	
	// to be implemented differently in each file depending on the operation
	public void run();
	
}
