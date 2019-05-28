package mario.storage;

import mario.OPCUa.OPCUAConnection;
import mario.order.RequestStores;

/***
 * Armazem. Array 8,2,
 * primeira posição representa o tipo de peça,
 * IE: posição 0 representa peça P1, posição 8 representa peça P9
 * Segundo valor representa quantidade de peças no armazem
 * IE: pieceList[0][1]: quantidade de peças P1
 *
 */
public class Storage {
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

    public void retrievePieceOPCua(int pieceType) {
        if (pieceType >= 1 && pieceType <= 9) {
            if (!Boolean.parseBoolean(OPCUAConnection.getValue("GVL", "AT1"))) {
                OPCUAConnection.setValue("GVL", "Peca_Remover", pieceType);

            }
        }
    }


}
