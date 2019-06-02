package mario.order;

import mario.order.Peca;
import mario.plc.CelulaFactory;
import mario.plc.Tapete;
import mario.plc.TapeteMaquina;

import javax.swing.*;

public class InstrucaoTransformacoes {
    private Peca pecaInicial;
    private String pecaFinal;
    private String maquina;
    private int ferramenta;
    private int tempo;
    private boolean ab=false;
    private boolean ac=false;
    private boolean a=false;
    private boolean b=false;
    private boolean c=false;
    private TapeteMaquina tapeteAir;


    public boolean isAb() {
        return ab;
    }

    public void setAb(boolean ab) {
        this.ab = ab;
    }

    public boolean isAc() {
        return ac;
    }

    public void setAc(boolean ac) {
        this.ac = ac;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean isC() {
        return c;
    }

    public void setC(boolean c) {
        this.c = c;
    }

    public InstrucaoTransformacoes(Peca pecaInicial, String pecaFinal, String maquina, int ferramenta, int tempo) {
        this.pecaInicial=pecaInicial;
        this.pecaFinal = pecaFinal;
        this.maquina = maquina;
        this.tempo = tempo;
        this.ferramenta=ferramenta;
    }

    public InstrucaoTransformacoes(String pecaInitial, String pecaFinal, String maquina,int ferramenta ,int tempo){
        this.pecaInicial=new Peca(pecaInitial);
        this.pecaFinal = pecaFinal;
        this.maquina = maquina;
        this.tempo = tempo;
        this.ferramenta=ferramenta;



    }
    public void setDescobrirTapete(){
        this.tapeteAir=descobrirTapetea();
    }

    public TapeteMaquina descobrirTapete(){
        return this.tapeteAir;
    }





    @Override
    public String toString() {
        return "Peca inicial: "+pecaInicial.getTipo()+" peca final: "+pecaFinal+" Maquina: M"+maquina+" ferramenta: " +ferramenta+ " tempo: "+tempo;
    }

    public void changePiece(Peca peca){
        this.pecaInicial=peca;
    }

    public Peca getPecaInicial() {
        return pecaInicial;
    }

    public String getPecaFinal() {
        return pecaFinal;
    }

    public String getMaquina() {
        return maquina;
    }

    public int getTempo() {
        return tempo;
    }

    public void setPecaInicial(Peca pecaInicial) {
        this.pecaInicial = pecaInicial;
        this.tapeteAir=descobrirTapetea();
    }

    public void setPecaFinal(String pecaFinal) {
        this.pecaFinal = pecaFinal;
    }

    public void setMaquina(String maquina) {
        this.maquina = maquina;
    }

    public int getFerramenta() {
        return ferramenta;
    }

    public void setFerramenta(int ferramenta) {
        this.ferramenta = ferramenta;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public TapeteMaquina descobrirTapetea(){

        //String variableName=pecaInicial.getNomeDaCelulaParaOndeVai();
       // System.out.println(pecaInicial.getCelulaParaOndeVai());

        CelulaFactory celulaFactory=(CelulaFactory) pecaInicial.getCelulaParaOndeVai();



       // System.out.println(celulaFactory.getName());


        if (celulaFactory.getMaquina4().getTipo().equals(maquina)){
            //System.out.println(celulaFactory.getMaquina6().getTipo());
                return celulaFactory.getMaquina4();
            }

        if (celulaFactory.getMaquina5().getTipo().equals(maquina)){
            //System.out.println(celulaFactory.getMaquina5().getTipo());
        return celulaFactory.getMaquina5();}

        if (celulaFactory.getMaquina6().getTipo().equals(maquina)){
            //System.out.println(celulaFactory.getMaquina4().getTipo());
        return celulaFactory.getMaquina6();}


        return null;


    }
}
