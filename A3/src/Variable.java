public class Variable {
    private int ID; //variable ID
    private int value;  //variable Value

    //Variable class constructor
    Variable(int ID, int value) {
        this.ID = ID;
        this.value = value;
    }

    Variable(int ID) { this.ID = ID; }

    public int getID() { return ID; }

    public void setID(int iD) { ID = iD; }

    public int getValue() { return value; }

    public void setValue(int Value) { value = Value;}
}
