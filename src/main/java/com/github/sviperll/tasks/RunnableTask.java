/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 * Task that runs given Runnable action
 */
public class RunnableTask implements Task {
    private final Runnable runnable;
    
    /**
     * 
     * @param runnable action to perform
     */
    public RunnableTask(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * do nothing
     */
    @Override
    public void stop() {
    }

    /**
     * Calls given runnable
     */
    @Override
    public void run() {
        runnable.run();
    }

    /**
     * Do nothing
     */
    @Override
    public void close() {
    }
}
