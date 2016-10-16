package org.incha.ui;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public abstract class TaskProgressMonitor extends JPanel {

    public TaskProgressMonitor() {}

    public TaskProgressMonitor(LayoutManager layoutManager) {
        super(layoutManager);
    }

    /**
     * @param name task name.
     */
    public abstract void setTaskName(String name);
    /**
     * @return task name.
     */
    public abstract String getTaskName();
    /**
     * @param taskName task name.
     * @param max max value.
     */
    public abstract void beginTask(String taskName, int max);
    /**
     * @return maximum value.
     */
    public abstract int getMaximum();
    /**
     * @return progress value.
     */
    public abstract int getProgress();
    /**
     * Notify task finished.
     */
    public abstract void done();
    /**
     * @return check task canceled.
     */
    public abstract boolean isCanceled();
    /**
     * @param value sets the task finished.
     */
    public abstract void setCanceled(boolean value);
    /**
     * @param value new current value.
     */
    public abstract void worked(int value);
    /**
     * @param max
     */
    public abstract void setMaximum(int max);
}
