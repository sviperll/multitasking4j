/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 * This class represents Task that behaves the same as a Task passed to the constructor
 * but performes cleanup as the last action performed by run method.
 * <p>
 * #close method does nothing.
 * When #run method is called, #run method of the original task is called at first
 * and than #close method of the original task is called
 */
class CloseOnEachRunTask implements Task {
    private final Task task;
    
    /**
     * 
     * @param task original task to base behaviour on
     */
    public CloseOnEachRunTask(Task task) {
        this.task = task;
    }

    /**
     * Calls #stop method of the original task @see Task#stop
     */
    @Override
    public void stop() {
        task.stop();
    }

    /**
     * Does nothing
     */
    @Override
    public void close() {
    }

    /**
     * Calls #run method of the original task @see Task#run
     * and than calls #close method of the original task @see Task#close
     */
    @Override
    public void run() {
        try {
            task.run();
        } finally {
            task.close();
        }
    }
}
