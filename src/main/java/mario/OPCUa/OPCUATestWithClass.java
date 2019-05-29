package mario.OPCUa;

import mario.plc.Celula;
import mario.storage.Storage;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

public class OPCUATestWithClass {
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-LA0FL5M";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) {
        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();



        Storage storage=new Storage();



        Celula c1=new Celula(1);
        Celula c2=new Celula(2);
        Celula c3=new Celula(3);
        Celula c4=new Celula(4);
        UnloadCell unloadCell=new UnloadCell();


        ////NOTAS:C2T2->C3T1 possivel problema nos sensores, possivel falha na maquinaçao

        /// from p8 to p9, 8 pieces - simulate order 003 of command2.xml

        for (int i = 0; i < 15; i++) {

            //verifica celulas livre capazes de transformaçao
            while (true) {
                System.out.println("Entrou ciclo 1");

                if (!c2.hasPieceOnTapeteAbaixoDoRotadorDeCima() || !c4.hasPieceOnTapeteAbaixoDoRotadorDeCima()) {
                    break;
                }

            }

            //prioridade a celula mais a direita para nao entupir
            if (!c4.hasPieceOnTapeteAbaixoDoRotadorDeCima() || !c4.hasPieceOnMaquina4()) {

                ///aramazem lento e programa rapido sem estes dois while's o programa vai ignorar multiplas ordens de
                // descargado armazem

                if(i!=0) {
                    while (true) {
                        System.out.println("Entrou ciclo 2");
                        if (!storage.hasPieceTapeteDiscarga()) {
                            break;
                        }

                    }
                }
                 //contagem do armazem esta a ser feita?
                storage.retrievePieceOPCua(8);
                while (true) {
                    System.out.println("Entrou ciclo 3");
                    if (storage.hasPieceTapeteDiscarga()) {
                        break;
                    }
                }
                c1.getTapeteRotator().moveRight();
                c2.getTapeteRotator().moveRight();
                c3.getTapeteRotator().moveRight();
                c4.getTapeteRotator().moveDown();
                c4.getMaquina4().goDownDirection();
                c4.getMaquina5().goDownDirection();
                c4.getMaquina6().getToWork(3, 8000);
                c4.getMaquina6().goDownDirection();

            }

            else {

                if(i!=0) {
                    while (true) {
                        System.out.println("Entrou ciclo 4");
                        if (!storage.hasPieceTapeteDiscarga()) {
                            break;
                        }

                    }
                }

                storage.retrievePieceOPCua(8);
                while (true) {
                    System.out.println("Entrou ciclo 5");
                    if (storage.hasPieceTapeteDiscarga()) {
                        break;
                    }
                }

                c1.getTapeteRotator().moveRight();
                //caso esteja alguma peça no processo em andamento, será que virá a meio?
                c2.getTapeteRotator().moveDown();
                c2.getMaquina4().goDownDirection();
                c2.getMaquina5().goDownDirection();
                c2.getMaquina6().getToWork(3, 8000);
                c2.getMaquina6().goDownDirection();
            }
            System.out.print("first statement. ");
        }
        /**c1.getMaquina4().stopDoingWork();
        c1.getMaquina4().goDownDirection();
        c1.getMaquina5().stopDoingWork();
        c1.getMaquina5().goDownDirection();
        c1.getMaquina6().stopDoingWork();
        c1.getMaquina6().goDownDirection();
        c1.getTapeteRotator().moveDown();

        c2.getTapeteRotator().moveRight();
        c3.getTapeteRotator().moveRight();
        c4.getTapeteRotator().moveRight();
        c1.getMaquina4().goDownDirection();
        c1.getMaquina5().goDownDirection();
        c1.getMaquina6().goDownDirection();
        unloadCell.unLoad4();
        unloadCell.stopUnLoad4();
        unloadCell.stopUnLoad5();
        unloadCell.stopUnLoad6();

        while(true){
            if(storage.hasPieceTapeteDiscarga()) {
                System.out.println("Passou celula de Discarga");
                break;
            }
        }

        while(true){
            if(c1.hasPieceOnTapeteAEsquerdaDoTapeteRotadorDeCima()) {
                System.out.println("Passou Tapete a Esquerda do Rotativo de Cima!!!");
                break;
            }
        }

        while(true){
            if(c1.hasPieceOnTapeteRotatorDeCima()) {
                System.out.println("Rotador De cima!!!");
                break;
            }
        }
        while(true) {
            if (c1.hasPieceOnTapeteAcimaDaMaquina4()) {
                System.out.println("Tapete Acima da máquina 4!!!");
                break;
            }
        }


        while(true){
            if(c1.hasPieceOnMaquina4()) {
                System.out.println("PassouMaquina4!!!");
                break;
            }
        }
        while(true){
            if(c1.hasPieceOnMaquina5()) {
                System.out.println("PassouMaquina5!!!");
                break;
            }
        }

        while(true){
            if(c1.hasPieceOnMaquina6()) {
                System.out.println("PassouMaquina6!!!");
                break;
            }
        }
        while(true){
            if(c1.hasPieceOnTapeteRotadorAbaixoDaMaquina6()) {
                System.out.println("PassouMaquinaRotadorDeBaixo!!!");
                break;
            }
        }

        while(true){
            if(c1.hasPieceOnTapeteAEsquerdaDoRotadorDeBaixo()) {
                System.out.println("Passou Tapete da Esquerda do de baixo!!!");
                break;
            }
        }

        while(true){
            if(storage.hasPieceTapeteEntradaDoArmazem()) {
                System.out.println("Tapete de Entrada do Armazem!!!");
                break;
            }
        }
         */



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
