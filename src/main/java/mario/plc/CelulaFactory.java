package mario.plc;

import mario.OPCUa.OPCUAConnection;

import java.util.ArrayList;
import java.util.List;

public class CelulaFactory extends Celula {
    private int name;
    private TapeteRotator tapeteRotatorDeCima;
    private TapeteMaquina maquina4;
    private TapeteMaquina maquina5;
    private TapeteMaquina maquina6;
    private Tapete tapeteAEsquerdaDoRotadorDeCima;
    private Tapete tapeteAcimaDaMaquina4;
    private Tapete tapeteRotativoDeBaixo;
    private Tapete tapeteAEsquedaDoRotadorDeBaixo;



    public CelulaFactory(int name) {
        this.name = name;
        tapeteRotatorDeCima = new TapeteRotator(this.name,this);
        maquina4 = new TapeteMaquina(this.name, 4,this);
        maquina5 = new TapeteMaquina(this.name, 5,this);
        maquina6 = new TapeteMaquina(this.name, 6,this);
        tapeteAEsquerdaDoRotadorDeCima = new Tapete("Sensores_Peca", "C" + this.name + "T1",this);
        tapeteAcimaDaMaquina4 = new Tapete("Sensores_Peca", "C" + this.name + "T3",this);
        tapeteRotativoDeBaixo = new Tapete("Sensores_Peca", "C" + this.name + "T7",this);
        tapeteAEsquedaDoRotadorDeBaixo = new Tapete("Sensores_Peca", "C" + this.name + "T8",this);
    }

    public void checkEachCycle(){
        tapeteAEsquerdaDoRotadorDeCima.checkFallingAndRisingOrRisingEdge();
        tapeteRotatorDeCima.checkFallingAndRisingOrRisingEdge();
        maquina4.checkFallingAndRisingOrRisingEdge();
        maquina5.checkFallingAndRisingOrRisingEdge();
        maquina6.checkFallingAndRisingOrRisingEdge();
        tapeteAEsquerdaDoRotadorDeCima.checkFallingAndRisingOrRisingEdge();
        tapeteAcimaDaMaquina4.checkFallingAndRisingOrRisingEdge();
        tapeteRotativoDeBaixo.checkFallingAndRisingOrRisingEdge();
        tapeteAEsquedaDoRotadorDeBaixo.checkFallingAndRisingOrRisingEdge();

    }

    public void setUpCell(){
         tapeteAcimaDaMaquina4.addtapetesAssociado(tapeteRotatorDeCima);
         tapeteAcimaDaMaquina4.addtapetesAssociado(maquina4);

         maquina4.addtapetesAssociado(tapeteAcimaDaMaquina4);
         maquina4.addtapetesAssociado(maquina5);

         maquina5.addtapetesAssociado(maquina4);
         maquina5.addtapetesAssociado(maquina6);

         maquina6.addtapetesAssociado(maquina5);
         maquina6.addtapetesAssociado(tapeteRotativoDeBaixo);

         tapeteRotativoDeBaixo.addtapetesAssociado(tapeteAEsquedaDoRotadorDeBaixo);
         if(name==4) {
             tapeteRotativoDeBaixo.addtapetesAssociado(getUnload().getTapeteAbaixoDoTerceiroPusher());
         }
         else{
             tapeteRotativoDeBaixo.addtapetesAssociado(getCell(name + 1).getTapeteAEsquedaDoRotadorDeBaixo());
         }
         tapeteRotativoDeBaixo.addtapetesAssociado(maquina6);


         tapeteRotatorDeCima.addtapetesAssociado(tapeteAEsquerdaDoRotadorDeCima);
         if(name==4){
             tapeteRotatorDeCima.addtapetesAssociado(getUnload().getTapeteRotatorDeCima());
         }
         else {
             tapeteRotatorDeCima.addtapetesAssociado(getCell(name + 1).getTapeteAEsquerdaDoRotadorDeCima());
         }
         tapeteRotatorDeCima.addtapetesAssociado(tapeteAcimaDaMaquina4);

         if(name==1){
             tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(getStorage().getTapeteUnload());
         }
         else{
             tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(getCell(name-1).tapeteRotatorDeCima);
         }

         tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(tapeteRotatorDeCima);

         if(name==1){
             tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(getStorage().getTapeteLoad());
         }
         else{
             tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(getCell(name-1).getTapeteRotativoDeBaixo());
         }
         tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(tapeteRotativoDeBaixo);










    }

    public int getName() {
        return name;
    }

    public TapeteRotator getTapeteRotatorDeCima() {
        return tapeteRotatorDeCima;
    }

    public Tapete getTapeteAEsquerdaDoRotadorDeCima() {
        return tapeteAEsquerdaDoRotadorDeCima;
    }

    public Tapete getTapeteAcimaDaMaquina4() {
        return tapeteAcimaDaMaquina4;
    }

    public Tapete getTapeteRotativoDeBaixo() {
        return tapeteRotativoDeBaixo;
    }

    public Tapete getTapeteAEsquedaDoRotadorDeBaixo() {
        return tapeteAEsquedaDoRotadorDeBaixo;
    }

    public TapeteMaquina getMaquina4() {
        return maquina4;
    }

    public TapeteMaquina getMaquina5() {
        return maquina5;
    }

    public TapeteMaquina getMaquina6() {
        return maquina6;
    }

    private boolean hasPiece(int tapeteNumber){
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","C"+this.name+"T"+tapeteNumber));
    }

    public boolean hasPieceOnTapeteAEsquerdaDoTapeteRotadorDeCima(){
        return hasPiece(1);
    }

    public boolean hasPieceOnTapeteRotatorDeCima(){
        return hasPiece(2);
    }
    public boolean hasPieceOnTapeteAcimaDaMaquina4(){
        return hasPiece(3);
    }
    public boolean hasPieceOnTapeteAbaixoDoRotadorDeCima(){
        return hasPiece(3);
    }

    public boolean hasPieceOnMaquina4(){
        return hasPiece(4);
    }

    public boolean hasPieceOnMaquina5(){
        this.maquina5.setHasPiece(hasPiece(5));
        return this.maquina5.hasPiece;
    }
    public boolean hasPieceOnMaquina6(){
        this.maquina6.setHasPiece(hasPiece(6));
        return this.maquina6.hasPiece;
    }

    public boolean hasPieceOnTapeteRotadorAbaixoDaMaquina6(){
        return hasPiece(7);
    }
    public boolean hasPieceOnTapeteRotadorDeBaixo(){
        return hasPiece(7);
    }

    public boolean hasPieceOnTapeteAEsquerdaDoRotadorDeBaixo(){
        return hasPiece(8);
    }
    public List<Tapete> retornaTapetes(){
        List<Tapete> lista=new ArrayList<>();

        lista.add(tapeteRotatorDeCima);
        lista.add( maquina4);
        lista.add( maquina5);
        lista.add( maquina6);
        lista.add(tapeteAEsquerdaDoRotadorDeCima);
        lista.add(tapeteAcimaDaMaquina4);
        lista.add( tapeteRotativoDeBaixo);
        lista.add( tapeteAEsquedaDoRotadorDeBaixo);
        return lista;
    }
    public void printAll(){
        for(Tapete tapete: retornaTapetes()){
            tapete.printAssociados();
        }
    }


}
