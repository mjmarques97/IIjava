package mario.order;

import com.google.common.base.Joiner;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;

import java.util.List;

/***
 * Ficheiro XML request stores
 */
public class RequestStores {
    private String appendQuotes(String string) {
        return Joiner.on("").join("\"",string,"\"");
    }


    /***
     *
     *
     *
     * @return String com mario.xml, enviar depois para a porta.
     */
    public String returnCurrentStores(Integer[][] stores) {
        List<String> list= Lists.newLinkedList();
        list.add("<?mario.xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        list.add("<Current_Stores>\n");
        for (int i = 0; i < 9; i++) {
            String first="<WorkPiece type=";
            String second=appendQuotes(Joiner.on("").join("P",i+1));
            String third=" quantity=";
            String fourth=appendQuotes(Integer.toString(stores[i][1]));
            String fifth="/>\n";
            list.add(Joiner.on("").join(first,second,third,fourth,fifth));
        }
       list.add("</Current_Stores>");

        String a= Joiner.on("").join(list);


        return a;




    }
}


