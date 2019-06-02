// JAVA program to print all  
// paths from a source to 
// destination.
package mario.graph;

import mario.order.InstrucaoTransformacoes;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultDirectedGraph;

// A directed graph using
// adjacency list representation 
public class GraphTransformations {
    private  Graph<String, InstrucaoTransformacoes> graph=new DefaultDirectedGraph<String, InstrucaoTransformacoes>(InstrucaoTransformacoes.class);
    private String p1="P1";
    private String p2="P2";
    private String p3="P3";
    private String p4="P4";
    private String p5="P5";
    private String p6="P6";
    private String p7="P7";
    private String p8="P8";
    private String p9="P9";

    private AllDirectedPaths<String ,InstrucaoTransformacoes> allDirectedPaths;

    public GraphTransformations() {
       /* String p1="P1";
        String p2="P2";
        String p3="P3";
        String p4="P4";
        String p5="P5";
        String p6="P6";
        String p7="P7";
        String p8="P8";
        String p9="P9";*/
        graph.addVertex(p1);
        graph.addVertex(p2);
        graph.addVertex(p3);
        graph.addVertex(p4);
        graph.addVertex(p5);
        graph.addVertex(p6);
        graph.addVertex(p7);
        graph.addVertex(p8);
        graph.addVertex(p9);

        graph.addEdge(p1,p2,new InstrucaoTransformacoes(p1,p2,"A",1,10000));
        graph.addEdge(p1,p4,new InstrucaoTransformacoes(p1,p4,"A",2,5000));
        graph.addEdge(p1,p7,new InstrucaoTransformacoes(p1,p7,"A",3,20000));
        graph.addEdge(p5,p6,new InstrucaoTransformacoes(p5,p6,"A",2,3000));
        graph.addEdge(p9,p5,new InstrucaoTransformacoes(p9,p5,"A",3,8000));
        graph.addEdge(p2,p3,new InstrucaoTransformacoes(p2,p3,"B",1,20000));
        graph.addEdge(p4,p5,new InstrucaoTransformacoes(p4,p5,"B",2,20000));
        graph.addEdge(p2,p5,new InstrucaoTransformacoes(p2,p5,"C",1,5000));
        graph.addEdge(p7,p8,new InstrucaoTransformacoes(p7,p8,"C",2,8000));
        graph.addEdge(p8,p9,new InstrucaoTransformacoes(p8,p9,"C",3,8000));
        allDirectedPaths= new AllDirectedPaths(graph);
    }

    public AllDirectedPaths<String, InstrucaoTransformacoes> getAllDirectedPaths() {
        return allDirectedPaths;
    }

    public Graph<String, InstrucaoTransformacoes> getGraph() {
        return graph;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    public String getP3() {
        return p3;
    }

    public String getP4() {
        return p4;
    }

    public String getP5() {
        return p5;
    }

    public String getP6() {
        return p6;
    }

    public String getP7() {
        return p7;
    }

    public String getP8() {
        return p8;
    }

    public String getP9() {
        return p9;
    }
}


