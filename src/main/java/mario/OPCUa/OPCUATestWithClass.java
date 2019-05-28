package mario.OPCUa;

import mario.plc.Tapete;
import mario.plc.TapeteMaquina;
import mario.plc.TapeteRotator;
import mario.storage.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public class OPCUATestWithClass {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();



        Storage storage=new Storage();
        storage.retrievePieceOPCua(2);


        Celula c1=new Celula(1);
        Celula c2=new Celula(2);
        Celula c3=new Celula(3);
        Celula c4=new Celula(4);
        UnloadCell unloadCell=new UnloadCell();

        c1.getMaquina4().stopDoingWork();
        c1.getMaquina5().stopDoingWork();
        c1.getMaquina6().stopDoingWork();
        c1.getTapeteRotator().moveDown();

        c2.getTapeteRotator().moveRight();
        c3.getTapeteRotator().moveRight();
        c4.getTapeteRotator().moveRight();
        c1.getMaquina4().stopDoingWork();

        unloadCell.unLoad4();
        unloadCell.stopUnLoad4();
        unloadCell.stopUnLoad5();
        unloadCell.stopUnLoad6();



       // TapeteRotator topRotatorHorizontal1= new TapeteRotator(2);
       // System.out.println("True? "+topRotatorHorizontal1.getMovingRight());

        /*topRotatorHorizontal1.stopMovingRight();
        System.out.println("False? "+topRotatorHorizontal1.getMovingRight());

        TapeteMaquina maquina4C1=new TapeteMaquina(1,4);

        maquina4C1.selectTool(3);
        System.out.println("3? "+maquina4C1.getTool());

        maquina4C1.selectTool(2);
        System.out.println("2? "+maquina4C1.getTool());

        maquina4C1.doWork();
        System.out.println("True? "+maquina4C1.isWorking());

        maquina4C1.stopDoingWork();
        System.out.println("False? "+maquina4C1.isWorking());

        maquina4C1.selectTimeToOperateOnPiece(2000);
        System.out.println("2000? "+maquina4C1.getTimeToOperateOnPiece());

        maquina4C1.selectTimeToOperateOnPiece(1000);
        System.out.println("1000? "+maquina4C1.getTimeToOperateOnPiece());

        maquina4C1.goDownDirection();
        System.out.println("True? "+maquina4C1.getDownDirection());

        maquina4C1.stopDownDirection();
        System.out.println("False? "+maquina4C1.getDownDirection());*/






    }
}
