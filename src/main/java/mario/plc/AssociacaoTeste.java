package mario.plc;

public class AssociacaoTeste {
    public static void setEverythingUp(Storage storage,CelulaFactory c1,CelulaFactory c2,CelulaFactory c3,CelulaFactory c4,UnloadCell unloadCell){
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

    public static void main(String[] args) {
        Storage storage=new Storage();
        CelulaFactory c1=new CelulaFactory(1);
        CelulaFactory c2=new CelulaFactory(2);
        CelulaFactory c3=new CelulaFactory(3);
        CelulaFactory c4=new CelulaFactory(4);
        UnloadCell unloadCell=new UnloadCell();
        setEverythingUp(storage,c1,c2,c3,c4,unloadCell);




        c1.printAll();
        c2.printAll();
        c3.printAll();
        c4.printAll();
        unloadCell.printAll();

    }
}
