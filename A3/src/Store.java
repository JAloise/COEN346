public class Store extends Command {

	@Override
	public void setVarValue(int val) { variable.setValue(val); }

	@Override
	public int getVarValue() { return variable.getValue(); }

	@Override
    public int getVarID(){ return variable.getID(); }
		
	@Override
    public void setVarID(int var){ variable.setID(var);	}

	@Override
    public String getCommands(){ return command; }

	@Override
    public void setCommands(String command) { this.command = command; }

	Store(Variable variable, String command) {
		this.variable = variable;
		this.command = command;
	}
}
