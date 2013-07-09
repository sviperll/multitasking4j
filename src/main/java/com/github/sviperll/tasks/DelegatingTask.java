/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class DelegatingTask implements Task {
    private volatile Task task;

    public DelegatingTask(Task task) {
        this.task = task;
    }

    public DelegatingTask() {
        this(new DoNothingTask());
    }

    public void set(Task task) {
        this.task = task;
    }

    @Override
    public void stop() {
        task.stop();
    }

    @Override
    public void run() {
        task.run();
    }

    @Override
    public void close() {
        task.close();
    }
}
