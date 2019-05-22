import mario.UDP.UDPReceive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static mario.UDP.UDPSend.sendUDP;
import static org.junit.Assert.assertEquals;

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
