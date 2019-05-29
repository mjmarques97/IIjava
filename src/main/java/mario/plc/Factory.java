package mario.plc;

public class Factory {
    private Celula storage=new Storage();
    private Celula celula1=new CelulaFactory(1);
    private Celula celula2=new CelulaFactory(2);
    private Celula celula3=new CelulaFactory(3);
    private Celula celula4=new CelulaFactory(1);
    private Celula unloadCell=new UnloadCell();



}
