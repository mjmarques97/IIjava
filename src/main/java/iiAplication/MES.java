package iiAplication;

import mario.OPCUa.OPCUAConnection;
import mario.OPCUa.OrderManager;
import mario.graph.GraphMES;
import mario.graph.ListaDeListaInstrucoes;
import mario.order.Peca;
import mario.order.TransformationOrder;
import mario.order.UnloadOrder;
import mario.plc.Celula;
import mario.plc.SeguidorDePecas;
import mario.plc.Tapete;

import java.util.ArrayList;
import java.util.List;

public class MES {
    private GraphMES graphMES = new GraphMES();
    private OrderManager orderManager = new OrderManager();
    private SeguidorDePecas seguidorDePecas;
    public String clientName;
    public OPCUAConnection myConnection;
    private List<UnloadOrder> unloadOrdersBeingProcessed = new ArrayList<>();
    private List<TransformationOrder> transformationOrdersBeingProcessed = new ArrayList<>();


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

        if (orderManager.getUnloadOrdersToProcess().size() > 0) {
            UnloadOrder order = orderManager.getUnloadOrdersToProcess().get(0);
            orderManager.getUnloadOrdersToProcess().remove(0);

            Peca peca = new Peca(order.getType(), tapete, order);
            peca.setNomeDaCelulaParaOndeVai(this.getSeguidorDePecas().getUnloadCell());
            peca.setWhereToUnload(order.getDestination());

            seguidorDePecas.getStorage().retrievePieceOPCua(peca);
            return;
        }

        if (orderManager.getTransformationOrdersToProcess().size() > 0) {
            TransformationOrder order = orderManager.getTransformationOrdersToProcess().get(0);
            orderManager.getTransformationOrdersToProcess().remove(0);
            transformationOrdersBeingProcessed.add(order);
            Peca peca =celulaSelector(order);

            peca.setPieceComida(peca);

            seguidorDePecas.getStorage().retrievePieceOPCua(peca);
            return;
        }

    }

    private Peca pecaSelector(ListaDeListaInstrucoes listaDeListaInstrucoes, TransformationOrder order) {
        if (listaDeListaInstrucoes.isA()) {
            if (!seguidorDePecas.getC1().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC1());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
            if (!seguidorDePecas.getC2().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC2());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;

            } else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC3());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;

            }

        }

        if (listaDeListaInstrucoes.isB() || listaDeListaInstrucoes.isAb()) {
            if (!seguidorDePecas.getC1().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC1());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            } else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC3());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
        }

        if (listaDeListaInstrucoes.isC() || listaDeListaInstrucoes.isAc()) {
            if (!seguidorDePecas.getC2().full()) {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC2());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC2());
                return peca;
            } else {
                Peca peca = new Peca(order.getFrom(), getSeguidorDePecas().getStorage().getTapeteUnload(), order);
                peca.setNomeDaCelulaParaOndeVai(seguidorDePecas.getC4());
                peca.setInstrucoes(listaDeListaInstrucoes.getInstrucaoTransformacoes());
                return peca;
            }
        }
        return null;
    }


    public Peca celulaSelector(TransformationOrder order) {

        List<ListaDeListaInstrucoes> listaDeListaInstrucoes = graphMES.getInstructions(order.getFrom(), order.getTo());

        for (int i = 0; i < listaDeListaInstrucoes.size(); i++) {
                Peca peca=pecaSelector(listaDeListaInstrucoes.get(i),order);
                if(peca!=null)
                    return peca;
            }


            return null;
        }

    }

