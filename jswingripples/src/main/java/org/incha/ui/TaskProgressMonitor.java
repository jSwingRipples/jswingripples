package org.incha.ui;

import org.incha.core.jswingripples.parser.InteractiveTask;

import javax.swing.JPanel;
import java.awt.LayoutManager;

public abstract class TaskProgressMonitor extends JPanel {

    public InteractiveTask currentTask;

    public TaskProgressMonitor() {}

    public TaskProgressMonitor(LayoutManager layoutManager) {
        super(layoutManager);
    }

    protected void doCancel() {
        if (currentTask == null) {
            return;
        }
        if (currentTask.isAlive() && !currentTask.isInterrupted()) {
            currentTask.stop();
        }
        currentTask.getListener().taskFailure();
    }

    public void setTask(InteractiveTask task) {
        this.currentTask = task;
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

    public abstract void setMaximum(int max);
}
