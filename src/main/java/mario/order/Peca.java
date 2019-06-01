package mario.order;

import mario.plc.Tapete;
import mario.plc.TapeteRotator;

public class Peca {
    private String tipo;
    private Order order;
    private Tapete tapete;
    private Tapete tapeteParaOndeVai;
    private String celulaParaOndeVai="JACHEGOU";

    public void setCelulaParaOndeVai(int i){
        this.celulaParaOndeVai="C"+i;
    }

    public void setCelulaParaOndeVai(String celulaParaOndeVai){
       String string=celulaParaOndeVai.toUpperCase();
        if(string.equals("UNLOAD")){
            this.celulaParaOndeVai="C5";
            return;
        }
        this.celulaParaOndeVai=string;
    }

    public void checkJaChegou(){
        if (celulaParaOndeVai.equals("JACHEGOU") || tapeteParaOndeVai==null){
            return;
        }
        if(tapeteParaOndeVai instanceof TapeteRotator){
            TapeteRotator tapeteRotator=(TapeteRotator) tapeteParaOndeVai;
            if(tapeteRotator.getVerificador().equals(celulaParaOndeVai+"T2")){
                tapeteRotator.moveDown();
                celulaParaOndeVai="JACHEGOU";
            }
            else{
                tapeteRotator.moveRight();
            }
        }

    }
    public void printPecaNoTapete(){
        if(order!=null) {
            System.out.println("Peça do Tipo " + this.getTipo() + " com a ordem n: " + this.getOrder().getNumber() + " acabou de chegar ao tapete " + this.getTapete().getPlcVariableName());
            return;
        }
        System.out.println("Peça do Tipo " + this.getTipo() + " sem ordem acabou de chegar ao tapete " + this.getTapete().getPlcVariableName());

    }

    public Peca(int tipo, Tapete tapete){
        this.tipo="P"+tipo;
        this.tapeteParaOndeVai=tapete;
    }

    public Peca(String tipo,Tapete tapete) {
        this.tipo = tipo.toUpperCase();
        this.tapeteParaOndeVai=tapete;
    }

    public Tapete getTapeteParaOndeVai() {
        return tapeteParaOndeVai;
    }

    public void setTapeteParaOndeVai(Tapete tapeteParaOndeVai) {
        this.tapeteParaOndeVai = tapeteParaOndeVai;
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

    public Tapete getTapete() {
        return tapete;
    }

    public Peca(String tipo, Tapete tapete, Order order) {
        this.tipo = tipo.toUpperCase();
        this.order = order;
        this.tapete=tapete;
    }
    public Peca(int tipo, Tapete tapete, Order order){
        this.tipo = "P"+tipo;
        this.order = order;
        this.tapete=tapete;

    }
}
