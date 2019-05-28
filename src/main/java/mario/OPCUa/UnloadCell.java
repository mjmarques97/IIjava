package mario.OPCUa;

public class UnloadCell {
    public void loadUnload(int cell,boolean value){
        OPCUAConnection.setValue("PLC_PRG.C5","T"+cell+"_expect_unload",value);

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
        return Boolean.parseBoolean(OPCUAConnection.getValue("GVL","C5"+"T"+tapeteNumber));
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
}
