package xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/***
 * Parte que trata de fazer parse ao XML
 */
class SAXHandler extends DefaultHandler {
    private final xmlParser unparsed;

    public SAXHandler(xmlParser unparsed){
        this.unparsed=unparsed;
    }
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes)
            throws SAXException {

        int attributeLength = attributes.getLength();
        addtoUnparsedList("xml.Order",qName, attributes, attributeLength);
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
               // System.out.println(add);
            }
            this.unparsed.addUnparsedOrder(add);


        }

    }

}