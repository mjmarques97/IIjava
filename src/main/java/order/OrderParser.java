package order;



import xml.XMLParser;

import java.util.ArrayList;
import java.util.List;

/***
 * Pega nas strings que XMLparser tirou do ficheiro XML e coloca-as "bonitinhas"
 */
public class OrderParser {
    private static final String LINES = "\n ---------------------------";
    private List<TransformationOrder> transformationOrders=new ArrayList<>();
    private List<UnloadOrder> unloadOrders=new ArrayList<>();




    private void parseUnload(String[] unloadOrder){
        String orderNumber=unloadOrder[0].substring(12);
        String type=unloadOrder[1].substring(11);
        String destination=unloadOrder[2].substring(12);
        String quantity=unloadOrder[3].substring(9);
        try {
            this.unLoadOrdersAdd(new UnloadOrder(orderNumber, quantity, type, destination));
        }
        catch (DuplicateOrderException e){
            System.out.println("Order already exists");
        }
    }

    private void parseTransformation(String[] transformOrder){
        String orderNumber=transformOrder[0].substring(12);
        String from=transformOrder[1].substring(14);
        String to=transformOrder[2].substring(3);
        String quantity=transformOrder[3].substring(9);
        try {
            this.transformationsAdd(new TransformationOrder(orderNumber, quantity, from, to));
        }
        catch (DuplicateOrderException e){
            System.out.println("Order already exists");
        }

    }
    private void unLoadOrdersAdd(UnloadOrder order) throws DuplicateOrderException{
        if(this.unloadOrders.contains(order)){
            throw new DuplicateOrderException("Order already exists");
        }
        this.unloadOrders.add(order);
    }
    private void transformationsAdd(TransformationOrder order) throws DuplicateOrderException{
        if (this.transformationOrders.contains(order)) {
            throw new DuplicateOrderException("Order already exists");
        }
        this.transformationOrders.add(order);
    }

    private void parseOrder(String order, String type){
        String tobeproccessed=order+type;

        if(tobeproccessed.contains("TransformFrom=")){
            parseTransformation(tobeproccessed.split(";"));
        }
        else if(tobeproccessed.contains("UnloadType=")){
            parseUnload(tobeproccessed.split(";"));
        }
        else {
            System.out.println("Request Stores");
        }
    }

    public OrderParser(XMLParser xml) {
        if (xml.getUnparsedOrder().size()==0){
            return;
        }

       List<String> list=xml.getUnparsedOrder();
       while(!list.isEmpty()){

           String a1=list.get(0);
           String a2=list.get(1);
           list.remove(0);
           list.remove(0);
           this.parseOrder(a1,a2);
       }

    }

    public void printTransformations(){
        System.out.println("Transformations");
        if(transformationOrders.isEmpty()){
            System.out.println("No transformations to print");
            return;
        }

        for(TransformationOrder order: transformationOrders)
            order.print();

    }

    public void printUnloads(){
        System.out.println("Unloads:");
        if(unloadOrders.isEmpty()){
            System.out.println("No Unloads to print.");
            return;
        }
        for(UnloadOrder order: unloadOrders)
            order.print();
    }

    public void printAll(){
        if(this.transformationOrders.isEmpty() && unloadOrders.isEmpty()){
            System.out.println("Get Stores");
            System.out.println(LINES);
            return;
        }
        this.printTransformations();
        this.printUnloads();
        System.out.println(LINES);
    }
}
