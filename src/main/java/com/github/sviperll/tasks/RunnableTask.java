/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class RunnableTask implements Task {
    private final Runnable runnable;
    public RunnableTask(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void stop() {
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public void close() {
    }
}
