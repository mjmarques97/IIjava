package mario.xml;


import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/***
 * Coloca elementos do ficheiro numa String.
 * Formato dos elemento da lista: Order Number=, Transform From= To= Quantity= ,Unload Type="P2" Destination="D1" Quantity="4"
 * As ordens aparecem aos pares, há sempre uma mario.order number e um tipo de ordem, pelo que a lista tem sempre tamanho par.
 * Se o comando for "<Request_Stores/>" a lista não tem um número par de elementos.
 *
 */
public class XMLParser {
    private List<String> unparsedOrder = new ArrayList<>();

    public List<String> getUnparsedOrder() {
        return unparsedOrder;
    }

    public void addUnparsedOrder(String unparsedorder) {
        this.unparsedOrder.add(unparsedorder);
    }


    public XMLParser(String string) {
        try {

            // Create SAXParserFactory instance and a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            // Get an InputStream to the elements.mario.xml file and parse
            // its contents using the mario.xml.SAXHandler.


            InputStream is =

                    new FileInputStream(string);

            DefaultHandler handler = new SAXHandler(this);
            parser.parse(is, handler);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public XMLParser(byte[] bytes) {
        try {

            // Create SAXParserFactory instance and a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            // Get an InputStream to the elements.mario.xml file and parse
            // its contents using the mario.xml.SAXHandler.


            InputStream is =

                    new ByteArrayInputStream(bytes);

            DefaultHandler handler = new SAXHandler(this);
            parser.parse(is, handler);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (String a : unparsedOrder) {
            System.out.println(a);

        }

    }


}
