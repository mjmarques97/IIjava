package mario.plc;

import mario.OPCUa.OPCUAConnection;
import mario.order.Peca;
import mario.order.RequestStores;

/***
 * Armazem. Array 8,2,
 * primeira posição representa o tipo de peça,
 * IE: posição 0 representa peça P1, posição 8 representa peça P9
 * Segundo valor representa quantidade de peças no armazem
 * IE: pieceList[0][1]: quantidade de peças P1
 *
 */
public class Storage extends Celula {
    private  Integer[][] pieceList={
            {1,27},
            {2,27},
            {3,27},
            {4,27},
            {5,27},
            {6,27},
            {7,27},
            {8,27},
            {9,27},};
    private RequestStores requestStore=new RequestStores();
    private boolean fallingEdge;
    private boolean risingEdge;
    private Tapete tapeteUnload;
    private Tapete tapeteLoad;
    private boolean iCanWork=true;

    public Storage() {
        this.tapeteUnload = new Tapete("Sensores_Peca", "AT1");
        tapeteLoad = new Tapete("Sensores_Peca", "AT2");
        this.name="Storage";

    }
    public void setUp(){
        tapeteUnload.addtapetesAssociado(this.getCelula1().getTapeteAEsquerdaDoRotadorDeCima());
        tapeteUnload.armazemAssociado=this;
        tapeteLoad.addtapetesAssociado(this.getCelula1().getTapeteAEsquedaDoRotadorDeBaixo());
        tapeteLoad.armazemAssociado=this;
    }


    /***
     *Define quantidade de uma determinada peça no armazém
     * @param piece Nome da peça, peça pode estar em minusculas ou maiusculas IE: P1
     * @param quantity quantidade;
     */
    public void setQuantity(String piece,int quantity){
        this.pieceList[Integer.parseInt(piece.toUpperCase().replace("P",""))-1][1]=quantity;
    }

    public Integer[][] getPieceList() {
        return pieceList;
    }

    public void setPieceList(Integer[][] pieceList) {
        this.pieceList = pieceList;
    }

    /***
     * Returna quantidade de uma determinada peça no armazém
     * @param piece Nome da peça, peça pode estar em minusculas ou maiusculas IE: P1
     * @return
     */
    public int getQuantity(String piece){
             return this.pieceList[Integer.parseInt(piece.toUpperCase().replace("P",""))-1][1];

    }

    public String requestStores(){
        return requestStore.returnCurrentStores(pieceList);
    }
    public void youCantWork(){
        this.iCanWork=false;
    }
    public void youCanWork(){
        this.iCanWork=true;
    }

    public boolean canWork(){
        return iCanWork;
    }

    public void retrievePieceOPCua(Peca peca) {
        if(peca==null || !iCanWork || this.getQuantity(peca.getTipo())==0)
            return;
        int type=Integer.parseInt(peca.getTipo().substring(1));
        if (tapeteUnload.getPecaNoTapeteTipo().equals("NAOTEMPECA")) {
            if (type >= 1 && type <= 9) {
                tapeteUnload.setPecaEsperadaNoTapete(peca);
                //!Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca", "AT1") Original
                if (tapeteUnload.getPecaNoTapeteTipo().equals("NAOTEMPECA")) {
                    OPCUAConnection.setValue("GVL", "Peca_Remover", type);
                    youCantWork();
                    OPCUAConnection.setValue("PLC_PRG.C1","T8_ready_send",false);
                }
            }
        }
    }
    public Tapete getTapeteADireitaDeODeUnload(){

        Tapete tapete= tapeteUnload.tapetesAssociados.get(0);
        System.out.println(tapete.plcVariableName);
        return tapete;

    }
    public Tapete getTapeteADireitaDeODeLoad(){
        Tapete tapete= tapeteLoad.tapetesAssociados.get(0);
        System.out.println(tapete.plcVariableName);
        return tapete;
    }


    public boolean hasPieceTapeteDiscarga(){
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","AT1"));
    }

    public boolean hasPieceTapeteEntradaDoArmazem(){
        return Boolean.parseBoolean(OPCUAConnection.getValue("Sensores_Peca","AT2"));
    }

    public Tapete getTapeteUnload() {
        return tapeteUnload;
    }

    public Tapete getTapeteLoad() {
        return tapeteLoad;
    }

    public void printAll(){
        System.out.println("Tapetes Associados para Storage");
        System.out.println("Load");
        for(Tapete tapete: tapeteLoad.tapetesAssociados)
            System.out.println(tapete.getPlcCellName()+" "+tapete.plcVariableName);
        System.out.println("Unload");
        for(Tapete tapete: tapeteUnload.tapetesAssociados)
            System.out.println(tapete.getPlcCellName()+" "+tapete.plcVariableName);
    }
    public boolean entrouNoTapeteDeUnload(){
        return risingEdge;
    }

    public boolean saiuTapeteUnload(){
        return fallingEdge;
    }

    public void checkEachCycle(){
        tapeteUnload.checkFallingAndRisingOrRisingEdge();
        tapeteLoad.checkFallingAndRisingOrRisingEdge();
        if(tapeteUnload.pecaNoTapete.getTipo().equals("NAOTEMPECA") && tapeteLoad.pecaNoTapete.getTipo().equals("NAOTEMPECA"))
            OPCUAConnection.setValue("PLC_PRG.C1","T8_ready_send",true);
    }

}
