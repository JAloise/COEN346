public class Variable {
    private int ID;
    private int value;

    Variable(int ID, int value) {
        this.ID = ID;
        this.value = value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int Value) {
        value = Value;
    }
}
