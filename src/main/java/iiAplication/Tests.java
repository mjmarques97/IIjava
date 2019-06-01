package iiAplication;

import mario.order.OrderParser;
import mario.xml.XMLParser;

/***
 *
 */

public class Tests extends Thread {
    private static final String LINES = "\n ---------------------------";
    private static final String SUCCESS="() test successful!";


    public Tests() {
        xmlTest();
       /* try {
            UDPTest();
        } catch (SocketException e) {
            e.printStackTrace();
        }

     /*   try {
            databaseTest();
        } catch (SQLException e) {
            e.printStackTrace();

        }*/

//        requestStoresTest();



    }

    private static void xmlTest() {
        System.out.println("Starting XML Test");


        OrderParser order1 = new OrderParser("XMLFiles//command1.xml");
        order1.printAll();



        OrderParser order2 = new OrderParser("XMLFiles//command2.xml");
        order2.printAll();


        OrderParser order3 = new OrderParser("XMLFiles//command3.xml");
        order3.printAll();

        XMLParser test3 = new XMLParser("XMLFiles//command4.xml");


        OrderParser order4 = new OrderParser("XMLFiles//command4.xml");
        order4.printAll();


        System.out.println(LINES);
        XMLParser test4 = new XMLParser("XMLFiles//command5.xml");
        OrderParser order5 = new OrderParser("XMLFiles//command5.xml");
        order5.printAll();
        System.out.println(LINES);

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+SUCCESS);
    }
/*
    public void UDPTest() throws SocketException {

        System.out.println("Starting mario.UDP test");
        UDPHandler a=new UDPHandler(54321);
        a.start();
    }

    public void databaseTest() throws SQLException{
        System.out.println("Starting DB Test");
        Database database = new Database();
        database.query("SELECT * from \"II\".armazem");
        database.closeConnection();
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+SUCCESS);
        System.out.println(LINES);


    }


    public void requestStoresTest(){
        Storage storage=new Storage();
        storage.setQuantity("p9",2);
        storage.setQuantity("P3",5);
        storage.setQuantity("p5",90);
        String stores= storage.requestStores();
        System.out.println(stores);
        UDPSend.sendUDP(stores,54321);

    }


*/

}

