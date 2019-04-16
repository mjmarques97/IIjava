package iiAplication;

import UDP.UDPReceive;
import database.Database;
import order.OrderParser;
import xml.XMLParser;

import java.net.SocketException;
import java.sql.SQLException;

/***
 * Testes definidos nesta classe
 */

public class Tests extends Thread {
    private static final String LINES = "\n ---------------------------";
    private static final String SUCCESS="() test successful!";


    public Tests() {

        xmlTest();
        try {
            UDPTest();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            databaseTest();
        } catch (SQLException e) {
            e.printStackTrace();

        }


    }

    private static void xmlTest() {
        System.out.println("Starting XML Test");

        XMLParser test = new XMLParser("XMLFiles//command1.xml");
        OrderParser order1 = new OrderParser(test);
        order1.printAll();

        XMLParser test1 = new XMLParser("XMLFiles//command2.xml");
        OrderParser order2 = new OrderParser(test1);
        order2.printAll();
        XMLParser test2 = new XMLParser("XMLFiles//command3.xml");
        OrderParser order3 = new OrderParser(test2);
        order3.printAll();

        XMLParser test3 = new XMLParser("XMLFiles//command4.xml");
        OrderParser order4 = new OrderParser(test3);
        order4.printAll();

        XMLParser test4 = new XMLParser("XMLFiles//command5.xml");
        OrderParser order5 = new OrderParser(test4);
        order5.printAll();
        System.out.println(LINES);

        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName()+SUCCESS);
    }

    public void UDPTest() throws SocketException {

        System.out.println("Starting UDP test");
        UDPReceive a=new UDPReceive(54321);
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




}
