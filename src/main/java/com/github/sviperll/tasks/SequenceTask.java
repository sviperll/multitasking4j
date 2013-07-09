/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class SequenceTask implements Task {
    private final Task[] tasks;
    public SequenceTask(Task[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public void stop() {
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
    }

    @Override
    public void run() {
        for (Task task: tasks)
            task.run();
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
}
