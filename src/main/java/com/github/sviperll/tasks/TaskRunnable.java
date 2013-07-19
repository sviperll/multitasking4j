/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 * Wrapps given task in Runnable object
 */
class TaskRunnable implements Runnable {
    private final Task task;
    public TaskRunnable(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }
}
