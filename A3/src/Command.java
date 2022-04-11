public abstract class Command {
	//public abstract class that is extended by children classes corresponding to different Command types
	protected Variable variable;	//variable associated with command
	protected String command;		//command type 
	//abstract getter/setter methods for attributes
	public abstract int getVarValue();
	public abstract void setVarValue(int value);
    public abstract int getVarID();
	public abstract void setVarID(int ID);
	public abstract String getCommands();
	public abstract void setCommands(String command);
	public abstract String printType();	//print command type
}