package mario.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend {
    public static void sendUDP(String message, int port) {
        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress inetAddress=InetAddress.getLocalHost();
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), inetAddress, port);
            ds.send(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
