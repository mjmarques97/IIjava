package xml;


import org.xml.sax.helpers.DefaultHandler;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XMLParser {
    private List<String> unparsedOrder=new ArrayList<>();

    public List<String> getUnparsedOrder() {
        return unparsedOrder;
    }

    public void addUnparsedOrder(String unparsedorder){
        this.unparsedOrder.add(unparsedorder);
    }


    public XMLParser(String string) {
        try {

            // Create SAXParserFactory instance and a SAXParser
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            // Get an InputStream to the elements.xml file and parse
            // its contents using the xml.SAXHandler.


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

            // Get an InputStream to the elements.xml file and parse
            // its contents using the xml.SAXHandler.


            InputStream is =

                   new ByteArrayInputStream(bytes);

            DefaultHandler handler = new SAXHandler(this);
            parser.parse(is, handler);
            is.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(){
        for(String a: unparsedOrder) {
            System.out.println(a);

        }

    }


}
