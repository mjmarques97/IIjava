package mario.OPCUa;

import iiAplication.MES;
import mario.UDP.UDPHandler;
import mario.order.*;
import mario.plc.CelulaFactory;
import mario.plc.SeguidorDePecas;
import mario.plc.UnloadCell;
import mario.plc.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://" + aux + ":4840";
    private List<TransformationOrder> transformationOrdersToProcess=new ArrayList<>();
    private List<UnloadOrder> unloadOrdersToProcess= new ArrayList<>();

    private List<TransformationOrder> transformationOrdersConcluidas=new ArrayList<>();
    private List<UnloadOrder> unloadOrdersConcluidas=new ArrayList<>();
    private OrderParser orderParser=new OrderParser();

    public OrderParser getOrderParser() {
        return orderParser;
    }


    public  void addOrdersFromUdp(OrderParser orderParser) {
        HashMap<UnloadOrder,Integer> unloadOrdersMap=new HashMap<>();
        HashMap<TransformationOrder,Integer> transformationOrderMap=new HashMap<>();

        if(orderParser.getUnloadOrders().size()>0) {
            for (UnloadOrder unloadOrder : orderParser.getUnloadOrders()) {
                unloadOrdersMap.put(unloadOrder, Integer.parseInt(unloadOrder.getQuantity()));
            }
        }

            if(orderParser.getTransformationOrders().size()>0){
                for (TransformationOrder transformationOrder : orderParser.getTransformationOrders()) {
                    transformationOrderMap.put(transformationOrder, Integer.parseInt(transformationOrder.getQuantity()));
                }
            }
            setUpOrders(unloadOrdersMap,transformationOrderMap);









        //this.unloadOrdersToProcess.addAll(orderParser.getUnloadOrders());
        //this.transformationOrdersToProcess.addAll(orderParser.getTransformationOrders());
      //  printUnloads();
       // printTransformations();

    //}

    }

    public void setUpOrders(HashMap<UnloadOrder,Integer> unloadOrdersBeingProcessedMap,HashMap<TransformationOrder,Integer> transformationOrdersBeingProcessedMap){
        if(unloadOrdersBeingProcessedMap.size()>0) {
            for (Map.Entry<UnloadOrder, Integer> unloadOrderIntegerEntry : unloadOrdersBeingProcessedMap.entrySet()) {
                int a = unloadOrderIntegerEntry.getValue();
                for (int i = 0; i < a; i++) {
                    this.unloadOrdersToProcess.add(unloadOrderIntegerEntry.getKey());
                }
            }
        }
        if(transformationOrdersBeingProcessedMap.size()>0) {
            for (Map.Entry<TransformationOrder, Integer> transformationOrderIntegerEntry : transformationOrdersBeingProcessedMap.entrySet()) {
                int a = transformationOrderIntegerEntry.getValue();
                for (int i = 0; i < a; i++) {
                    this.transformationOrdersToProcess.add(transformationOrderIntegerEntry.getKey());
                }
            }
        }

    }




    public List<TransformationOrder> getTransformationOrdersToProcess() {
        return transformationOrdersToProcess;
    }

    public List<UnloadOrder> getUnloadOrdersToProcess() {
        return unloadOrdersToProcess;
    }

    public void unloadOrderConcluded(UnloadOrder unloadOrder) {
        unloadOrdersConcluidas.add(unloadOrder);
    }
    public void transformationOrderConcluded(TransformationOrder transformationOrder){
        transformationOrdersConcluidas.add(transformationOrder);
    }

    public void printTransformations(){
        System.out.println("Transformations");
        if(transformationOrdersToProcess.isEmpty()){
            System.out.println("No transformations to print");
            return;
        }

        for(TransformationOrder order: transformationOrdersToProcess)
            order.print();

    }

    public void printUnloads(){
        System.out.println("Unloads:");
        if(unloadOrdersToProcess.isEmpty()){
            System.out.println("No Unloads to print.");
            return;
        }
        for(UnloadOrder order: unloadOrdersToProcess)
            order.print();
    }

    public void terminaOrdem(Order order){
        if(order instanceof TransformationOrder){
            transformationOrdersToProcess.remove(order);
            System.out.println("Ordem "+order.getNumber()+ " de "+((TransformationOrder) order).getFrom() + " para "+((TransformationOrder) order).getTo()+" terminada.");
        }
        if(order instanceof UnloadOrder){
            unloadOrdersToProcess.remove(order);
            System.out.println("Ordem de Unload "+order.getNumber()+ " de "+((UnloadOrder) order).getType()+ " terminada.");
        }

    }



   /* public static void main(String[] args) {

        /*myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();
        SeguidorDePecas seguidorDePecas = new SeguidorDePecas(0);
        Storage storage=new Storage();
        UDPHandler udpHandler=new UDPHandler(54321,storage);

        udpHandler.run();

        while (true){
            this.addOrdersFromUdp(udpHandler.getOrderParser());
        }

*/
    }


