package mario.plc;

import java.util.ArrayList;
import java.util.List;

public class Tapete{
    protected String piece;
    protected String plcCellName;
    protected String plcVariableName;
    protected boolean hasPiece;
    protected List<Tapete> tapetesAssociados=new ArrayList<>();

    public Tapete(int plcCellName) {
        this.plcCellName ="PLC_PRG.C"+ plcCellName;
    }

    public Tapete(String plcCellName,String plcVariableName){
        this.plcCellName=plcCellName;
        this.plcVariableName=plcVariableName;
    }

    public String getPlcCellName() {
        return plcCellName;
    }

    public void setHasPiece(boolean value){
        this.hasPiece=value;
    }

    public void addtapetesAssociado(Tapete tapete){
        tapetesAssociados.add(tapete);
    }

    public void printAssociados(){
        System.out.println("Tapete "+plcVariableName +"tem os seguintes tapetes associados:");
        for(Tapete tapete: tapetesAssociados){
            System.out.println(tapete.plcVariableName);
        }
        System.out.println("----------------");
    }
}
