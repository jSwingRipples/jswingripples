package org.incha.core.jswingripples.parser;

/**
 * Created by stefano on 19-10-16.
 */
public abstract class InteractiveTask extends Thread {
    public interface TaskListener {
        void taskSucessful();
        void taskFailure();
    }

    protected TaskListener listener;

    public static TaskListener getDummyListener() {
        return new TaskListener() {
            @Override
            public void taskSucessful() { }
            @Override
            public void taskFailure() { }
        };
    }

    public InteractiveTask() {
        listener = getDummyListener();
    }

    public TaskListener getListener() {
        return listener;
    }
}
