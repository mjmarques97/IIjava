package mario.OPCUa;

import mario.plc.Tapete;
import mario.plc.TapeteMaquina;
import mario.plc.TapeteRotator;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public class OPCUATestWithClass {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();
        TapeteRotator topRotatorHorizontal1= new TapeteRotator(1);
        topRotatorHorizontal1.goRight();
        System.out.println("True? "+topRotatorHorizontal1.getMovingRight());

        topRotatorHorizontal1.stopMovingRight();
        System.out.println("False? "+topRotatorHorizontal1.getMovingRight());

        TapeteMaquina maquina4C1=new TapeteMaquina(1,4);

        maquina4C1.selectTool(3);
        System.out.println("3? "+maquina4C1.getTool());

        maquina4C1.selectTool(2);
        System.out.println("2? "+maquina4C1.getTool());

        maquina4C1.waitForPieceToComeIn();
        System.out.println("True? "+maquina4C1.getWaitForPieceToComein());

        maquina4C1.doNotWaitForPieceToComeIn();
        System.out.println("False? "+maquina4C1.getWaitForPieceToComein());

        maquina4C1.selectTimeToOperateOnPiece(2000);
        System.out.println("2000? "+maquina4C1.getTimeToOperateOnPiece());

        maquina4C1.selectTimeToOperateOnPiece(1000);
        System.out.println("1000? "+maquina4C1.getTimeToOperateOnPiece());

        maquina4C1.goDownDirection();
        System.out.println("True? "+maquina4C1.getDownDirection());

        maquina4C1.stopDownDirection();
        System.out.println("False? "+maquina4C1.getDownDirection());






    }
}
