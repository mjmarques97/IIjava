package iiAplication;

import mario.OPCUa.OPCUAConnection;
import mario.OPCUa.OrderManager;
import mario.UDP.UDPHandler;
import mario.order.OrderParser;
import mario.order.Peca;
import mario.plc.SeguidorDePecas;
import mario.plc.Storage;
import mario.plc.Tapete;
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

        MES mes=new MES(100);
        UDPHandler udpHandler=new UDPHandler(54321,mes.getSeguidorDePecas().getStorage(),mes.getOrderManager());

        mes.getSeguidorDePecas().getC3().getMaquina4().stopDownDirection();
        mes.getSeguidorDePecas().getC3().getMaquina5().goDownDirection();
        mes.getSeguidorDePecas().getC3().getMaquina6().goDownDirection();

        mes.getSeguidorDePecas().getC2().getMaquina4().goDownDirection();
        mes.getSeguidorDePecas().getC2().getMaquina5().goDownDirection();
        mes.getSeguidorDePecas().getC2().getMaquina6().goDownDirection();

        mes.getSeguidorDePecas().getC1().getMaquina4().stopDownDirection();
        mes.getSeguidorDePecas().getC1().getMaquina5().goDownDirection();
        mes.getSeguidorDePecas().getC1().getMaquina6().goDownDirection();

        mes.getSeguidorDePecas().getC4().getMaquina4().goDownDirection();
        mes.getSeguidorDePecas().getC4().getMaquina5().stopDownDirection();
        mes.getSeguidorDePecas().getC4().getMaquina6().goDownDirection();


        udpHandler.start();

       /* Tapete tapete=mes.getSeguidorDePecas().getStorage().getTapeteUnload();
        Peca peca=new Peca(1,tapete);
        peca.setNomeDaCelulaParaOndeVai(1);

        mes.getSeguidorDePecas().getStorage().retrievePieceOPCua(peca);*/

        while (true){
            mes.getSeguidorDePecas().updateAllEachCycle();
            mes.libertaPecas();
        }
        // XML test

        //new Tests();
    }
}
