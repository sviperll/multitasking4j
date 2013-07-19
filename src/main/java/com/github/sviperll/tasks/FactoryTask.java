/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;


/**
 * Task that uses factory to build two sub-tasks:
 * 
 * <ol>
 * <li>Main task and
 * <li>Cleanup task
 * </ol>
 * 
 * Main task is used in #run method.
 * Cleanup task is used in #close method.
 */
class FactoryTask implements Task {
    private final TaskFactory factory;
    private volatile Task task = Tasks.doNothing();
    
    /**
     * 
     * @param factory factory to create main task and cleanup task
     */
    public FactoryTask(TaskFactory factory) {
        this.factory = factory;
    }

    @Override
    public void stop() {
        task.stop();
    }

    /**
     * Creates "main" task, calls it's #run and then calls it's #close method
     * @see Task#run 
     * @see Task#close
     */
    @Override
    public void run() {
        task = factory.createWorkTask();
        try {
            task.run();
        } finally {
            task.close();
            task = Tasks.doNothing();
        }
    }

    /**
     * Creates "cleanup" task, calls it's #run and then calls it's #close method
     * @see Task#run 
     * @see Task#close
     */
    @Override
    public void close() {
        Task unstoppableTask = factory.createClosingTask();
        try {
            unstoppableTask.run();
        } finally {
            unstoppableTask.close();
        }
    }
}
