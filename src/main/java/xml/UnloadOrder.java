package xml;

/***
 * Ordem de Unload, como é uma ordem herda atributos do Pai, xml.Order
 */

public class UnloadOrder extends Order {
    private final String type;
    private final String destination;

    public UnloadOrder(String number, String quantity, String type, String destination) {
        super(number, quantity);
        this.type = type;
        this.destination = destination;
    }

    @Override
    void print() {
        System.out.println("xml.Order Number:"+this.number+" Type:" +this.type+" To: "+this.destination+" Quantity:"+ this.quantity);
    }
}
