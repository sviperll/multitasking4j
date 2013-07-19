/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.sviperll.tasks;

/**
 * This class represents Task that calls close method of the original task when run.
 * <p>
 * This class allowes to extract cleanup action of some task into standalone task
 */
class ClosingTask implements Task {
    private final Task task;

    /**
     * 
     * @param task task to extract cleanup behaviour from
     */
    public ClosingTask(Task task) {
        this.task = task;
    }

    /**
     * Calls #close method of the original task (@see Task#close)
     */
    @Override
    public void run() {
        task.close();
    }

    /**
     * does nothing
     */
    @Override
    public void stop() {
    }


    /**
     * does nothing
     */
    @Override
    public void close() {
    }
}
