package iiAplication;

import mario.OPCUa.OPCUAConnection;
import mario.OPCUa.OrderManager;
import mario.graph.GraphMES;
import mario.graph.ListaDeListaInstrucoes;
import mario.order.InstrucaoTransformacoes;
import mario.order.Peca;
import mario.order.TransformationOrder;
import mario.order.UnloadOrder;
import mario.plc.Celula;
import mario.plc.SeguidorDePecas;
import mario.plc.Tapete;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MES {
    private GraphMES graphMES = new GraphMES();
    private OrderManager orderManager = new OrderManager();
    private SeguidorDePecas seguidorDePecas;
    public String clientName;
    public OPCUAConnection myConnection;
    private HashMap<UnloadOrder,Integer> unloadOrdersBeingProcessedMap = new HashMap<>();
    private HashMap<TransformationOrder,Integer> transformationOrdersBeingProcessedMap = new HashMap<>();

    private List<TransformationOrder> transformationOrdersBeingProcessed=new ArrayList<>();
    private List<UnloadOrder> unloadOrdersBeingProcessed= new ArrayList<>();




    public HashMap<UnloadOrder, Integer> getUnloadOrdersBeingProcessedMap() {
        return unloadOrdersBeingProcessedMap;
    }

    public void setUnloadOrdersBeingProcessedMap(HashMap<UnloadOrder, Integer> unloadOrdersBeingProcessedMap) {
        this.unloadOrdersBeingProcessedMap = unloadOrdersBeingProcessedMap;
    }

    public HashMap<TransformationOrder, Integer> getTransformationOrdersBeingProcessedMap() {
        return transformationOrdersBeingProcessedMap;
    }

    public void setTransformationOrdersBeingProcessedMap(HashMap<TransformationOrder, Integer> transformationOrdersBeingProcessedMap) {
        this.transformationOrdersBeingProcessedMap = transformationOrdersBeingProcessedMap;
    }

    public MES(int delay) {
        this.seguidorDePecas = new SeguidorDePecas(delay);
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public SeguidorDePecas getSeguidorDePecas() {
        return seguidorDePecas;
    }

    public void libertaPecas() {
        if (!seguidorDePecas.getStorage().canWork()) {
            return;
        }
        Tapete tapete = seguidorDePecas.getStorage().getTapeteUnload();

        synchronized (orderManager){

        if (orderManager.getUnloadOrdersToProcess().size() > 0) {
           // System.out.println(orderManager.getUnloadOrdersToProcess().size());
            UnloadOrder order = orderManager.getUnloadOrdersToProcess().get(0);
            orderManager.getUnloadOrdersToProcess().remove(0);

            Peca peca = new Peca(order.getType(), tapete, order);
            peca.setNomeDaCelulaParaOndeVai(this.getSeguidorDePecas().getUnloadCell());
            peca.setWhereToUnload(order.getDestination());

            seguidorDePecas.getStorage().retrievePieceOPCua(peca);
            return;
        }

        if (orderManager.getTransformationOrdersToProcess().size() > 0) {
          //  System.out.println(orderManager.getTransformationOrdersToProcess().size());
            TransformationOrder order = orderManager.getTransformationOrdersToProcess().get(0);
            //System.out.println(order.getFrom());
           orderManager.getTransformationOrdersToProcess().remove(0);
            Peca peca = celulaSelector(order);
            //System.out.println(peca.getCelulaParaOndeVai());
            peca.setPieceComida(peca);





            //      System.out.println(peca.getInstrucoes().get(0).descobrirTapete().getPlcVariableName());

            //System.out.println(peca.getInstrucoes().get(0).toString());
           // System.out.println(peca.getInstrucoes().get(0).descobrirTapete().getPlcVariableName());
            seguidorDePecas.getStorage().retrievePieceOPCua(peca);
            return;
        }
    }

    }

    private Peca pecaSelector(ListaDeListaInstrucoes listaDeListaInstrucoes, TransformationOrder order) {
        if (listaDeListaInstrucoes.isA()) {
            if (!seguidorDePecas.getC4().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
            if (!seguidorDePecas.getC3().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC3());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
            if (!seguidorDePecas.getC2().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC2());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
            else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;

            }

        }

        if (listaDeListaInstrucoes.isB() || listaDeListaInstrucoes.isAb()) {
            if (!seguidorDePecas.getC3().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC3());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;


            }
            if (!seguidorDePecas.getC1().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC1());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;


            }
            else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC3());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
        }

        if (listaDeListaInstrucoes.isC() || listaDeListaInstrucoes.isAc()) {
            if (!seguidorDePecas.getC4().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                return peca;
            }
            if (!seguidorDePecas.getC2().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC2());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;

            }
            else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
        }
        return null;
    }

    private void prinLista(List<InstrucaoTransformacoes> list){
        for(InstrucaoTransformacoes transformacoes: list){
           // System.out.println(transformacoes.toString());
        }
    }

    public Peca celulaSelector(TransformationOrder order) {

        List<ListaDeListaInstrucoes> listaDeListaInstrucoes = graphMES.getInstructions(order.getFrom(), order.getTo());

        for (int i = 0; i < listaDeListaInstrucoes.size(); i++) {
                //prinLista(listaDeListaInstrucoes.get(0).getInstrucaoTransformacoes());
                Peca peca=pecaSelector(listaDeListaInstrucoes.get(i),order);
                if(peca!=null)
                    return peca;
            }


            return null;
        }

    }

