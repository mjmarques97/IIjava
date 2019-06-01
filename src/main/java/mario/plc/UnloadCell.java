package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.order.Peca;

import java.util.ArrayList;
import java.util.List;

public class UnloadCell extends Celula {
    private Tapete tapeteRotatorDeCima;
    private Tapete tapeteADireitaDoDeCima;
    private Tapete tapeteAcimaDoPrimeiroPusher;
    private Tapete tapetePrimeiroPusher;
    private Tapete tapeteSegundoPusher;
    private Tapete tapeteTerceiroPusher;
    private Tapete tapeteAbaixoDoTerceiroPusher;
    private Tapete tapeteADireitaDoTapeteDeBaixo;
    private List<Tapete> lista=new ArrayList<>();
    private Peca p1=new Peca("P1");
    private Peca p2=new Peca("P2");

    public UnloadCell() {
        this.name="Unload";
        tapeteRotatorDeCima = new Tapete("Sensores_Peca", "C5T1",this);
        tapeteADireitaDoDeCima = new Tapete("Sensores_Peca", "C5T2",this);
        tapeteAcimaDoPrimeiroPusher = new Tapete("Sensores_Peca", "C5T3",this);
        tapetePrimeiroPusher = new Tapete("Sensores_Peca", "C5T4",this);
        tapeteSegundoPusher = new Tapete("Sensores_Peca", "C5T5",this);
        tapeteTerceiroPusher = new Tapete("Sensores_Peca", "C5T6",this);
        tapeteAbaixoDoTerceiroPusher = new Tapete("Sensores_Peca", "C5T7",this);
        tapeteADireitaDoTapeteDeBaixo = new Tapete("Sensores_Peca", "C5T8",this);
        tapeteADireitaDoTapeteDeBaixo.pecaEsperadaNoTapete=new Peca("P2");
        tapeteADireitaDoDeCima.pecaEsperadaNoTapete=new Peca("P1");
        lista.add(tapeteRotatorDeCima);
        lista.add(tapeteADireitaDoDeCima);
        lista.add(tapeteAcimaDoPrimeiroPusher);
        lista.add(tapetePrimeiroPusher);
        lista.add(tapeteSegundoPusher);
        lista.add(tapeteTerceiroPusher);
        lista.add(tapeteAbaixoDoTerceiroPusher);
        lista.add(tapeteADireitaDoTapeteDeBaixo);
    }
    public void checkEachCycle(){
        tapeteADireitaDoDeCima.pecaEsperadaNoTapete=p1;
        tapeteADireitaDoTapeteDeBaixo.pecaEsperadaNoTapete=p2;
        tapeteRotatorDeCima.checkFallingAndRisingOrRisingEdge();
        tapeteADireitaDoDeCima.checkFallingAndRisingOrRisingEdge();
        tapeteAcimaDoPrimeiroPusher.checkFallingAndRisingOrRisingEdge();
        tapetePrimeiroPusher.checkFallingAndRisingOrRisingEdge();
        tapeteSegundoPusher.checkFallingAndRisingOrRisingEdge();
        tapeteTerceiroPusher.checkFallingAndRisingOrRisingEdge();
        tapeteAbaixoDoTerceiroPusher.checkFallingAndRisingOrRisingEdge();
        tapeteADireitaDoTapeteDeBaixo.checkFallingAndRisingOrRisingEdge();

    }



    void setUpCell(){
         tapeteRotatorDeCima.addtapetesAssociado(getCelula4().getTapeteRotatorDeCima());
         tapeteRotatorDeCima.addtapetesAssociado(tapeteADireitaDoDeCima);
         tapeteRotatorDeCima.addtapetesAssociado(tapeteAcimaDoPrimeiroPusher);
         tapeteADireitaDoDeCima.addtapetesAssociado(tapeteRotatorDeCima);
         tapeteADireitaDoTapeteDeBaixo.addtapetesAssociado(tapeteAbaixoDoTerceiroPusher);

         tapeteAcimaDoPrimeiroPusher.addtapetesAssociado(tapeteRotatorDeCima);
         tapeteAcimaDoPrimeiroPusher.addtapetesAssociado(tapetePrimeiroPusher);

         tapetePrimeiroPusher.addtapetesAssociado(tapeteAcimaDoPrimeiroPusher);
         tapetePrimeiroPusher.addtapetesAssociado(tapeteSegundoPusher);

         tapeteSegundoPusher.addtapetesAssociado(tapetePrimeiroPusher);
         tapeteSegundoPusher.addtapetesAssociado(tapeteTerceiroPusher);

         tapeteTerceiroPusher.addtapetesAssociado(tapeteSegundoPusher);
         tapeteTerceiroPusher.addtapetesAssociado(tapeteAbaixoDoTerceiroPusher);

         tapeteAbaixoDoTerceiroPusher.addtapetesAssociado(getCelula4().getTapeteRotativoDeBaixo());
         tapeteAbaixoDoTerceiroPusher.addtapetesAssociado(tapeteADireitaDoTapeteDeBaixo);
         tapeteAbaixoDoTerceiroPusher.addtapetesAssociado(tapeteTerceiroPusher);
    }


    public Tapete getTapeteRotatorDeCima() {
        return tapeteRotatorDeCima;
    }

    public Tapete getTapeteADireitaDoDeCima() {
        return tapeteADireitaDoDeCima;
    }

    public Tapete getTapeteAcimaDoPrimeiroPusher() {
        return tapeteAcimaDoPrimeiroPusher;
    }

    public Tapete getTapetePrimeiroPusher() {
        return tapetePrimeiroPusher;
    }

    public Tapete getTapeteSegundoPusher() {
        return tapeteSegundoPusher;
    }

    public Tapete getTapeteTerceiroPusher() {
        return tapeteTerceiroPusher;
    }

    public Tapete getTapeteAbaixoDoTerceiroPusher() {
        return tapeteAbaixoDoTerceiroPusher;
    }

    public Tapete getTapeteADireitaDoTapeteDeBaixo() {
        return tapeteADireitaDoTapeteDeBaixo;
    }

    public void loadUnload(int cell, boolean value){
        OPCUAConnection.setValue("PLC_PRG.C5","T"+cell+"_expect_unload",value);

    }
    public void loadEEE(String string){
        String a=string.substring(2);
        OPCUAConnection.setValue("PLC_PRG.C5",a+"_expect_unload",true);
    }

    public void unloadEEE(String string){
        String a=string.substring(2);
        OPCUAConnection.setValue("PLC_PRG.C5",a+"_expect_unload",false);
    }


    private String getValue(int cell){
       return OPCUAConnection.getValue("PLC_PRG.C5","T"+cell+"_expect_unload");
    }
    public void unLoad4(){
        loadUnload(4,true);
    }
    public void stopUnLoad4(){
        loadUnload(4,false);
    }
    public String getUnload4(){
        return getValue(4);
    }

    public void unLoad5(){
        loadUnload(5,true);
    }
    public void stopUnLoad5(){
        loadUnload(5,false);
    }
    public String getUnload5(){
        return getValue(5);
    }

    public void unLoad6(){
        loadUnload(6,true);

    }
    public void stopUnLoad6(){
        loadUnload(6,false);
    }

    public String getUnload6(){
        return getValue(5);
    }

    private boolean hasPiece(int tapeteNumber){
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","C5"+"T"+tapeteNumber));
    }

    public boolean hasPieceOnTapeteRotadorDeCima(){
        return hasPiece(1);
    }

    public boolean hasPieceOnTapeteADireitaDoRotadorDeCima(){
        return hasPiece(2);
    }
    public boolean hasPieceOnTapeteAbaixoDoRotadorDeCima(){
        return hasPiece(3);
    }
    public boolean hasPieceOnPrimeiroPusher(){
        return hasPiece(4);
    }

    public boolean hasPieceOnSegundoPusher(){
        return hasPiece(5);
    }
    public boolean hasPieceOnTerceiroPusher(){
        return hasPiece(6);
    }

    public boolean hasPieceOnTapeteRotadorAbaixoDoTerceiroPusher(){
        return hasPiece(7);
    }
    public boolean hasPieceOnTapeteADireitaDoRotadorDeBaixo(){
        return hasPiece(8);
    }

    public List<Tapete> retornaTapetes(){
        List<Tapete> lista=new ArrayList<>();
        lista.add(tapeteRotatorDeCima);
        lista.add(tapeteADireitaDoDeCima);
                lista.add(tapeteAcimaDoPrimeiroPusher);
                        lista.add(tapetePrimeiroPusher);
                                lista.add(tapeteSegundoPusher);
                                lista.add(tapeteTerceiroPusher);
                                                lista.add(tapeteAbaixoDoTerceiroPusher);
                                                        lista.add(tapeteADireitaDoTapeteDeBaixo);
                                                        return lista;
    }



    public void printAll(){
        for(Tapete tapete: lista){
            tapete.printAssociados();
        }
    }
}
