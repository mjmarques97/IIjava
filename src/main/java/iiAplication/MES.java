package iiAplication;

import mario.OPCUa.OPCUAConnection;
import mario.OPCUa.OrderManager;
import mario.order.Peca;
import mario.order.TransformationOrder;
import mario.order.UnloadOrder;
import mario.plc.SeguidorDePecas;
import mario.plc.Tapete;

import java.util.ArrayList;
import java.util.List;

public class MES {
    private OrderManager orderManager=new OrderManager();
    private SeguidorDePecas seguidorDePecas;
    public  String clientName;
    public OPCUAConnection myConnection;
    private List<UnloadOrder> unloadOrdersBeingProcessed=new ArrayList<>();
    private List<TransformationOrder> transformationOrdersBeingProcessed=new ArrayList<>();


    public MES(int delay) {
        this.seguidorDePecas = new SeguidorDePecas(delay);
    }

    public OrderManager getOrderManager() {
        return orderManager;
    }

    public SeguidorDePecas getSeguidorDePecas() {
        return seguidorDePecas;
    }

    public void libertaPecas(){
        if(!seguidorDePecas.getStorage().canWork()){
            return;
        }
        Tapete tapete=seguidorDePecas.getStorage().getTapeteUnload();

       if(orderManager.getUnloadOrdersToProcess().size()>0){
          UnloadOrder order= orderManager.getUnloadOrdersToProcess().get(0);
          orderManager.getUnloadOrdersToProcess().remove(0);

          Peca peca=new Peca(order.getType(),tapete,order);
          peca.setCelulaParaOndeVai(5);
          peca.setWhereToUnload(order.getDestination());
          peca.setUnloadCell(this.getSeguidorDePecas().getUnloadCell());

           seguidorDePecas.getStorage().retrievePieceOPCua(peca);
           return;
       }

       if(orderManager.getTransformationOrdersToProcess().size()>0){
           TransformationOrder order=orderManager.getTransformationOrdersToProcess().get(0);
           orderManager.getTransformationOrdersToProcess().remove(0);
           transformationOrdersBeingProcessed.add(order);
           Peca peca=new Peca(order.getFrom(),getSeguidorDePecas().getStorage().getTapeteUnload(),order);
           peca.setCelulaParaOndeVai(celulaSelector(peca,order));
           return;
       }

    }
    public int celulaSelector(Peca peca,TransformationOrder transformationOrder){

        return 1;
    }

}
