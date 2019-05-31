package mario.plc;

import mario.OPCUa.OPCUAConnection;

public class TapeteDireitaEsquerda extends Tapete {
    protected String plcVariableName;

    public TapeteDireitaEsquerda(int plcCellName, String plcVariableName) {
        super(plcCellName);
        this.plcVariableName=plcVariableName;
        super.plcVariableName=this.plcVariableName;

    }

    public void moveRight(){
        OPCUAConnection.setValue(this.plcCellName,this.plcVariableName,true);
    }

    public void stopMovingRight(){
        OPCUAConnection.setValue(this.plcCellName,this.plcVariableName,false);
    }

    public boolean getMovingRight(){
        return Boolean.parseBoolean(OPCUAConnection.getValue(this.plcCellName,this.plcVariableName));
    }

}
