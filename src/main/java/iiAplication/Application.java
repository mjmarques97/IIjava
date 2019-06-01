package iiAplication;

import mario.OPCUa.OPCUAConnection;
import mario.OPCUa.OrderManager;
import mario.UDP.UDPHandler;
import mario.order.OrderParser;
import mario.plc.SeguidorDePecas;
import mario.plc.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

/***
 * Parte que corre os testes
 */

public class Application {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args)  {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();

        MES mes=new MES(50);
        UDPHandler udpHandler=new UDPHandler(54321,mes.getSeguidorDePecas().getStorage(),mes.getOrderManager());
        mes.getSeguidorDePecas().getC1().getMaquina4().goDownDirection();
        mes.getSeguidorDePecas().getC1().getMaquina5().goDownDirection();
        mes.getSeguidorDePecas().getC1().getMaquina6().goDownDirection();

        udpHandler.start();


        while (true){
            mes.getSeguidorDePecas().updateAllEachCycle();
            mes.libertaPecas();
        }
        // XML test

        //new Tests();
    }
}
