package org.incha.ui.stats;

import org.apache.commons.logging.LogFactory;
import org.graphstream.ui.view.ViewerPipe;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

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

    }
}
