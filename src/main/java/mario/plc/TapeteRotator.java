package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.order.Peca;

public class TapeteRotator extends TapeteDireitaEsquerda {
    private String verificador;

    public TapeteRotator(int plcCellName,CelulaFactory celulaFactory) {
        super(plcCellName, "Top_Rotator_Horizontal");
        this.plcVariableName=super.plcVariableName;
        this.celulaFactory=celulaFactory;
        CelulaFactory celula=(CelulaFactory) this.celulaFactory;
        verificador="C"+celula.getNumber()+"T2";
    }

    public String getVerificador() {
        return verificador;
    }

    @Override
    public boolean hasPiece(){
        CelulaFactory celulaFactory=(CelulaFactory) this.celulaFactory;
        int a=celulaFactory.getNumber();
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","C"+a+"T2"));
    }

    public void moveDown(){
        this.stopMovingRight();
    }

    public void notifyTapetesAssociados(Peca pecaAEnviar){
        CelulaFactory celulaDoTapete=(CelulaFactory) celulaFactory;
        if(this.equals(celulaDoTapete.getTapeteRotatorDeCima())){
            if(this.getMovingRight()){
                getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
                return;
            }
            else{
                getTapeteEmBaixoDoRotador().setPecaEsperadaNoTapete(pecaAEnviar);
            }
        }
    }


}
