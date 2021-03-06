package mario.order;

/***
 * Ordem de transformação, como é uma mario.order herda atributos do pai, Order
 */

public class TransformationOrder extends Order {
    private final String from;
    private final String to;


    public TransformationOrder(String number, String quantity, String from, String to) {
        super(number, quantity);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public void print() {
        System.out.println("Order Number:"+this.number+" From:" +this.from+" To:"+this.to+ " Quantity: "+ this.quantity);
    }
}
