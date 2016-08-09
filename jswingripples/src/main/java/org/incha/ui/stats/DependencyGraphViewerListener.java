package org.incha.ui.stats;

import org.eclipse.jdt.core.IMember;
import org.graphstream.ui.view.ViewerPipe;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;
import org.incha.ui.jripples.EIGStatusMarks;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 6/6/16.
 * Part of org.incha.ui.stats.
 */
public class DependencyGraphViewerListener extends AbstractGraphViewerListener {
    public DependencyGraphViewerListener(ViewerPipe pipe, JSwingRipplesEIG eig) {

        super(pipe, eig);

    }

    @Override
    public void viewClosed(String viewName) {

    }

    @Override
    public void buttonPushed(String id) {

    }

    @Override
    public void buttonReleased(String id) {
        JSwingRipplesEIGNode node = eig.getNode((IMember) graph.getNode(id).getAttribute("IMember"));
        String mark = node.getMark();
        switch (mark)
        {
            case EIGStatusMarks.BLANK:
                node.setMark(EIGStatusMarks.NEXT_VISIT);
                break;
            case EIGStatusMarks.NEXT_VISIT:
                node.setMark(EIGStatusMarks.VISITED_CONTINUE);
                break;
            case EIGStatusMarks.VISITED_CONTINUE:
                node.setMark(EIGStatusMarks.IMPACTED);
                break;
            case EIGStatusMarks.IMPACTED:
                node.setMark(EIGStatusMarks.CHANGED);
                break;
            default:
                node.setMark(EIGStatusMarks.BLANK);
                break;
        }
    }
}
