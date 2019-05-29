package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.plc.TapeteMaquina;
import mario.plc.TapeteRotator;

public class Celula {
    private int name;
    private TapeteRotator tapeteRotator;
    private TapeteMaquina maquina4;
    private TapeteMaquina maquina5;
    private TapeteMaquina maquina6;

    public Celula(int name) {
        this.name = name;
         tapeteRotator=new TapeteRotator(this.name);
         maquina4=new TapeteMaquina(this.name,4);
         maquina5= new TapeteMaquina(this.name,5);
         maquina6 =new TapeteMaquina(this.name,6);
    }

    public int getName() {
        return name;
    }

    public TapeteRotator getTapeteRotator() {
        return tapeteRotator;
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
        return Boolean.parseBoolean(OPCUAConnection.getValue("GVL","C"+this.name+"T"+tapeteNumber));
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
        return hasPiece(5);
    }
    public boolean hasPieceOnMaquina6(){
        return hasPiece(6);
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


}
