/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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


/**
 *
 * @author Diogo
 */
public class T4 {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) throws ExecutionException, InterruptedException {
       EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints("opc.tcp://DESKTOP-ID8VMRO:4840").get();
       OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
       cfg.setEndpoint(endpoints[0]);
       OpcUaClient client = new OpcUaClient(cfg.build());
       client.connect().get();
        System.out.println("----------------------------");
       Object ValorL = null;
       NodeId nodeidstring=new NodeId(4,"|var|CODESYS Control Win V3 x64.Application.GVL.a");
       DataValue value = client.readValue(0, TimestampsToReturn.Both, nodeidstring) .get();
       ValorL = value.getValue().getValue();
       System.out.println("o valor da variável e  : "+ ValorL); 
        boolean i = true;
       Variant v = new Variant(i);
       DataValue dv = new DataValue(v);
       client.writeValue(nodeidstring,dv).get();
    }
    public OpcUaClient connect() throws InterruptedException, ExecutionException{
        EndpointDescription[] endpoints = UaTcpStackClient.getEndpoints("opc.tcp://DESKTOP-ID8VMRO:4840").get();
       OpcUaClientConfigBuilder cfg = new OpcUaClientConfigBuilder();
       cfg.setEndpoint(endpoints[0]);
       OpcUaClient client = new OpcUaClient(cfg.build());
       client.connect().get();
       return client;
}  
    
}
