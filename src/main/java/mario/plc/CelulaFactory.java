package mario.plc;

import mario.OPCUa.OPCUAConnection;

import java.util.ArrayList;
import java.util.List;

public class CelulaFactory extends Celula {
    private int number;
    private TapeteRotator tapeteRotatorDeCima;
    private TapeteMaquina maquina4;
    private TapeteMaquina maquina5;
    private TapeteMaquina maquina6;
    private Tapete tapeteAEsquerdaDoRotadorDeCima;
    private Tapete tapeteAcimaDaMaquina4;
    private Tapete tapeteRotativoDeBaixo;
    private Tapete tapeteAEsquedaDoRotadorDeBaixo;
    private boolean ready =true;


    public void setReady(boolean readySense){
        this.ready =readySense;
       // System.out.println("PLC_PRG.C"+this.number);
        OPCUAConnection.setValue("PLC_PRG.C"+this.number,"T3_ready_send",readySense);
    }





    public CelulaFactory(int number) {
        this.number = number;
        this.name="C"+number;
        tapeteRotatorDeCima = new TapeteRotator(this.number,this);
        maquina4 = new TapeteMaquina(this.number, 4,this);
        maquina5 = new TapeteMaquina(this.number, 5,this);
        maquina6 = new TapeteMaquina(this.number, 6,this);
        tapeteAEsquerdaDoRotadorDeCima = new Tapete("Sensores_Peca", "C" + this.number + "T1",this);
        tapeteAcimaDaMaquina4 = new Tapete("Sensores_Peca", "C" + this.number + "T3",this);
        tapeteRotativoDeBaixo = new Tapete("Sensores_Peca", "C" + this.number + "T7",this);
        tapeteAEsquedaDoRotadorDeBaixo = new Tapete("Sensores_Peca", "C" + this.number + "T8",this);
        setReady(true);


        if(number==1) {
            maquina4.setTipo("A");
            maquina5.setTipo("B");
            maquina6.setTipo("B");
        }
        if(number==2) {
            maquina4.setTipo("A");
            maquina5.setTipo("A");
            maquina6.setTipo("C");
        }
        if(number==3) {
            maquina4.setTipo("A");
            maquina5.setTipo("B");
            maquina6.setTipo("B");
        }
        if(number==4) {
            maquina4.setTipo("A");
            maquina5.setTipo("A");
            maquina6.setTipo("C");
        }





    }

    public boolean full(){
        if(!getTapeteAcimaDaMaquina4().getPecaNoTapeteTipo().equals("NAOTEMPECA") || !tapeteAcimaDaMaquina4.pecaEsperadaNoTapete.getTipo().equals("NAOESPERAPECA") )
            return true;
            return false;

    }

    public void stopMachines(String string){
        int a=Integer.parseInt(string.substring(3));
        if(a==4) {
            maquina5.stopEverythingYouAreDoing();
            maquina6.stopEverythingYouAreDoing();
        }
       else if(a==5){
            maquina4.stopEverythingYouAreDoing();
            maquina6.stopEverythingYouAreDoing();
        }
         else if (a==6){
            maquina5.stopEverythingYouAreDoing();
            maquina6.stopEverythingYouAreDoing();
        }
         else {
            maquina5.stopEverythingYouAreDoing();
            maquina6.stopEverythingYouAreDoing();
            maquina4.stopEverythingYouAreDoing();
        }
    }




    public TapeteMaquina getTapeteMaquina(String string){

        if(string.equals(maquina4.getPlcVariableName()))
            return maquina4;
        if(string.equals(maquina5.getPlcVariableName()))
            return maquina5;

        if(string.equals(maquina6.getPlcVariableName()))
                return maquina6;

            return null;
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
         if(number ==4) {
             tapeteRotativoDeBaixo.addtapetesAssociado(getUnload().getTapeteAbaixoDoTerceiroPusher());
         }
         else{
             tapeteRotativoDeBaixo.addtapetesAssociado(getCell(number + 1).getTapeteAEsquedaDoRotadorDeBaixo());
         }
         tapeteRotativoDeBaixo.addtapetesAssociado(maquina6);


         tapeteRotatorDeCima.addtapetesAssociado(tapeteAEsquerdaDoRotadorDeCima);
         if(number ==4){
             tapeteRotatorDeCima.addtapetesAssociado(getUnload().getTapeteRotatorDeCima());
         }
         else {
             tapeteRotatorDeCima.addtapetesAssociado(getCell(number + 1).getTapeteAEsquerdaDoRotadorDeCima());
         }
         tapeteRotatorDeCima.addtapetesAssociado(tapeteAcimaDaMaquina4);

         if(number ==1){
             tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(getStorage().getTapeteUnload());
         }
         else{
             tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(getCell(number -1).tapeteRotatorDeCima);
         }

         tapeteAEsquerdaDoRotadorDeCima.addtapetesAssociado(tapeteRotatorDeCima);

         if(number ==1){
             tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(getStorage().getTapeteLoad());
         }
         else{
             tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(getCell(number -1).getTapeteRotativoDeBaixo());
         }
         tapeteAEsquedaDoRotadorDeBaixo.addtapetesAssociado(tapeteRotativoDeBaixo);










    }

    public int getNumber() {
        return number;
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
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","C"+this.number +"T"+tapeteNumber));
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

    @Override
    public String toString() {
        return "CelulaFactory{" +
                "maquina4=" + maquina4.toString() +
                ", maquina5=" + maquina5.toString() +
                ", maquina6=" + maquina6.toString() +
                '}';
    }


}
