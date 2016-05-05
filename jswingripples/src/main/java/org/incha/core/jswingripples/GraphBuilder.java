package org.incha.core.jswingripples;

import org.apache.commons.logging.LogFactory;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGEdge;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 4/26/2016.
 * Part of org.incha.core.jswingripples.
 */

public class GraphBuilder {

    private static GraphBuilder instance = null; // singleton

    private JSwingRipplesEIG eig;
    private Graph graph;

    /**
     * Private constructor for singleton instance.
     */
    private GraphBuilder()
    {
        graph = new DefaultGraph("Dependencies");
        eig = null;
        try {
            graph.addAttribute("ui.stylesheet", "url(file://" + GraphBuilder.class.getClassLoader()
                    .getResource("graph.css").toString().substring(5) + ")");
        }
        catch (NullPointerException e)
        {
            LogFactory.getLog(this.getClass()).error("Missing graph stylesheet! - graph.css");
            System.exit(1);
        }
    }

    /**
     * Returns the current instance of the singleton GraphBuilder object.
     * @return GraphBuilder object instance.
     */
    public static GraphBuilder getInstance() {
        if ( instance == null )
        {
            instance = new GraphBuilder();
        }

        return instance;
    }

    /**
     * Associates this GraphBuilder instance with the provided EIG.
     * @param eig The EIG from which we want to build a visual graph.
     */
    public void addEIG(JSwingRipplesEIG eig)
    {
        this.eig = eig;
    }

    /**
     * Constructs a Graph object from the previously provided EIG instance and returns it.
     * @return Graph object representing the EIG model, or null if no EIG instance has been provided.
     */
    public Graph prepareGraph()
    {

        if ( eig == null )
            return null;

        JSwingRipplesEIGNode[] eigNodes = eig.getAllNodes();
        JSwingRipplesEIGEdge[] eigEdges = eig.getAllEdges();

        for ( JSwingRipplesEIGNode node : eigNodes )
            if ( graph.getNode(node.getFullName()) == null ) {
                Node n = graph.addNode(node.getFullName());
                n.addAttribute("label", node.getShortName());
            }

        for ( JSwingRipplesEIGEdge edge : eigEdges )
        {
            String eid = edge.getFromNode().getFullName() + " -> " + edge.getToNode().getFullName();
            if ( graph.getEdge(eid) == null)
                graph.addEdge(eid, edge.getFromNode().getFullName(), edge.getToNode().getFullName());
        }

    }
}
