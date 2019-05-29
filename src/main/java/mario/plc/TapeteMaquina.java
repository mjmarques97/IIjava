package mario.plc;

import mario.OPCUa.OPCUAConnection;

public class TapeteMaquina extends Tapete {
    private String down;
    private String esperaPeca;
    private String ferramenta;
    private String tempoServico;
    public TapeteMaquina(int plcCellName,int machineNumber) {
        super(plcCellName);
        this.down="T"+machineNumber+"_direcao_baixo";
        this.esperaPeca="T"+machineNumber+"_espera_peca";
        this.ferramenta="T"+machineNumber+"_ferramenta";
        this.tempoServico="T"+machineNumber+"_tempo_Servico";

    }
    public void setPlcVariableName(){

    }


    private void setDownDirection(boolean value){
        OPCUAConnection.setValue(this.plcCellName,down,value);
    }
    public void stopDownDirection(){
        this.setDownDirection(false);
    }
    public void goDownDirection(){
        this.setDownDirection(true);
    }
    public String getDownDirection(){
        return OPCUAConnection.getValue(this.plcCellName,down);
    }

    private void setPiece(boolean value){
        OPCUAConnection.setValue(this.plcCellName,esperaPeca,value);
    }
    private void doWork(){
        this.setPiece(true);
    }
    public void stopDoingWork(){
        this.setPiece(false);
    }
    public String isWorking(){
        return OPCUAConnection.getValue(this.plcCellName,esperaPeca);
    }

    private void selectTool(int toolNumber){
        OPCUAConnection.setValue(this.plcCellName,ferramenta,toolNumber);
    }
    public String getTool(){
        return OPCUAConnection.getValue(this.plcCellName,ferramenta);
    }

    private void selectTimeToOperateOnPiece(int time){
        OPCUAConnection.setValue(this.plcCellName,tempoServico,time);
    }

    public String getTimeToOperateOnPiece(){
        return OPCUAConnection.getValue(this.plcCellName,tempoServico);
    }

    public void getToWork(int toolNumber, int time){
        this.doWork();
        this.selectTimeToOperateOnPiece(time);
        this.selectTool(toolNumber);
        this.stopDownDirection();
    }


}
