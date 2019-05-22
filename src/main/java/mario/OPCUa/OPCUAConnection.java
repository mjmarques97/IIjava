package mario.OPCUa;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;

import java.util.concurrent.ExecutionException;

public class OPCUAConnection {
	
	private static OpcUaClient client;
	private static Object ValueL;
	private String Client_Name;
	private static int id_node = 4;
	private static String aux = "|var|CODESYS Control Win V3 x64.Application.";
	
	public static OpcUaClient getClient() {
		return client;
	}

	public static void setClient(OpcUaClient client) {
		OPCUAConnection.client = client;
	}

	public static Object getValueL() {
		return ValueL;
	}

	public static void setValueL(Object valueL) {
		ValueL = valueL;
	}

	public OPCUAConnection(String client_Name) {
		super();
		Client_Name = client_Name;
	}
	
	//Função de Conexão
	public void makeConnection() {
		
		EndpointDescription[] endpoints;
		try {
			endpoints = UaTcpStackClient.getEndpoints(Client_Name).get();
			OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
			cfg.setEndpoint(endpoints[0]);
			client = new OpcUaClient(cfg.build());
			client.connect().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/*Função para ler o valor de uma variavel em especifico de uma célula em especifico
	 * String celulaName -> contém o nome do POU da célula
	 * String VarName -> contém o nome da variavál que se pretende ler o valor
	 */
	static void getValue(String cellName, String VarName) {
		String aux1;
		aux1 = aux + cellName + "." + VarName;
		NodeId nodeidstring = new NodeId(id_node, aux1);
		DataValue value;
		try {
		value = client.readValue(0, TimestampsToReturn.Both, nodeidstring).get();
		setValueL(value);
		ValueL = ((DataValue)getValueL()).getValue().getValue();
		System.out.println("O valor da variável é: " + ValueL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*Função para inserir valores
	 * String celulaName -> contém o nome do POU da célula
	 * String VarName -> contém o nome da variavál que se pretende mudar o valor
	 * boolean ValueSet -> contém o valor "true" ou "false" que se pretende atribuir à variável
	 */
	static void setValue(String cellName, String VarName, boolean ValueSet) {
		String aux2;
		aux2 = aux + cellName + "." + VarName;
		NodeId nodeidstring = new NodeId(id_node, aux2);
		
		boolean i = ValueSet;
		Variant v = new Variant(i);
		DataValue dv = new DataValue(v);
		
		try {
			getClient().writeValue(nodeidstring, dv).get();
			System.out.println("Variavel alterada para: " + ((DataValue) client.readValue(0, TimestampsToReturn.Both, nodeidstring).get()).getValue().getValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        static void setValueInt(String cellName, String VarName, int ValueSet) {
		String aux2;
		aux2 = aux + cellName + "." + VarName;
		NodeId nodeidstring = new NodeId(id_node, aux2);
		
		int i = ValueSet;
		Variant v = new Variant((short) i);
		DataValue dv = new DataValue(v);
		
		try {
			getClient().writeValue(nodeidstring, dv).get();
			System.out.println("Variavel alterada para: " + ((DataValue) client.readValue(0, TimestampsToReturn.Both, nodeidstring).get()).getValue().getValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}