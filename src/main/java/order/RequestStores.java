package order;

import storage.Storage;

/***
 * Ficheiro XML request stores
 */
public class RequestStores {
    private Storage storage;
    private String appendQuotes(String string) {
        return "\"" + string + "\"";
    }

    public RequestStores(Storage storage) {
        this.storage = storage;
    }

    /***
     *
     *
     *
     * @return String com xml, enviar depois para a porta.
     */
    public String returnCurrentStores() {
        Integer[][] stores=storage.getPieceList();
        StringBuilder xmlBuilder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlBuilder.append("<Current_Stores>\n");
        for (int i = 0; i < 9; i++) {

            xmlBuilder.append("<WorkPiece type=" + appendQuotes("P" + (i + 1)) + " quantity=" + appendQuotes(Integer.toString(stores[i][1])) + "/>\n");
        }
        xmlBuilder.append("</Current_Stores>\n");
        return  xmlBuilder.toString();


    }
}


