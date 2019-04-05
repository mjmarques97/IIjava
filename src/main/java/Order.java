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
    abstract void print();
}
