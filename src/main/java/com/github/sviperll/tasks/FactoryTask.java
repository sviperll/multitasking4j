/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class FactoryTask implements Task {
    private final TaskFactory factory;
    private volatile Task task = new DoNothingTask();
    public FactoryTask(TaskFactory factory) {
        this.factory = factory;
    }

    @Override
    public void stop() {
        task.stop();
    }

    @Override
    public void close() {
        task = factory.createTask();
        task.close();
    }

    @Override
    public void run() {
        task = factory.createTask();
        task.run();
    }
}
