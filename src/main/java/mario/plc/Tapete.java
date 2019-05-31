package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.order.Peca;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class Tapete {
    protected String plcCellName;
    protected String plcVariableName;
    protected boolean hasPiece = false;
    Peca pecaNoTapete=new Peca("NAOTEMPECA");
    Peca pecaEsperadaNoTapete=new Peca("NAOESPERAPECA");
    Peca pecaAEnviar=new Peca("NAOTEMPECA");
    protected boolean previousHasPiece = false;
    protected List<Tapete> tapetesAssociados = new ArrayList<>();
    protected Storage armazemAssociado;
    protected Celula celulaFactory;
    private boolean fallingEdge=false;
    private boolean risingEdge=false;



    public Storage getArmazemAssociado() {
        return armazemAssociado;
    }

    public Tapete(int plcCellName) {
        this.plcCellName = "PLC_PRG.C" + plcCellName;
    }

    public Tapete(String plcCellName, String plcVariableName) {
        this.plcCellName = plcCellName;
        this.plcVariableName = plcVariableName;
    }
    public Tapete(String plcCellName, String plcVariableName,Celula celulaFactory) {
        this.plcCellName = plcCellName;
        this.plcVariableName = plcVariableName;
        this.celulaFactory=celulaFactory;
    }


    protected boolean risingEdgePeca() {
        if(pecaEsperadaNoTapete.getTipo().equals("NAOESPERAPECA"))
            return false;

        //System.out.println(pecaEsperadaNoTapete.getTipo());
        if (previousHasPiece == false && hasPiece() == true) {
            if(this.plcVariableName.equals("AT1"))
                System.out.println(pecaEsperadaNoTapete.getTipo());
            previousHasPiece = true;
            if(pecaEsperadaNoTapete==null)
                System.out.println("NULLLLLLLLL");
            pecaNoTapete=pecaEsperadaNoTapete;
            pecaAEnviar=pecaNoTapete;
            System.out.println("Peça "+pecaNoTapete.getTipo()+" no tapete "+this.plcVariableName+" acabaou de chegar!");
          //  pecaEsperadaNoTapete=new Peca("NAOESPERAPECA");


            if(this.plcVariableName.equals("C5T2")) {
                Peca p=new Peca("P1");
                pecaEsperadaNoTapete=p;
                pecaNoTapete = p;
                hasPiece=true;


            }
             if(this.plcVariableName.equals("C5T8")){
                 Peca p=new Peca("P2");
                 pecaEsperadaNoTapete=p;
                 pecaNoTapete=p;
                 hasPiece=true;


            }

            return true;
        }
        return false;
    }

    public void checkFallingAndRisingOrRisingEdge(){
        if(risingEdgePeca()){
            if(plcVariableName.equals("AT1")){
                this.risingEdge=true;
                Storage armazem=getArmazemAssociado();
                armazem.setQuantity(pecaAEnviar.getTipo(),armazem.getQuantity(pecaAEnviar.getTipo())-1);
                System.out.println("Peca "+pecaAEnviar.getTipo()+ " no armazem diminuida para "+armazem.getQuantity(pecaAEnviar.getTipo()));
            }
            this.pecaNoTapete=this.pecaEsperadaNoTapete;
            this.pecaEsperadaNoTapete=new Peca("NAOESPERAPECA");
        }
        if(fallingEdgePeca())
            notifyTapetesAssociados(pecaAEnviar);
    }


    public String getPecaNoTapete() {
        return pecaNoTapete.getTipo();
    }

    public void notifyTapetesAssociados(Peca pecaAEnviar) {

        if (this.plcVariableName.equals("AT1")){
            Storage armazem = getArmazemAssociado();
            //armazem.setQuantity(pecaAEnviar.getTipo(),armazem.getQuantity(pecaAEnviar.getTipo())-1);
            //System.out.println("Peca "+pecaAEnviar.getTipo()+ " no armazem diminuida para "+armazem.getQuantity(pecaAEnviar.getTipo()));
            this.getTapeteDoLado(0).setPecaEsperadaNoTapete(pecaAEnviar);
    }

        if(this.plcVariableName.equals("AT2")){
            Storage armazem=getArmazemAssociado();
            armazem.setQuantity(pecaAEnviar.getTipo(),armazem.getQuantity(pecaAEnviar.getTipo())+1);
            System.out.println("Peca "+pecaAEnviar.getTipo()+ " no armazem aumentada para "+armazem.getQuantity(pecaAEnviar.getTipo()));
            this.getTapeteDoLado(0).setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.contains("T7") ){
           // System.out.println("Peça do tipo "+pecaAEnviar.getTipo()+" saiu do tapete "+this.plcVariableName);
            this.getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.contains("T8") || this.plcVariableName.equals("C5T2") || this.plcVariableName.equals("C5T8")){
            this.getTapeteLadoEsquerdoOuEmCima().setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.equals("C5T1")){
            this.getTapeteEmBaixoDoRotador().setPecaEsperadaNoTapete(pecaAEnviar);
        }

        else if(this.plcVariableName.equals("C5T3")){
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.equals("C5T4")){
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.equals("C5T5")){
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
        }
        else if(this.plcVariableName.equals("C5T6")){
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
        }












        else if(this.plcVariableName.contains("T3")) {
           // System.out.println(pecaAEnviar.getTipo());
            //System.out.println("Peça do tipo " + pecaAEnviar.getTipo() + " saiu do tapete " + this.plcVariableName);
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);
        }
            else if(this.plcVariableName.contains("T1") && !this.plcVariableName.contains("C5") && !this.plcVariableName.contains("A")){
            //System.out.println("Peça do tipo " + pecaAEnviar.getTipo() + " saiu do tapete " + this.plcVariableName);
            this.getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula().setPecaEsperadaNoTapete(pecaAEnviar);


        }


    }

    public boolean fallingEdgePeca(){
        if(pecaNoTapete.equals("NAOTEMPECA") && !plcVariableName.contains("C5"))
            return false;
        if (previousHasPiece == true && hasPiece() == false) {
            if(this.plcVariableName.equals("AT1")) {
                this.risingEdge = false;
                this.fallingEdge=true;
            }
            previousHasPiece = false;
            pecaAEnviar=pecaNoTapete;
            //System.out.println("Peça "+pecaAEnviar.getTipo()+" no tapete "+this.plcVariableName+" acabou de sair!");
            pecaNoTapete=new Peca("NAOTEMPECA");

            return true;
        }
        return false;

    }



    public String getPlcVariableName() {
        return plcVariableName;
    }

    public String getPlcCellName() {
        return plcCellName;
    }

    public void setHasPiece(boolean value) {
        this.hasPiece = value;
    }

    protected void addtapetesAssociado(Tapete tapete) {
        tapetesAssociados.add(tapete);
    }

    public void printAssociados() {
        System.out.println("Celula:"+this.getPlcCellName()+ " Tapete " + plcVariableName + " tem os seguintes tapetes associados:");
        for (Tapete tapete : tapetesAssociados) {
            System.out.println(tapete.getPlcCellName()+ " "+ tapete.plcVariableName);
        }
        System.out.println("----------------");
    }

    private Tapete getTapeteDoLado(int i) {
        try {
            Tapete tapete = tapetesAssociados.get(i);
            //System.out.println(tapete.plcVariableName);
            return tapete;
        } catch (ArrayIndexOutOfBoundsException outofbonds) {
            System.out.println("Out of bonds");
            return null;
        }
    }

    public Tapete getTapeteLadoEsquerdoOuEmCima() {
        return getTapeteDoLado(0);

    }

    public Tapete getTapeteDoLadoDireitoOuEmBaixoSemSerRotatorDeCelula() {
        return getTapeteDoLado(1);
    }

    public Tapete getTapeteEmBaixoDoRotador() {
        return getTapeteDoLado(2);
    }

    public boolean hasPiece() {
        boolean returnValue = Boolean.parseBoolean(OPCUAConnection.getValue(this.plcCellName, plcVariableName));
       /* if (true == returnValue) {
            System.out.println("Tem peça em " + plcVariableName + " do tipo "+ pecaEsperadaNoTapete.getTipo()+"!");
        }*/
        this.hasPiece=returnValue;
        return hasPiece;
    }
    protected void setPecaEsperadaNoTapete(Peca peca){
        //System.out.println("Tapete "+this.plcVariableName +" espera uma peca " +peca.getTipo()+"!");
        this.pecaEsperadaNoTapete =peca;
    }
}




