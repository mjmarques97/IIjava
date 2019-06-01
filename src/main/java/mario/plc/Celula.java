package mario.plc;

import java.util.ArrayList;
import java.util.List;

public abstract class Celula {
    protected String name;

    public String getName() {
        return name;
    }


    protected List<Celula> celulaList=new ArrayList<>();

    public List<Celula> getCelulaList() {
        return celulaList;
    }

    public void setCelulaList(List<Celula> celulaList) {
        this.celulaList = celulaList;
    }
    public void addToList(Celula celula){
        this.celulaList.add(celula);
    }
    public Storage getStorage(){
        return (Storage) celulaList.get(0);
    }

    public CelulaFactory getCelula1(){
        return (CelulaFactory) celulaList.get(1);
    }
    public CelulaFactory getCelula2(){
        return (CelulaFactory) celulaList.get(2);
    }
    public CelulaFactory getCelula3(){
        return (CelulaFactory) celulaList.get(3);
    }
    public CelulaFactory getCelula4(){
        return (CelulaFactory) celulaList.get(4);
    }
    public UnloadCell getUnload(){
        return (UnloadCell) celulaList.get(5);
    }

    public CelulaFactory getCell(int number){
        switch (number){
            case 1:
                return getCelula1();
            case 2:
                return getCelula2();
            case 3:
                return getCelula3();
            case 4:
                return getCelula4();


        }
        return null;
    }

    public void add(Storage storage,CelulaFactory c1,CelulaFactory c2,CelulaFactory c3,CelulaFactory c4,UnloadCell unloadCell){
        addToList(storage);
        addToList(c1);
        addToList(c2);
        addToList(c3);
        addToList(c4);
        addToList(unloadCell);
    }

}
