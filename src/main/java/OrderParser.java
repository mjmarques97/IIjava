import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.List;

public class OrderParser {
    private List<Order> transformationOrders=new ArrayList<>();
    private List<Order> unloadOrders=new ArrayList<>();




    private void parseUnload(String[] unloadOrder){
        String orderNumber=unloadOrder[0].substring(12);
        String type=unloadOrder[1].substring(11);
        String destination=unloadOrder[2].substring(12);
        String quantity=unloadOrder[3].substring(9);
        unloadOrders.add(new UnloadOrder(orderNumber,quantity,type,destination));
    }

    private void parseTransformation(String[] transformOrder){
        String orderNumber=transformOrder[0].substring(12);
        String from=transformOrder[1].substring(14);
        String to=transformOrder[2].substring(3);
        String quantity=transformOrder[3].substring(9);
        this.transformationOrders.add(new TransformationOrder(orderNumber,quantity,from,to));

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

    public OrderParser(xmlParser xml) {
       List<String> list=xml.getUnparsedOrder();
       while(!list.isEmpty()){

           String a1=list.get(0);
           String a2=list.get(1);
           list.remove(0);
           list.remove(0);
          // System.out.println(a1+a2);
           this.parseOrder(a1,a2);
       }
    }

    public void printTransformations(){
        System.out.println("Transformations");
        if(transformationOrders.isEmpty()){
            System.out.println("No transformations to print");
            return;
        }

        for(Order order: transformationOrders)
            order.print();

    }

    public void printUnloads(){
        System.out.println("Unloads:");
        if(unloadOrders.isEmpty()){
            System.out.println("No Unloads to print.");
            return;
        }
        for(Order order: unloadOrders)
            order.print();
    }

    public void printAll(){
        if(this.transformationOrders.isEmpty() && unloadOrders.isEmpty()){
            System.out.println("Get Stores");
            return;
        }
        this.printTransformations();
        this.printUnloads();
    }
}
