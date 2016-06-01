package org.incha.core.jswingripples;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGEdge;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

public class NodeSearchBuilder {

    private JSwingRipplesEIG eig;
    private Graph graph;
    public NodeSearchBuilder(String name)
    {
        graph = new DefaultGraph(name);
        prepareGraph(name);
    }


    public void addEIG(JSwingRipplesEIG eig)
    {
        this.eig = eig;
    }
    
    private JSwingRipplesEIGNode[] addRelatedNodes(JSwingRipplesEIGNode node){
        addNode(node);
    	JSwingRipplesEIGNode[] neighbors = eig.getAllAnyNodeNeighbors(node);
    	for (JSwingRipplesEIGNode n : neighbors){
    		if (graph.getNode(n.getFullName()) == null){
    			addNode(n);
    		}
    	}
    	return neighbors;
    }
    
    private void addNode(JSwingRipplesEIGNode node){
    	Node n = graph.addNode(node.getFullName());
    	n.addAttribute("label", node.getShortName());
    }
    
    private void addEdgeIfExisting(JSwingRipplesEIGNode node1, JSwingRipplesEIGNode node2){
    	JSwingRipplesEIGEdge edge = eig.getEdge(node1, node2);
    	if (edge != null){
    		String eid = node1.getFullName() + " -> " + node2.getFullName();
    		if (graph.getEdge(eid) == null){
    		graph.addEdge(eid, graph.getNode(node1.getFullName()), graph.getNode(node2.getFullName()));
    		}
    	}
    }
    
    private void computeAndAddEdges(JSwingRipplesEIGNode node, JSwingRipplesEIGNode[] neighbors){
    	for (JSwingRipplesEIGNode n : neighbors){
    		addEdgeIfExisting(node,n);
    	}
    	for (JSwingRipplesEIGNode n : neighbors){
    		for (JSwingRipplesEIGNode m : neighbors){
    			addEdgeIfExisting(n,m);
    		}
    	}
    }

    private void prepareGraph(String name)
    {
        JSwingRipplesEIGNode[] eigNodes = eig.getAllNodes();

        for ( JSwingRipplesEIGNode node : eigNodes )
            	if (node.getFullName() == name){
                computeAndAddEdges(node,addRelatedNodes(node));
            	}

    }
    
    public Graph getGraph(){
    	return graph;
    }


}

