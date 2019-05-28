package mario.OPCUa;

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
}
