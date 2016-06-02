package org.incha.core.jswingripples;

import org.apache.commons.logging.LogFactory;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.incha.core.jswingripples.eig.*;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.ui.TaskProgressMonitor;
import org.incha.ui.jripples.EIGStatusMarks;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 4/26/2016.
 * Part of org.incha.core.jswingripples.
 */

public class GraphBuilder implements JSwingRipplesEIGListener{

    private static GraphBuilder instance = null; // singleton

    private JSwingRipplesEIG eig;
    private Graph graph;

    /**
     * Private constructor for singleton instance.
     */
    private GraphBuilder()
    {
        eig = null;
        resetGraph();
    }

    /**
     * Resets the internal graph and returns it.
     * @return Graph object.
     */
    public Graph resetGraph()
    {
        graph = new DefaultGraph("Dependencies");
        try {
            graph.addAttribute("ui.stylesheet", "url(file://" + GraphBuilder.class.getClassLoader()
                    .getResource("graph.css").toString().substring(5) + ")");
        }
        catch (NullPointerException e) {
            LogFactory.getLog(this.getClass()).error("Missing graph stylesheet! - graph.css");
            System.exit(1);
        }

        return graph;
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
    public void prepargiteGraph()
    {

        if ( eig == null )
            return;

        //resetGraph();

        JSwingRipplesEIGNode[] eigNodes = eig.getAllNodes();
        JSwingRipplesEIGEdge[] eigEdges = eig.getAllEdges();

        final TaskProgressMonitor monitor = JSwingRipplesApplication.getInstance().getProgressMonitor();
        monitor.beginTask("Building graph: Adding nodes.", eigNodes.length);
        for ( int i = 0; i < eigNodes.length; i++ ) {
            JSwingRipplesEIGNode node = eigNodes[i];
            monitor.worked(i);
            monitor.setTaskName("Adding node " + node.getShortName());
            if (graph.getNode(node.getFullName()) == null) {
                LogFactory.getLog(this.getClass()).info(node.getMark());
                Node n = graph.addNode(node.getFullName());
                n.addAttribute("label", node.getShortName());
            }
        }
        monitor.done();

        monitor.beginTask("Building graph: Adding edges.", eigEdges.length);
        for ( int i = 0; i < eigEdges.length; i++ )
        {
            JSwingRipplesEIGEdge edge = eigEdges[i];
            monitor.worked(i);
            String eid = edge.getFromNode().getFullName() + " -> " + edge.getToNode().getFullName();
            monitor.setTaskName("Adding edge " + eid);
            if ( graph.getEdge(eid) == null)
                graph.addEdge(eid, edge.getFromNode().getFullName(), edge.getToNode().getFullName());
        }
        monitor.done();
    }

    public Graph getGraph() { return graph; }

    @Override
    public void jRipplesEIGChanged(JSwingRipplesEIGEvent event) {

        if (!event.hasNodeEvents()) return;

        final JSwingRipplesEIGNodeEvent[] nodeEvents=event.getNodeTypedEvents(
                new int[] {JSwingRipplesEIGNodeEvent.NODE_MARK_CHANGED, JSwingRipplesEIGNodeEvent.NODE_REMOVED,
                        JSwingRipplesEIGNodeEvent.NODE_ADDED});
        if (nodeEvents.length==0) return;

        for (int i=0;i<nodeEvents.length;i++) {
            final JSwingRipplesEIGNode changedNode = nodeEvents[i].getSource();
            switch (nodeEvents[i].getEventType()) {
                case JSwingRipplesEIGNodeEvent.NODE_REMOVED: {
                    //TODO
                    break;
                }
                case JSwingRipplesEIGNodeEvent.NODE_ADDED: {
                    //TODO
                    break;
                }
                case JSwingRipplesEIGNodeEvent.NODE_MARK_CHANGED: {
                    final String mark=changedNode.getMark();
                    if (mark!=null)
                    {

                        switch (mark)
                        {
                            case EIGStatusMarks.CHANGED:
                                graph.getNode(changedNode.getFullName()).setAttribute("ui.class", "changed");
                                break;
                            case EIGStatusMarks.IMPACTED:
                                graph.getNode(changedNode.getFullName()).setAttribute("ui.class", "impacted");
                                break;
                            case EIGStatusMarks.NEXT_VISIT:
                                graph.getNode(changedNode.getFullName()).setAttribute("ui.class", "next");
                                break;
                            default:
                                graph.getNode(changedNode.getFullName()).setAttribute("ui.class", "blank");
                                break;
                        }

                    }
                    break;
                }
            }
        }
    }
}
