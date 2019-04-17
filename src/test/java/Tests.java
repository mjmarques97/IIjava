import mario.UDP.UDPReceive;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static mario.UDP.UDPSend.sendUDP;

@RunWith(JUnit4.class)
public class Tests {
    public UDPReceive receive=new UDPReceive(54321);

    public Tests() {
        receive.run();
    }

    @Test
           public void teste(){
        sendUDP("hello",54321);
        assertEquals("hello",receive.getMessage());

    }




}
