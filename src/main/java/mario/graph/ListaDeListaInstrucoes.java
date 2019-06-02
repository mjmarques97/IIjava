package mario.graph;

import mario.order.InstrucaoTransformacoes;
import mario.order.Peca;

import java.util.List;

public class ListaDeListaInstrucoes {

    private boolean a=false;
    private boolean b=false;
    private boolean c=false;
    private boolean ab=false;
    private boolean ac=false;

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

    public void setPiece(Peca piece){
        instrucaoTransformacoes.get(0).setPecaInicial(piece);

    }

    private List<InstrucaoTransformacoes> instrucaoTransformacoes;

    public List<InstrucaoTransformacoes> getInstrucaoTransformacoes() {
        return instrucaoTransformacoes;
    }

    public ListaDeListaInstrucoes(List<InstrucaoTransformacoes> instrucaoTransformacoes) {
        this.instrucaoTransformacoes = instrucaoTransformacoes;
    }

    public void setInstrucaoTransformacoes(List<InstrucaoTransformacoes> instrucaoTransformacoes) {
        this.instrucaoTransformacoes = instrucaoTransformacoes;
    }
}
