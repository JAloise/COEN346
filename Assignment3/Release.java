public class Release implements Command {

	public int id;
	
	Release() {};
	
	Release(int i) {
		this.id =i;
	}
	
	public int getID() {
		return this.id;
	}

	public void setID(int i) {
		this.id = i;
	}

}