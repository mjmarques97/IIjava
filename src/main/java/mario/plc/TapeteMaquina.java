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
    private String maquinaAcabou;
    public boolean downDirection=false;
    private boolean imWorking=false;
    boolean stop=false;
    private String tipo;



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TapeteMaquina(int plcCellName, int machineNumber, CelulaFactory celulaFactory) {
        super(plcCellName);
        this.machinenumber=machineNumber;
        this.down="T"+machineNumber+"_direcao_baixo";
        this.esperaPeca="T"+machineNumber+"_espera_peca";
        this.ferramenta="T"+machineNumber+"_ferramenta";
        this.tempoServico="T"+machineNumber+"_tempo_Servico";
        this.plcVariableName="C"+plcCellName+"T"+machineNumber;
        this.celulaFactory=celulaFactory;
        maquinaAcabou="C"+plcCellName+"M"+(machinenumber-3);

    }
    public void setPlcVariableName(String variableName){
        this.plcVariableName=variableName;
    }

    public boolean isImWorking() {
        return imWorking;
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
    public void imWaitingForPiece(){
        OPCUAConnection.setValue(this.plcCellName,esperaPeca,true);
    }


    private void doWork(){
        this.setPiece(true);
        imWorking=true;
    }
    public void stopDoingWork(){
        this.setPiece(false);
       // this.stopDownDirection();

    }
    public void stopEverythingYouAreDoing(){
        stopDoingWork();
        this.stopDownDirection();


    }

    public boolean isWorking(){
        return imWorking;
    }

    public boolean finishedWorking(){
        if(imWorking) {
            Boolean b = Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca", maquinaAcabou));
            if (b == false) {
                return false;
            } else {
                OPCUAConnection.setValue("Sensores_Peca", maquinaAcabou, false);
                stopDoingWork();
                imWorking = false;
                stop=true;
               //System.out.println("Im done");
                return true;

            }
        }
        return true;
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
        if(imWorking)
            return;
        this.doWork();
        this.selectTool(toolNumber);
        this.selectTimeToOperateOnPiece(time);
        this.stopDownDirection();
        imWorking=true;



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
                this.celulaFactory.setReady(true);
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
