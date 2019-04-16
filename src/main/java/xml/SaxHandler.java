package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/***
 * Parte que extrai elementos do ficheiro XML, sacado da net.
 */
class SAXHandler extends DefaultHandler {
    private final XMLParser unparsed;

    public SAXHandler(XMLParser unparsed){
        this.unparsed=unparsed;
    }
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        int attributeLength = attributes.getLength();

        addtoUnparsedList("Order",qName, attributes, attributeLength);
        addtoUnparsedList("Transform",qName, attributes, attributeLength);
        addtoUnparsedList("Unload",qName, attributes, attributeLength);


    }


    private void addtoUnparsedList(String compare,String qName, Attributes attributes, int attributeLength) {
        String add = compare + "";
        if (compare.equals(qName)) {
            for (int i = 0; i < attributeLength; i++) {
                // Get attribute names and values
                String attrName = attributes.getQName(i);
                String attrVal = attributes.getValue(i);
                add=add+ attrName + "=" + attrVal + ";";
            }
            this.unparsed.addUnparsedOrder(add.replace("order.",""));


        }

    }

}