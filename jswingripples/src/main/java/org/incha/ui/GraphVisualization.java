package org.incha.ui;

import org.graphstream.graph.Graph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.incha.core.jswingripples.GraphBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 4/28/16.
 * Part of org.incha.ui.
 */
public class GraphVisualization {

    public void showGraph()
    {
        Graph graph = GraphBuilder.getInstance().getGraph();

        Viewer v = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        v.enableAutoLayout();
        ViewPanel view =  v.addDefaultView(false);
        JInternalFrame frame = new JInternalFrame("graph");
        frame.getContentPane().setLayout(new BorderLayout());


        JDesktopPane viewArea = JSwingRipplesApplication.getInstance().getViewArea();
        frame.setBounds(0, 0, viewArea.getWidth(), viewArea.getHeight());
        frame.setClosable(true);
        frame.setMaximizable(true);
        frame.setVisible(true);
        frame.setResizable(true);
        frame.add(view, BorderLayout.CENTER);
        viewArea.add(frame);

    }

}

