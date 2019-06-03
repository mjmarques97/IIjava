package mario.order;

import com.google.common.collect.Lists;
import mario.OPCUa.OPCUAConnection;
import mario.graph.ListaDeListaInstrucoes;
import mario.plc.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Peca {
    private String tipo;
    private Order order;
    private Tapete tapete;
    private Tapete tapeteParaOndeVai;
    private String nomeDaCelulaParaOndeVai ="JACHEGOU";
    private String whereToUnload;
    private Celula celulaParaOndeVai;
    private boolean imWorking=false;
    private TapeteMaquina whereIshouldWork;

    private boolean firstTime=true;

    private  List<InstrucaoTransformacoes> instrucoes;


    public List<InstrucaoTransformacoes> getInstrucoes() {
        return instrucoes;
    }

    public void setPieceComida(Peca peca){
        this.instrucoes.get(0).setPecaInicial(peca);
        peca.setInstrucoes(this.instrucoes);
    }

    private void setArrayList(InstrucaoTransformacoes[] instrucaoTransformacoes){
        this.instrucoes=new ArrayList<InstrucaoTransformacoes>(Arrays.asList(instrucaoTransformacoes));
    }

    public void setWhereToUnload(String whereToUnload) {
       String string=whereToUnload.substring(1);
      this.whereToUnload="C5"+"T"+(Integer.parseInt(string)+3);
    }

    public void setCelulaParaOndeVai(Celula celula){
        this.celulaParaOndeVai =celula;
    }

    public void setNomeDaCelulaParaOndeVai(Celula celulaParaOndeVai){
        this.celulaParaOndeVai=celulaParaOndeVai;
        if (this.celulaParaOndeVai instanceof UnloadCell) {
            this.nomeDaCelulaParaOndeVai = "C5";
        }
        else {
            this.nomeDaCelulaParaOndeVai =(celulaParaOndeVai).getName();
        }
    }
    public void setNomeDaCelulaParaOndeVai(String nome){
        this.nomeDaCelulaParaOndeVai=nome;
    }


    public String getNomeDaCelulaParaOndeVai() {
       return celulaParaOndeVai.getName();
    }

    public Celula getCelulaParaOndeVai() {
        return celulaParaOndeVai;
    }

    public void checkJaChegou(){
        if (nomeDaCelulaParaOndeVai.equals("JACHEGOU") || tapeteParaOndeVai==null){
            return;
        }


        if(tapeteParaOndeVai instanceof TapeteRotator) {
            TapeteRotator tapeteRotator = (TapeteRotator) tapeteParaOndeVai;
            if (tapeteRotator.getVerificador().equals(nomeDaCelulaParaOndeVai + "T2")) {
                tapeteRotator.moveDown();

                nomeDaCelulaParaOndeVai="JACHEGOU";
                if(firstTime){
                    CelulaFactory celulaFactory=(CelulaFactory) celulaParaOndeVai;
                    celulaFactory.getMaquina4().goDownDirection();
                    celulaFactory.getMaquina5().goDownDirection();
                    celulaFactory.getMaquina6().goDownDirection();
                    firstTime=false;
                }

                return;
            } else {
                tapeteRotator.moveRight();
                return;
            }
        }



            if (tapeteParaOndeVai.getPlcVariableName().equals("C5T1") || order==null){
                nomeDaCelulaParaOndeVai ="JACHEGOU";

            }

            if(order==null && nomeDaCelulaParaOndeVai.equals("JACHEGOU")){
                UnloadCell unloadCell=(UnloadCell) this.celulaParaOndeVai;
            }
        }









    public void printPecaNoTapete(){
        if(order!=null) {
            System.out.println("Peça do Tipo " + this.getTipo() + " com a ordem n: " + this.getOrder().getNumber() + " acabou de chegar ao tapete " + this.getTapete().getPlcVariableName());
            return;
        }
        System.out.println("Peça do Tipo " + this.getTipo() + " sem ordem acabou de chegar ao tapete " + this.getTapete().getPlcVariableName());

    }

    public Peca(int tipo, Tapete tapete){
        this.tipo="P"+tipo;
        this.tapeteParaOndeVai=tapete;
    }

    public Peca(String tipo,Tapete tapete) {
        this.tipo = tipo.toUpperCase();
        this.tapeteParaOndeVai=tapete;
    }

    public Tapete getTapeteParaOndeVai() {
        return tapeteParaOndeVai;
    }

    public void setTapeteParaOndeVai(Tapete tapeteParaOndeVai) {
        this.tapeteParaOndeVai = tapeteParaOndeVai;
    }

    //VER MELHOR DESPUES
    public Peca(String name){
        this.tipo=name;
    }
    public String getTipo() {
        return tipo;
    }

    public Order getOrder() {
        return order;
    }

    public void changeType(String tipo){
        this.tipo=tipo;
    }

    public void setTapete(Tapete tapete) {
        this.tapete = tapete;
    }

    public Tapete getTapete() {
        return tapete;
    }

    public Peca(String tipo, Tapete tapete, Order order) {
        this.tipo = tipo.toUpperCase();
        this.order = order;
        this.tapete=tapete;
    }
    public Peca(int tipo, Tapete tapete, Order order){
        this.tipo = "P"+tipo;
        this.order = order;
        this.tapete=tapete;

    }
    private boolean getTapeteMaquina(TapeteMaquina tapeteMaquina,int i){
        if(tapeteMaquina.getPlcVariableName().equals(this.nomeDaCelulaParaOndeVai+"T"+i))
            return true;
        return false;
    }

    private boolean instrucaoCheck(InstrucaoTransformacoes instrucaoTransformacoes,int i){
        if (instrucaoTransformacoes.getMaquina().contains(String.valueOf(i)))
                return true;
        return false;
    }
    private void doNextPhase(TapeteMaquina tapeteMaquina,CelulaFactory celulaFactory) {
        if (instrucoes.size() > 1) {
            // System.out.println(instrucoes.size());
            instrucoes.get(1).setPecaInicial(instrucoes.get(0).getPecaInicial());
            instrucoes.get(1).descobrirTapete();
        }


        instrucoes.remove(0);


        if (instrucoes.isEmpty()) {
            return;
        }
        /*System.out.println("Tapete a ir: "+ instrucoes.get(1).descobrirTapete());
            if(celulaFactory.getMaquina5().equals(tapeteMaquina)){
               // System.out.println("MoveDown");
                celulaFactory.getMaquina5().goDownDirection();
                celulaFactory.getMaquina4().goDownDirection();
                celulaFactory.getMaquina6().goDownDirection();
                return;
            }

            if(celulaFactory.getMaquina6().equals(tapeteMaquina)){
               // System.out.println("MoveDown");
                celulaFactory.getMaquina6().goDownDirection();
                celulaFactory.getMaquina5().stopDoingWork();
                celulaFactory.getMaquina5().goDownDirection();
                celulaFactory.getMaquina4().stopDoingWork();
                celulaFactory.getMaquina4().goDownDirection();
                return;
            }

            if(celulaFactory.getMaquina4().equals(tapeteMaquina)){
               // System.out.println("MoveDown");
                celulaFactory.getMaquina4().goDownDirection();
                return;
            }*/

        String tapete = instrucoes.get(0).descobrirTapete();
        String tapeteToGo = this.celulaParaOndeVai.getName() + tapete.substring(2);
        //String tapeteToGo=this.getNomeDaCelulaParaOndeVai();


        // System.out.println(instrucoes.get(0).toString());
        //tapeteMaquina.stopEverythingYouAreDoing();

        //System.out.println(tapeteToGo.getPlcVariableName());
        //tapeteToGo.imWaitingForPiece();
       // tapeteMaquina.setPecaNoTapete(tapeteMaquina.getPecaAEnviar());
         //tapeteMaquina.getPecaAEnviar().setTapete(tapeteMaquina);


        if (tapeteMaquina.getPlcVariableName().equals(celulaFactory.getMaquina4().getPlcVariableName())) {
            System.out.println(tapeteToGo + "  " + "Este tapete: " + tapeteMaquina.getPlcVariableName());
            if (tapeteMaquina.getPlcVariableName().equals(tapeteToGo)) {
                if(tapeteMaquina.finishedWorking())
                tapeteMaquina.getToWork(instrucoes.get(0).getFerramenta(), instrucoes.get(0).getTempo());
                celulaFactory.stopMachines("C1T4");
                return;
            } else if (tapeteMaquina.equals(celulaFactory.getMaquina5())) {

                if (celulaFactory.getMaquina5().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina4().goDownDirection();
                    celulaFactory.getMaquina5().getToWork(instrucoes.get(0).getFerramenta(), instrucoes.get(0).getTempo());
                    celulaFactory.getMaquina6().stopEverythingYouAreDoing();
                    return;
                }
                if (celulaFactory.getMaquina6().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina4().goDownDirection();
                    celulaFactory.getMaquina5().goDownDirection();
                    celulaFactory.getMaquina6().getToWork(instrucoes.get(0).getFerramenta(), instrucoes.get(0).getTempo());

                    return;
                }
            }
        }

        if(tapeteMaquina.getPlcVariableName().equals(celulaFactory.getMaquina5().getPlcVariableName())){
            System.out.println(tapeteToGo + "  " + "Este tapete: " + tapeteMaquina.getPlcVariableName());
        if (tapeteMaquina.equals(celulaFactory.getMaquina5())) {
            if (tapeteMaquina.getPlcVariableName().equals(tapeteToGo)) {
                tapeteMaquina.getToWork(instrucoes.get(0).getFerramenta(), instrucoes.get(0).getTempo());
                celulaFactory.stopMachines("C1T5");
                return;
            } else {
                if (celulaFactory.getMaquina4().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina5().stopEverythingYouAreDoing();
                    celulaFactory.getMaquina6().stopEverythingYouAreDoing();
                    celulaFactory.getMaquina4().getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());

                }

                if (celulaFactory.getMaquina6().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina6().getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());
                    celulaFactory.getMaquina4().stopEverythingYouAreDoing();
                    celulaFactory.getMaquina5().goDownDirection();
                }
            }
        }
    }

        if(tapeteMaquina.equals(celulaFactory.getMaquina6())){
           // System.out.println(tapeteToGo + "  " + "Este tapete: " + tapeteMaquina.getPlcVariableName());
           // System.out.println("6");
            if(tapeteMaquina.getPlcVariableName().equals(tapeteToGo)){
                //System.out.println("Stay");
                tapeteMaquina.getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());
                celulaFactory.stopMachines("C1T6");
                return;
            }

            else {
                if (celulaFactory.getMaquina4().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina4().getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());
                    //celulaFactory.getMaquina5().getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());
                    celulaFactory.getMaquina6().stopEverythingYouAreDoing();
                    celulaFactory.getMaquina5().stopEverythingYouAreDoing();
                }

                if (celulaFactory.getMaquina5().getPlcVariableName().equals(tapeteToGo)) {
                    celulaFactory.getMaquina5().getToWork(instrucoes.get(0).getFerramenta(),instrucoes.get(0).getTempo());
                    celulaFactory.getMaquina4().stopEverythingYouAreDoing();
                    celulaFactory.getMaquina6().stopEverythingYouAreDoing();
                }
            }



        }


        //tapeteToGo.stopDoingWork();


    }
    private void moveAllDown(CelulaFactory celulaFactory){
        celulaFactory.getMaquina4().goDownDirection();
        celulaFactory.getMaquina5().goDownDirection();
        celulaFactory.getMaquina6().goDownDirection();
    }

    public void processaInstrucao(){
       // int pecaFinal=Integer.parseInt(instrucoes.get(0).getPecaFinal().substring(1));
        if(!nomeDaCelulaParaOndeVai.equals("JACHEGOU")) {
            //System.out.println(" Nao Cheguei");
            return;
        }
        if(order instanceof TransformationOrder) {
            CelulaFactory celulaFactory = (CelulaFactory) celulaParaOndeVai;
            if (instrucoes.isEmpty()) {
                celulaFactory.getMaquina4().goDownDirection();
                celulaFactory.getMaquina5().goDownDirection();
                celulaFactory.getMaquina6().goDownDirection();
                return;

            }
            int pecaFinal = Integer.parseInt(instrucoes.get(0).getPecaFinal().substring(1));

            InstrucaoTransformacoes instrucao = instrucoes.get(0);
            if (instrucao == null)
                System.out.println("NULLLLLLLLLL");

            String tapeteMaquinaToGo = instrucoes.get(0).descobrirTapete();
            tapeteMaquinaToGo = celulaFactory.getName() + tapeteMaquinaToGo.substring(2);

            whereIshouldWork=celulaFactory.getTapeteMaquina(tapeteMaquinaToGo);

            if (tapeteParaOndeVai instanceof TapeteMaquina) {
                TapeteMaquina tapeteMaquina = (TapeteMaquina) tapeteParaOndeVai;
                if (tapeteMaquina != null) {


                    //System.out.println("Tapete para onde deve ir " + tapeteMaquinaToGo + " Este Tapete a ir: " + tapeteMaquina.getPlcVariableName());
                    if (tapeteMaquina.getPlcVariableName().equals(tapeteMaquinaToGo)) {
                        imWorking = true;
                        System.out.println("Chegou ao tapete");
                       // celulaFactory.stopMachines(tapeteMaquinaToGo);
                        tapeteMaquina.getToWork(instrucao.getFerramenta(), instrucao.getTempo());
                        celulaFactory.stopMachines(tapeteMaquinaToGo);
                        pecaFinal = Integer.parseInt(instrucao.getPecaFinal().substring(1));
                        //doNextPhase(tapeteMaquina,celulaFactory);
                        //tapeteMaquina.stopDownDirection();
                        //doNextPhase(tapeteMaquina, celulaFactory);
                        //celulaFactory.getMaquina4().stopEverythingYouAreDoing();
                    }
                } else {
                     tapeteMaquina.stopDoingWork();
                     tapeteMaquina.goDownDirection();

                }
            }


            if (tapete instanceof TapeteMaquina) {
                TapeteMaquina tapeteMaquina = (TapeteMaquina) tapete;
                if (tapeteMaquina != null) {
                   // System.out.println("Este Tapete: " + tapeteMaquina.getPlcVariableName());
                    if (tapeteMaquina.getPlcVariableName().equals(tapeteMaquinaToGo)) {
                        if (tapeteMaquina.finishedWorking()) {
                            this.changeType("P" + pecaFinal);
                            //System.out.println("Nova peca: "+instrucao.getPecaFinal());
                            doNextPhase(tapeteMaquina, celulaFactory);
                            imWorking=false;
                            //System.out.println(instrucao.toString());
                            return;

                        }
                    }
                }
            }
        }








        if(tapeteParaOndeVai!=null || order==null){


            //System.out.println(tapeteParaOndeVai.getPlcVariableName());

            if(tapeteParaOndeVai.getCelulaFactory() instanceof CelulaFactory || tapeteParaOndeVai.getPlcVariableName().equals("AT2")){
                return;
            }

           // System.out.println(tapeteParaOndeVai.getPlcVariableName());


            UnloadCell unload=(UnloadCell) tapeteParaOndeVai.getCelulaFactory();

            if(tapeteParaOndeVai.getPlcVariableName().equals("C5T4")){
                OPCUAConnection.setValue("PLC_PRG.C5","T4_expect_unload",false);
                return;
            }
            if(tapeteParaOndeVai.getPlcVariableName().equals("C5T5")){
                OPCUAConnection.setValue("PLC_PRG.C5","T5_expect_unload",false);
                return;
            }
            if(tapeteParaOndeVai.getPlcVariableName().equals("C5T6")){
                OPCUAConnection.setValue("PLC_PRG.C5","T6_expect_unload",false);
                unload.stopUnLoad6();
                return;
            }

        }



        if(order instanceof UnloadOrder){
            UnloadCell unloadCell=(UnloadCell) celulaParaOndeVai;

            if(tapeteParaOndeVai.getPlcVariableName().equals(whereToUnload)){
               unloadCell.loadEEE(whereToUnload);
                return;
            }
            else {

                unloadCell.unloadEEE(tapeteParaOndeVai.getPlcVariableName());
                return;
            }
        }

    }

    public void setInstrucoes(List<InstrucaoTransformacoes> instrucoes) {
       InstrucaoTransformacoes[] array=new InstrucaoTransformacoes[instrucoes.size()];
       array=instrucoes.toArray(array);
       setArrayList(array);

    }
}
