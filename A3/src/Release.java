public class Release extends Command {

	@Override
	public int getVarValue() { return 0; }

	@Override
	public void setVarValue(int value) {}

	@Override
    public int getVarID(){ return variable.getID(); }
		
	@Override
    public void setVarID(int var){ variable.setID(var);	}

	@Override
    public String getCommands(){ return command; }

	@Override
    public void setCommands(String command) { this.command = command; }

	Release(Variable variable, String command) {
		this.variable = variable;
		this.command = command;
	}

	
}