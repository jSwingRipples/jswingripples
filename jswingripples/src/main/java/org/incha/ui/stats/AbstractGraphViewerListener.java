package org.incha.ui.stats;

import org.graphstream.graph.Graph;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;

/**
 * Created by Manuel Olguín (molguin@dcc.uchile.cl) on 6/6/16.
 * Part of org.incha.ui.stats.
 */
public abstract class AbstractGraphViewerListener implements ViewerListener{
    private ViewerPipe pipe;
    private boolean loop;
    private Thread pumpt;
    protected JSwingRipplesEIG eig;
    protected Graph graph;

    AbstractGraphViewerListener(ViewerPipe pipe, JSwingRipplesEIG eig)
    {
        this.pipe = pipe;
        this.loop = true;
        this.eig = eig;

        pumpt = new Thread(new PumpRunnable());
        pumpt.start();
    }

    public void setGraph(Graph g) { this.graph = g; }

    public void stopPumpThread()
    {
        this.loop = false;
        try {
            pumpt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public abstract void viewClosed(String viewName);

    @Override
    public abstract void buttonPushed(String id);

    @Override
    public abstract void buttonReleased(String id);

    private class PumpRunnable implements Runnable
    {

        @Override
        public void run() {
            while(loop)
            {
                pipe.pump();
                try
                {
                    Thread.sleep(10);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
