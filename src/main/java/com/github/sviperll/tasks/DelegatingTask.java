/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

/**
 * This class allows to dynamically change behaviour of the task
 * 
 * #set method can be used to change current behaviour
 */
public class DelegatingTask implements Task {
    private volatile Task task;

    /**
     * 
     * @param task initial behaviour of new instance
     */
    public DelegatingTask(Task task) {
        this.task = task;
    }

    /**
     * Creates new instance with "doing nothing" initial behaviour
     * @see Tasks#doNothing()
     */
    public DelegatingTask() {
        this(new DoNothingTask());
    }

    /**
     * Changes current behaviour to new passed as a parameter
     * 
     * @param task new behaviour
     */
    public void set(Task task) {
        this.task = task;
    }

    /**
     * runs #stop method of current behaviour, @see Task#stop
     */
    @Override
    public void stop() {
        task.stop();
    }

    /**
     * runs #run method of current behaviour, @see Task#run
     */
    @Override
    public void run() {
        task.run();
    }

    /**
     * runs #close method of current behaviour, @see Task#close
     */
    @Override
    public void close() {
        task.close();
    }
}
