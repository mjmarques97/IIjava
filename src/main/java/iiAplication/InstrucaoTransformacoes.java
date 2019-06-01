package iiAplication;

import mario.order.Peca;

public class InstrucaoTransformacoes {
    private Peca peca;
    private String pecaFinal;
    private String maquina;
    private int tempo;

    public InstrucaoTransformacoes(Peca peca, String pecaFinal, String maquina, int tempo) {
        this.peca = peca;
        this.pecaFinal = pecaFinal;
        this.maquina = maquina;
        this.tempo = tempo;
    }

    public Peca getPeca() {
        return peca;
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
}
