package org.incha.ui.stats;

import org.apache.commons.logging.LogFactory;
import org.graphstream.ui.view.ViewerPipe;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 6/6/16.
 * Part of org.incha.ui.stats.
 */
public class DependencyGraphViewerListener extends AbstractGraphViewerListener {
    public DependencyGraphViewerListener(ViewerPipe pipe) {
        super(pipe);
    }

    @Override
    public void viewClosed(String viewName) {

    }

    @Override
    public void buttonPushed(String id) {
        LogFactory.getFactory().getInstance(this.getClass()).info("Click " + id);

    }

    @Override
    public void buttonReleased(String id) {

    }
}
