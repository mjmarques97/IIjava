package mario.order;

import java.util.Objects;

/***
 * Classe Abstrata mario.order
 */

public abstract class Order {
    protected final String number;
   protected String quantity;


    Order(String number, String quantity) {
        this.number = number;
        this.quantity=quantity;
    }

    public void decreaseQuantity(){
        int a=Integer.valueOf(quantity);
        this.quantity=Integer.toString(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return number.equals(order.number);
    }

    public String getNumber() {
        return number;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    abstract void print();
}
