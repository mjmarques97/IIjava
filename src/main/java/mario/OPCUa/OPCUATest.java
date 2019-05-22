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
	public static String aux = "mario";
	public static String clientName = "opc.tcp://"+aux+":4840";

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		
		myConnection = new OPCUAConnection(clientName);
		myConnection.makeConnection();
		
		
		//Inicialização das variáveis
		String cellName, variable;
		Boolean value;
		cellName = "eu";
		variable = "Hello";
		value= false;
		
		/*Funções: getValue para saber o valor de uma variavel
		*          setValue para escrever o valor numa variavel
		*/
		System.out.println("--------------Value Get--------------");
		OPCUAConnection.getValue(cellName,variable);
		System.out.println("--------------Value Change--------------");
		OPCUAConnection.setValue(cellName,variable,value);
	}

}
