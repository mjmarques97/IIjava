package mario.plc;

import mario.OPCUa.OPCUAConnection;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public class SeguidorDePecasTeste {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();
        SeguidorDePecas seguidorDePecas =new SeguidorDePecas(5);

       // seguidorDePecas.getStorage().printAll();


        seguidorDePecas.getC1().getTapeteRotatorDeCima().moveDown();
        seguidorDePecas.getC1().getMaquina4().goDownDirection();
        seguidorDePecas.getC1().getMaquina5().goDownDirection();
        seguidorDePecas.getC1().getMaquina6().goDownDirection();

        seguidorDePecas.getC2().getTapeteRotatorDeCima().moveRight();
        seguidorDePecas.getC3().getMaquina4().goDownDirection();
        seguidorDePecas.getC3().getMaquina5().goDownDirection();
        seguidorDePecas.getC3().getMaquina6().goDownDirection();



        seguidorDePecas.getStorage().retrievePieceOPCua(2);



        while(true)
            seguidorDePecas.updateAllEachCycle();


        //seguidorDePecas.updateAllEachCycle();
    }
}
