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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setID(int iD) {
        this.ID = iD;
    }
}
