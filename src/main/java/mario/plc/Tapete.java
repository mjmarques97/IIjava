package mario.plc;

public class Tapete{
    protected String piece;
    protected String plcCellName;
    protected String plcVariableName;

    public Tapete(int plcCellName) {
        this.plcCellName ="PLC_PRG.C"+ plcCellName;

    }

    public String getPlcCellName() {
        return plcCellName;
    }
}
