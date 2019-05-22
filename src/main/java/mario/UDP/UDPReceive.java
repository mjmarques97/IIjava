package mario.UDP;

import mario.order.OrderParser;
import mario.xml.XMLParser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/***
 * Recebe ficheiros por mario.UDP na porta especificada, extende thread porque pretende-se correr esta parte numa thread à parte.
 */
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

    /***
     *
     * @param port Porta mario.UDP de onde se recebe o ficheiro.
     */
    public UDPReceive(int port) {
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


    @Override

    /***
     * Corre
     */
    public void run() {
        while (true) {
            try {



                // Convert the contents to a string, and display them
               // System.out.println(packet.getAddress().getHostName() + ": "
                      // + msg)
                if(receiveString()) {
                    this.orderParser = new OrderParser(new XMLParser(this.message.getBytes()));
                }
               orderParser.printAll();
                // Reset the length of the packet before reusing it.
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }


}