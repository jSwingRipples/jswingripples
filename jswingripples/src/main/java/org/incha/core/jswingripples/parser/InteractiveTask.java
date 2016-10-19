package org.incha.core.jswingripples.parser;

/**
 * Created by stefano on 19-10-16.
 */
public abstract class InteractiveTask extends Thread {
    protected TaskListener listener;

    public InteractiveTask() {
        listener = getDummyListener();
    }

    interface TaskListener {
        void taskSucessful();
        void taskFailure();
    }

    public static TaskListener getDummyListener() {
        return new TaskListener() {
            @Override
            public void taskSucessful() { }
            @Override
            public void taskFailure() { }
        };
    }
}
