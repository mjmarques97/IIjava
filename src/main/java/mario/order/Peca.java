package mario.order;

import mario.plc.Tapete;

public class Peca {
    private String tipo;
    private Order order;
    private Tapete tapete;

    public Peca(String tipo,Tapete tapete) {
        this.tipo = tipo;
        this.tapete=tapete;
    }

    //VER MELHOR DESPUES
    public Peca(String name){
        this.tipo=name;
    }
    public String getTipo() {
        return tipo;
    }

    public Order getOrder() {
        return order;
    }

    public void changeType(String NAOTEMPECA){
        this.tipo=NAOTEMPECA;
    }

    public void setTapete(Tapete tapete) {
        this.tapete = tapete;
    }

    public Peca(String tipo, Tapete tapete, Order order) {
        this.tipo = tipo;
        this.order = order;
        this.tapete=tapete;
    }
}
