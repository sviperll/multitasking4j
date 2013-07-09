/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
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
