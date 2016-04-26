package org.incha.core.jswingripples;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGEdge;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

import java.util.ArrayList;
import java.util.List;

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
        graph = new SingleGraph("Dependencies");
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
            graph.addNode(node.getShortName());

        for ( JSwingRipplesEIGEdge edge : eigEdges )
        {
            String eid = edge.getFromNode().getShortName() + " -> " + edge.getToNode().getShortName();
            graph.addEdge(eid, edge.getFromNode().getShortName(), edge.getToNode().getShortName());
        }

    }



}
