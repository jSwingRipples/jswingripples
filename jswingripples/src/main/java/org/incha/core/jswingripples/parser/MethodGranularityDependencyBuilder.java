package org.incha.core.jswingripples.parser;
/*
 * Created on Dec 5, 2005
 *
 */
import java.awt.Window;

import javax.swing.JOptionPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.incha.core.jswingripples.JRipplesModule;
import org.incha.core.jswingripples.JRipplesModuleRunner;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.ui.JSwingRipplesApplication;
import org.incha.ui.TaskProgressMonitor;
import org.incha.ui.util.ModalContext;
import org.incha.ui.util.NullMonitor;
import org.incha.ui.util.RunnableWithProgress;
/**
 * @author Maksym Petrenko
 *
 */
public class MethodGranularityDependencyBuilder implements JRipplesModule {
    private static final Log log = LogFactory.getLog(MethodGranularityDependencyBuilder.class);
    private final JSwingRipplesEIG eig;

    private class AnalysisJob implements RunnableWithProgress {
        private JRipplesModuleRunner moduleRunner;
        public AnalysisJob(JRipplesModuleRunner moduleRunner) {
            this.moduleRunner = moduleRunner;
        }

        @Override
        public void run(final TaskProgressMonitor monitor) {
            monitor.beginTask("Building call graph",10);
            new Analyzer(eig, monitor, new InteractiveTask.TaskListener() {
                @Override
                public void taskSuccessful() {
                    monitor.done();
                    monitor.setTaskName("Analysis Successful");
                    moduleRunner.moduleFinished(); // tells the moduleRunner this thread is done
                }

                @Override
                public void taskFailure() {
                    // report failure peacefully, without notifying the module runner!
                    // TODO: Should anything happen if this module fails?
                    monitor.done();
                }
            }).start();
        }
    }

    /**
     * @param eig the eight.
     */
    public MethodGranularityDependencyBuilder(final JSwingRipplesEIG eig) {
        super();
        this.eig = eig;
    }

    @Override
    public void runModuleWithinRunner(JRipplesModuleRunner moduleRunner) {
        final Window window = JSwingRipplesApplication.getInstance();
        if (window != null) {
            try {
                AnalysisJob job=new AnalysisJob(moduleRunner);
                final JSwingRipplesApplication app = JSwingRipplesApplication.getInstance();
                try {
                    ModalContext.run(job, false, app.getProgressMonitor());
                } finally {
                    app.getProgressMonitor().done();
                }
            }
            catch (final Exception e) {
                log.error(e);
            }
        } else {
            final NullMonitor monitor=new NullMonitor();
            AnalysisJob job =new AnalysisJob(moduleRunner);
            try {
                job.run(monitor);
            } catch (final Exception e) {
                log.error(e);
            }
        }
    }

    protected Analyzer createAnalyzer(final TaskProgressMonitor monitor) {
        return new Analyzer(eig, monitor);
    }
}
