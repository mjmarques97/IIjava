package mario.OPCUa;

import mario.UDP.UDPHandler;
import mario.order.*;
import mario.plc.CelulaFactory;
import mario.plc.SeguidorDePecas;
import mario.plc.UnloadCell;
import mario.plc.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.ArrayList;
import java.util.List;

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

        this.unloadOrdersToProcess.addAll(orderParser.getUnloadOrders());
        this.transformationOrdersToProcess.addAll(orderParser.getTransformationOrders());
      //  printUnloads();
       // printTransformations();

    //}

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


