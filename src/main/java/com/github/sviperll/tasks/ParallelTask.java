/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class ParallelTask implements Task {
    private final Task[] tasks;
    private volatile ThreadManager threadManager = new ThreadManager(Collections.<Thread>emptyList());
    public ParallelTask(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public void run() {
        List<Thread> threads = new ArrayList<Thread>(tasks.length);
        try {
            for (Task task: tasks) {
                threads.add(Tasks.spawn(task));
            }
        } finally {
            threadManager = new ThreadManager(threads);
            threadManager.join();
        }
    }

    @Override
    public void stop() {
        try {
            RuntimeException exception = null;
            for (Task task: tasks) {
                try {
                    task.stop();
                } catch (RuntimeException ex) {
                    exception = ex;
                }
            }
            if (exception != null)
                throw exception;
        } finally {
            threadManager.interrupt();
        }
    }

    @Override
    public void close() {
        RuntimeException exception = null;
        for (Task task: tasks) {
            try {
                task.close();
            } catch (RuntimeException ex) {
                exception = ex;
            }
        }
        if (exception != null)
            throw exception;
    }

    private static class ThreadManager {
        private final List<Thread> threads;

        private ThreadManager(List<Thread> threads) {
            this.threads = threads;
        }

        private void join() {
            for (Thread thread: threads) {
                for (;;) {
                    try {
                        thread.join();
                        break;
                    } catch (InterruptedException ex) {
                        continue;
                    }
                }
            }
        }

        private void interrupt() {
            for (Thread thread: threads) {
                thread.interrupt();
            }
        }
    }
}
