package mario.OPCUa;

import mario.plc.CelulaFactory;
import mario.plc.SeguidorDePecas;
import mario.plc.UnloadCell;
import mario.plc.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public class OPCUATestWithClass {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();

        SeguidorDePecas seguidorDePecas=new SeguidorDePecas(0);


        for(int k=0;k<9;k++){

            seguidorDePecas.updateAllEachCycle();
            while (true) {
                //System.out.println("Hello 1");
                seguidorDePecas.updateAllEachCycle();
                if (seguidorDePecas.getUnloadCell().getTapeteAcimaDoPrimeiroPusher().getPecaNoTapete().equals("NAOTEMPECA")) {
                    break;
                }

            }

            if(k!=0) {
                while (true) {
                    //System.out.println("Hello2");
                    seguidorDePecas.updateAllEachCycle();
                    if (seguidorDePecas.getStorage().getTapeteUnload().getPecaNoTapete().equals("NAOTEMPECA")) {
                        break;
                    }

                }
            }
            seguidorDePecas.getStorage().retrievePieceOPCua(7);
            while (true) {
                seguidorDePecas.updateAllEachCycle();
               String peca= seguidorDePecas.getStorage().getTapeteUnload().getPecaNoTapete();
                if (!seguidorDePecas.getC1().getTapeteAEsquerdaDoRotadorDeCima().getPecaNoTapete().equals("NAOTEMPECA")) {
                    //System.out.println(peca);
                    break;
                }
            }


            seguidorDePecas.getC1().getTapeteRotatorDeCima().moveRight();
            seguidorDePecas.getC2().getTapeteRotatorDeCima().moveRight();
            seguidorDePecas.getC3().getTapeteRotatorDeCima().moveRight();
            seguidorDePecas.getC4().getTapeteRotatorDeCima().moveRight();
            seguidorDePecas.getUnloadCell().unLoad6();


        }
        seguidorDePecas.getStorage().retrievePieceOPCua(7);
        while(true){
            //System.out.println("Hello4");
            seguidorDePecas.updateAllEachCycle();
            if(!seguidorDePecas.getUnloadCell().getTapetePrimeiroPusher().getPecaNoTapete().equals("NAOTEMPECA"))break;

        }

        while(true){
            //System.out.println("Hello 5");
            seguidorDePecas.updateAllEachCycle();
            if(!seguidorDePecas.getUnloadCell().hasPieceOnTapeteRotadorDeCima() && !seguidorDePecas.getUnloadCell().hasPieceOnTapeteAbaixoDoRotadorDeCima()
                    && !seguidorDePecas.getUnloadCell().hasPieceOnPrimeiroPusher() && !seguidorDePecas.getUnloadCell().hasPieceOnSegundoPusher()
                    &&!seguidorDePecas.getUnloadCell().hasPieceOnTerceiroPusher()) break;

        }

    }



    }

