public class Variable {
    private int ID;
    private int value;

    Variable(int ID, int value) {
        this.ID = ID;
        this.value = value;
    }

    Variable(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int Value) {
        value = Value;
    }

    public void setID(int iD) {
        ID = iD;
    }
}
