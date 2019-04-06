package order;

import java.util.Objects;

/***
 * Classe Abstrata order
 */

abstract class Order {
    final String number;
    final String quantity;

    Order(String number, String quantity) {
        this.number = number;
        this.quantity=quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return number.equals(order.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    abstract void print();
}
