package mario.UDP;

import mario.OPCUa.OrderManager;
import mario.order.OrderParser;
import mario.plc.Storage;
import mario.xml.XMLParser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/***
 * Recebe ficheiros por mario.UDP na porta especificada, extende thread porque pretende-se correr esta parte numa thread à parte.
 */
public class UDPHandler extends Thread {
    private OrderParser orderParser;
    private static int port;
    private static DatagramSocket dsocket;
   private static byte[] buffer;
   private static DatagramPacket packet;
   private String message;
   private OrderParser orders;
   private Storage storage;
   OrderManager orderManager;
   private boolean requestStores=false;
   Thread thread;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /***
     *
     * @param port Porta mario.UDP de onde se recebe o ficheiro.
     */
    public UDPHandler(int port, Storage storage,OrderManager orderManager) {
        this.storage=storage;
        this.port=port;
        this.orderParser=orderManager.getOrderParser();
        this.orderManager=orderManager;
        this.message="";
        try {
            dsocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Create a buffer to read datagrams into. If a
        // packet is larger than this buffer, the
        // excess will simply be discarded!
        buffer = new byte[2048];

        // Create a packet to receive data into the buffer
        packet = new DatagramPacket(buffer, buffer.length);
    }

    public boolean receiveString(){
        this.message="";
        try {
            packet.setLength(buffer.length);
            dsocket.receive(packet);
            this.message=new String(buffer, 0, packet.getLength());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


public OrderParser getOrderParser(){
        return orderParser;
}

    @Override

    /***
     * Corre
     */
    public void run() {
        boolean proccessOrder=true;
        while (true) {
            try {



                // Convert the contents to a string, and display them
               // System.out.println(packet.getAddress().getHostName() + ": "
                      // + msg)
                if(receiveString()) {
                   //synchronized (this.orderParser) {
                    //   System.out.println(message);

                        this.orderParser = new OrderParser(new XMLParser(this.message.getBytes()));
                        if(this.orderParser.getTransformationOrders().isEmpty() && this.orderParser.getUnloadOrders().isEmpty() && requestStores) {
                            proccessOrder = false;
                        }
                        else {
                            requestStores=false;
                            proccessOrder=true;
                        }

                        if(proccessOrder) {
                            sendStores();

                                orderManager.addOrdersFromUdp(orderParser);

                        }
                        Thread.sleep(50);
                //}
                }
               //orderParser.printAll();
                // Reset the length of the packet before reusing it.
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    public void start(){
        System.out.println("Starting UDP Handler");
        thread =new Thread(this);
        thread.start();
    }

    public void sendStores(){
        synchronized (this.orderParser) {
            if (orderParser.getTransformationOrders().isEmpty() && orderParser.getUnloadOrders().isEmpty()) {
                String stores = storage.requestStores();

                UDPSend.sendUDP(stores, port);
                requestStores=true;
                System.out.println(stores);
            }
        }

    }


}