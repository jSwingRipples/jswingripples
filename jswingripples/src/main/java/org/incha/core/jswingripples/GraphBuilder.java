package org.incha.core.jswingripples;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.view.Viewer;
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
    private GraphBuilder()
    {
        graph = new DefaultGraph("Dependencies");
        graph.addAttribute("ui.stylesheet", "url(file://" + GraphBuilder.class.getClassLoader().getResource("graph.css").toString().substring(5) + ")");
    }

    public static GraphBuilder getInstance() {
        if ( instance == null )
        {
            instance = new GraphBuilder();
        }

        return instance;
    }

    public void addEIG(JSwingRipplesEIG eig)
    {
        this.eig = eig;
    }

    public void prepareGraph()
    {
        JSwingRipplesEIGNode[] eigNodes = eig.getAllNodes();
        JSwingRipplesEIGEdge[] eigEdges = eig.getAllEdges();

        for ( JSwingRipplesEIGNode node : eigNodes )
            if ( graph.getNode(node.getFullName()) == null )
                graph.addNode(node.getFullName());

        for ( JSwingRipplesEIGEdge edge : eigEdges )
        {
            String eid = edge.getFromNode().getFullName() + " -> " + edge.getToNode().getFullName();
            if ( graph.getEdge(eid) == null)
                graph.addEdge(eid, edge.getFromNode().getFullName(), edge.getToNode().getFullName());
        }

    }

    public void showGraph()
    {
        Viewer v = graph.display(true);
    }



}
