package mario.plc;

public class SeguidorDePecas {
    private UnloadCell unloadCell;
    Storage storage;
    CelulaFactory c1;
    CelulaFactory c2;
    CelulaFactory c3;
    CelulaFactory c4;
    private final int time=100;
    public void updateAllEachCycle(){
        storage.checkEachCycle();
        c1.checkEachCycle();
        c2.checkEachCycle();
        c3.checkEachCycle();
        c4.checkEachCycle();
        unloadCell.checkEachCycle();
    }

    public SeguidorDePecas() {
         storage=new Storage();
         c1=new CelulaFactory(1);
         c2=new CelulaFactory(2);
         c3=new CelulaFactory(3);
         c4=new CelulaFactory(4);
         unloadCell=new UnloadCell();
        setEverythingUp(storage,c1,c2,c3,c4,unloadCell);
    }

    public static void setEverythingUp(Storage storage, CelulaFactory c1, CelulaFactory c2, CelulaFactory c3, CelulaFactory c4, UnloadCell unloadCell){
        storage.add(storage,c1,c2,c3,c4,unloadCell);
        c1.add(storage,c1,c2,c3,c4,unloadCell);
        c2.add(storage,c1,c2,c3,c4,unloadCell);
        c3.add(storage,c1,c2,c3,c4,unloadCell);
        c4.add(storage,c1,c2,c3,c4,unloadCell);
        unloadCell.add(storage,c1,c2,c3,c4,unloadCell);

        storage.setUp();
        c1.setUpCell();
        c2.setUpCell();
        c3.setUpCell();
        c4.setUpCell();
        unloadCell.setUpCell();

    }

    public UnloadCell getUnloadCell() {
        return unloadCell;
    }

    public Storage getStorage() {
        return storage;
    }

    public CelulaFactory getC1() {
        return c1;
    }

    public CelulaFactory getC2() {
        return c2;
    }

    public CelulaFactory getC3() {
        return c3;
    }

    public CelulaFactory getC4() {
        return c4;
    }
}
