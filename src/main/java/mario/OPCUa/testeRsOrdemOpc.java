/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste_rs_ordem.opc;

import mario.OPCUa.OPCUAConnection;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;

import java.util.concurrent.ExecutionException;

/**
 * @author Diogo
 */

public class testeRsOrdemOpc {
    

    /**
     * @param args the command line arguments
     */
    
public static OPCUAConnection MyConnection ;
public static OpcUaClient client;
public static String aux = "DESKTOP-ID8VMRO"; //mudar para o teu identificador de pc
public static String Client = "opc.tcp://"+aux+":4840";
 
public static void main(String[] args) throws ExecutionException, InterruptedException {
    
    MyConnection = new OPCUAConnection(Client);
    MyConnection.makeConnection();
    run_order(1,2,3,2,5);
}
public static void run_order(int number, int t1,int t2,int zd,int quant) {
    Object check;
    String cellName,variable;
    cellName = "GVL";//nome do POU
    variable="numero_ordem";//Nome da variavel
    int i=0;
    while(i!=quant){
        OPCUAConnection.getValue(cellName, variable);//le o valor da variavel
        check=OPCUAConnection.getValueL();//retorna o valor da variavel em object
        if((short)check!=0){ //verifica se o valor esta a false dando cast do object para bool
            OPCUAConnection.setValueInt(cellName,"tipo_atual",t1);
            OPCUAConnection.setValueInt(cellName,"tipo_final",t2);
            OPCUAConnection.setValueInt(cellName,"zona_descarga",zd);
            OPCUAConnection.setValueInt(cellName,"numero_ordem",number); //da set da informação da ordem se estiver a falso
           i++; //incrementa o i   
        }
        
    }    
        
    }
public static void run_one_order(int number, int t1,int t2,int zd) {
    Object check;
    String cellName,variable;
    cellName = "GVL";//nome do POU
    variable="check";//Nome da variavel
    OPCUAConnection.getValue(cellName, "check");//le o valor da variavel
    check=OPCUAConnection.getValueL();//retorna o valor da variavel em object
    if((short)check==0){ //verifica se o valor esta a false dando cast do object para bool
        OPCUAConnection.setValueInt(cellName,"tipo_atual",t1);
        OPCUAConnection.setValueInt(cellName,"tipo_final",t2);
        OPCUAConnection.setValueInt(cellName,"zona_descarga",zd);
        OPCUAConnection.setValueInt(cellName,"numero_ordem",number); //da set da informação da ordem se estiver a falso
    }
        
 }    
        
}
