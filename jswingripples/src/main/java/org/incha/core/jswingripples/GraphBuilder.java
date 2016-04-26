package org.incha.core.jswingripples;

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
    private String[] nodes;
    private String[] edges; //TODO: Fix (tuple or struct?)
    private GraphBuilder(){}

    public static GraphBuilder getInstance() {
        if ( instance == null )
        {
            instance = new GraphBuilder();
        }

        return instance;
    }



}
