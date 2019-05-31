package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.order.Peca;

public class TapeteMaquina extends Tapete {
    private String down;
    private String esperaPeca;
    private String ferramenta;
    private String tempoServico;
    private CelulaFactory celulaFactory;
    private int machinenumber;
    public boolean downDirection=false;

    public TapeteMaquina(int plcCellName,int machineNumber,CelulaFactory celulaFactory) {
        super(plcCellName);
        this.machinenumber=machineNumber;
        this.down="T"+machineNumber+"_direcao_baixo";
        this.esperaPeca="T"+machineNumber+"_espera_peca";
        this.ferramenta="T"+machineNumber+"_ferramenta";
        this.tempoServico="T"+machineNumber+"_tempo_Servico";
        this.plcVariableName="C"+plcCellName+"T"+machineNumber;
        this.celulaFactory=celulaFactory;

    }
    public void setPlcVariableName(String variableName){
        this.plcVariableName=variableName;
    }


    private void setDownDirection(boolean value){
        OPCUAConnection.setValue(this.plcCellName,down,value);
    }
    public void stopDownDirection(){
        this.setDownDirection(false);
        downDirection=false;
    }
    public void goDownDirection(){
        this.setDownDirection(true);
        downDirection=true;
    }
    public Boolean getDownDirection(){
        return downDirection;
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

    @Override
    public void notifyTapetesAssociados(Peca pecaAEnviar) {
        if (pecaAEnviar == null)
            System.out.println("NULLLLL");
        CelulaFactory celulaDoTapete = (CelulaFactory) celulaFactory;
        if (this.equals(celulaDoTapete.getMaquina4())) {
            getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
            return;
        }
        if(this.equals(celulaDoTapete.getMaquina5())){
            if(celulaDoTapete.getMaquina5().downDirection==true){
                getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
                return;
            }
            else if(celulaDoTapete.getMaquina5().downDirection==false){
                getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
            }

        }
        if(this.equals(celulaDoTapete.getMaquina6())){
            if(celulaDoTapete.getMaquina6().downDirection==true){
                getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
                return;
            }
            else if(celulaDoTapete.getMaquina6().downDirection==false){
                getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
            }

        }
    }

            /*if(celulaDoTapete.getMaquina4().getDownDirection() || celulaDoTapete.getMaquina5().getDownDirection() ) {
                this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
                return;
            }
            else  {
                this.getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
                return;
            }
        }
        if(this.equals(celulaDoTapete.getMaquina5())){
            if(celulaDoTapete.getMaquina5().getDownDirection() || celulaDoTapete.getMaquina6().getDownDirection()) {
                this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
            }
            else {
                this.getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
            }
        }

        if(this.equals(celulaDoTapete.getMaquina6())){
            if(celulaDoTapete.getMaquina6().getDownDirection()) {
                this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
            }
            else {
                this.getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
            }
        }*/







    @Override
    public boolean hasPiece(){
        boolean returnValue = Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca", plcVariableName));
        if (true == returnValue) {
        //    System.out.println("Tem peça em " + plcVariableName + " do tipo "+ pecaNoTapete.getTipo()+"!");
        }
        this.hasPiece=returnValue;
        return hasPiece;
    }


}
