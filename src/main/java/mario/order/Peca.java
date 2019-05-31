package mario.order;

public class Peca {
    private String tipo;
    private Order order;

    public Peca(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public Order getOrder() {
        return order;
    }

    public Peca(String tipo, Order order) {
        this.tipo = tipo;
        this.order = order;
    }
}
