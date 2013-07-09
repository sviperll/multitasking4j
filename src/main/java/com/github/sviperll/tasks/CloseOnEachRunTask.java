/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class CloseOnEachRunTask implements Task {
    private final Task task;
    public CloseOnEachRunTask(Task task) {
        this.task = task;
    }

    @Override
    public void stop() {
        task.stop();
    }

    @Override
    public void close() {
    }

    @Override
    public void run() {
        try {
            task.run();
        } finally {
            task.close();
        }
    }
}
