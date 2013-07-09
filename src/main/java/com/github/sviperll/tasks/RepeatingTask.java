/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class RepeatingTask implements Task {
    private volatile boolean doExit = false;
    private final Task task;
    private final long pause;

    public RepeatingTask(Task task, long pause) {
        this.task = task;
        this.pause = pause;
    }

    @Override
    public void stop() {
        doExit = true;
        task.stop();
    }

    @Override
    public void run() {
        while (!doExit) {
            task.run();
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex) {
            }
        }
    }

    @Override
    public void close() {
        task.close();
    }
}
