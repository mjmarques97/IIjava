/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.OPCUa;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author Diogo & Marco
 */

public class OPCUATest {

    /**
     * @param args the command line arguments
     */
    public static OPCUAConnection myConnection;
    public static OpcUaClient client;
    public static String aux = "DESKTOP-4ODUT2H";
    public static String clientName = "opc.tcp://"+aux+":4840";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        myConnection = new OPCUAConnection(clientName);
        myConnection.makeConnection();


        //Inicialização das variáveis
        String cellName, variable;
        Boolean value;
        String C1 = "PLC_PRG.C1";
        String C2 = "PLC_PRG.C2";
        String C3 = "PLC_PRG.C3";
        String C4 = "PLC_PRG.C4";
        String C5 = "PLC_PRG.C5";
        String top_R = "Top_Rotator_Horizontal";
        String dir_D = "direcao_baixo";
        variable = "T5_expect_unload";

        //variable = "Register_Armazem";
        boolean valueR= true;
        value= true;

        /*Funções: getValue para saber o valor de uma variavel
         *          setValue para escrever o valor numa variavel
         */
        //System.out.println("--------------Value Get--------------");
        //OPCUAConnection.getValue(plcCellName,variable);
        System.out.println("--------------Value Change--------------");
        OPCUAConnection.setValue(C1,top_R, valueR);
        OPCUAConnection.setValue(C2,top_R, valueR);
        OPCUAConnection.setValue(C3,top_R, valueR);
        OPCUAConnection.setValue(C4,top_R, valueR);
        OPCUAConnection.setValue(C5,variable, false);

        OPCUAConnection.getValue(C3,"T4_tempo_Servico");


        OPCUAConnection.setValue(C3,top_R, false);
        OPCUAConnection.setValue(C3,"T4_espera_peca", true);
        String hello=OPCUAConnection.getValue(C3,"T4_espera_peca");
        System.out.println("awiufhweifuhwefu "+ hello);
        OPCUAConnection.setValue(C3,"T4_espera_peca", false);
        hello=OPCUAConnection.getValue(C3,"T4_espera_peca");
        System.out.println("New "+ hello);
        OPCUAConnection.setValue(C3,"T4_ferramenta", 5);
        OPCUAConnection.setValue(C3,"T4_tempo_Servico", 3000);
        OPCUAConnection.setValue(C3,"T5_"+dir_D, true);
        OPCUAConnection.setValue(C3,"T6_"+dir_D, false);

        OPCUAConnection.setValue("GVL","teste", 1);

    }

}
