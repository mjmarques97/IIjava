package mario.graph;

import mario.order.InstrucaoTransformacoes;
import mario.order.TransformationOrder;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;

public class GraphMES {
    private String cellThatHasMachines;
    private GraphTransformations graphTransformations=new GraphTransformations();

    private List<List<TransformationOrder>> lists=new ArrayList<>();

    public List<ListaDeListaInstrucoes> getInstructions(String from, String to){
        List<GraphPath<String,InstrucaoTransformacoes>> caminho= graphTransformations.getAllDirectedPaths().getAllPaths(from,to,false,10000);
      List<ListaDeListaInstrucoes> lists=retrieveNewList(caminho);

     lists= bestTime(lists);
     return lists;


    }

    public int listaAtualiazada(ListaDeListaInstrucoes listaDeListaInstrucoes){
        boolean machineA=false;
        boolean machineB=false;
        boolean machineC=false;
        int time=0;

        List<InstrucaoTransformacoes> lista=listaDeListaInstrucoes.getInstrucaoTransformacoes();

            for(InstrucaoTransformacoes instrucaoTransformacoes: lista){
                if(instrucaoTransformacoes.getMaquina().equals("A")){
                    machineA=true;
                }
                if(instrucaoTransformacoes.getMaquina().equals("B")){
                    machineB=true;
                }
                if(instrucaoTransformacoes.getMaquina().equals("C"))
                    machineC=true;

                if(machineA && machineB && machineC){
                    lista.remove(instrucaoTransformacoes);
                    return -1;
                }
                time=time+instrucaoTransformacoes.getTempo();

        }
            if(machineA){
                listaDeListaInstrucoes.setA(true);
                if(machineB){
                    listaDeListaInstrucoes.setA(false);
                    listaDeListaInstrucoes.setB(false);
                    listaDeListaInstrucoes.setAb(true);
                    return time;
                }
                if(machineC){
                    listaDeListaInstrucoes.setA(false);
                    listaDeListaInstrucoes.setB(false);
                    listaDeListaInstrucoes.setAc(true);
                    return time;
                }


            }
            listaDeListaInstrucoes.setB(machineB);
            listaDeListaInstrucoes.setC(machineC);
            return -1;

    }

    public List<ListaDeListaInstrucoes> bestTime(List<ListaDeListaInstrucoes> caminho){

        int time=listaAtualiazada(caminho.get(0));
        List<InstrucaoTransformacoes> melhoresInstrucoes=caminho.get(0).getInstrucaoTransformacoes();

        for(int i=0; i<caminho.size();i++){
            List<InstrucaoTransformacoes> temporario=melhoresInstrucoes;
            if(listaAtualiazada(caminho.get(i))>time){
                melhoresInstrucoes=caminho.get(i).getInstrucaoTransformacoes();
                caminho.set(0,new ListaDeListaInstrucoes(melhoresInstrucoes));
                caminho.set(i,new ListaDeListaInstrucoes(temporario));


            }
        }
        return caminho;
    }

    public List<ListaDeListaInstrucoes> retrieveNewList(List<GraphPath<String,InstrucaoTransformacoes>> caminho){
        List<ListaDeListaInstrucoes> lista=new ArrayList<>();
        List<List<InstrucaoTransformacoes>> list=new ArrayList<>();
        for(int i=0; i<caminho.size();i++){
           lista.add(new ListaDeListaInstrucoes(caminho.get(0).getEdgeList()));
        }
        return lista;
    }
}
