package UDP;
import order.OrderParser;

import xml.XMLParser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPReceive extends Thread {
    OrderParser orderParser;
    private static int port;
    private static DatagramSocket dsocket;
   private static byte[] buffer;
   private static DatagramPacket packet;
   private String message;
   private OrderParser orders;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UDPReceive(int port) {
        this.message="";
        this.port=port;
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


    public void run() {
        while (true) {
            try {


                dsocket.receive(packet);

                // Convert the contents to a string, and display them
                String msg = new String(buffer, 0, packet.getLength());
                this.setMessage(msg);
               // System.out.println(packet.getAddress().getHostName() + ": "
                      // + msg)
               this.orderParser=new OrderParser(new XMLParser(msg.getBytes()));
               orderParser.printAll();
                // Reset the length of the packet before reusing it.
                packet.setLength(buffer.length);
                this.setMessage("");
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }


}