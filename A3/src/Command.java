public abstract class Command{
	
	protected Variable variable;
	protected String command;
	public abstract int getVarValue();
	public abstract void setVarValue(int value);
    public abstract int getVarID();
	public abstract void setVarID(int ID);
	public abstract String getCommands();
	public abstract void setCommands(String command);
}