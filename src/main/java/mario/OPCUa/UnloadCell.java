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
}
