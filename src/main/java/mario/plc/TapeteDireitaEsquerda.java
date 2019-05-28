package mario.plc;

import mario.OPCUa.OPCUAConnection;

public class TapeteDireitaEsquerda extends Tapete {
    private String plcVariableName;

    public TapeteDireitaEsquerda(int plcCellName, String plcVariableName) {
        super(plcCellName);
        this.plcVariableName=plcVariableName;
    }

    public void moveRight(){
        OPCUAConnection.setValue(this.plcCellName,this.plcVariableName,true);
    }

    public void stopMovingRight(){
        OPCUAConnection.setValue(this.plcCellName,this.plcVariableName,false);
    }

    public String getMovingRight(){
        return OPCUAConnection.getValue(this.plcCellName,this.plcVariableName);
    }

}
