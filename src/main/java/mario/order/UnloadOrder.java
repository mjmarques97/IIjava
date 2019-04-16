package mario.order;

/***
 * Ordem de Unload, como é uma ordem herda atributos do Pai, Order
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
    public void print() {
        System.out.println("Order Number:"+this.number+" Type:" +this.type+" To:"+this.destination+" Quantity:"+ this.quantity);
    }
}
