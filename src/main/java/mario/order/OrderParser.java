package mario.order;



import mario.xml.XMLParser;

import java.util.ArrayList;
import java.util.List;

/***
 * XMLparser extrai os elementos do ficheiro XML, esta classe transforma esses elementos em ordens.
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

    /***
     * Adiciona ordem de Unload à lista
     * @param order Ordem a adicionar
     * @throws DuplicateOrderException Ordem não pode ser duplicada
     */
    private void unLoadOrdersAdd(UnloadOrder order) throws DuplicateOrderException{
        if(this.unloadOrders.contains(order)){
            throw new DuplicateOrderException("Order already exists");
        }
        this.unloadOrders.add(order);
    }

    /***
     * Adiciona ordem de Transformação à lista
     * @param order Ordem a adicionar
     * @throws DuplicateOrderException Ordem não pode ser duplicada
     */

    private void transformationsAdd(TransformationOrder order) throws DuplicateOrderException{
        if (this.transformationOrders.contains(order)) {
            throw new DuplicateOrderException("Order already exists");
        }
        this.transformationOrders.add(order);
    }

    private void parseOrder(String order, String type){
        String toBeProccessed=order+type;

        if(toBeProccessed.contains("TransformFrom=")){
            parseTransformation(toBeProccessed.split(";"));
        }
        else if(toBeProccessed.contains("UnloadType=")){
            parseUnload(toBeProccessed.split(";"));
        }
        else {
            System.out.println("Request Stores");
        }
    }

    /***
     * Como os elementos aparecem aos pares (explicado na classe XMLParser), método processa lista de strings de XMLParser aos pares também
     * @param xml self-explanatory
     */
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

    /***
     * Imprime ordens de transformação
     */
    public void printTransformations(){
        System.out.println("Transformations");
        if(transformationOrders.isEmpty()){
            System.out.println("No transformations to print");
            return;
        }

        for(TransformationOrder order: transformationOrders)
            order.print();

    }

    /***
     * Imprime ordens de Unload
     */
    public void printUnloads(){
        System.out.println("Unloads:");
        if(unloadOrders.isEmpty()){
            System.out.println("No Unloads to print.");
            return;
        }
        for(UnloadOrder order: unloadOrders)
            order.print();
    }



    /***
     * Imprime todas as ordens
     */
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
