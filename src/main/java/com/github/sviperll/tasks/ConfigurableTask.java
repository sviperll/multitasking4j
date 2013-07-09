/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class ConfigurableTask implements Task {
    private final Task task;
    public ConfigurableTask(Task task) {
        this.task = task;
    }

    @Override
    public void stop() {
        task.stop();
    }

    @Override
    public void run() {
        task.run();
    }

    @Override
    public void close() {
        task.close();
    }

    public ConfigurableTask repeat(long pause, TimeUnit unit) {
        return Tasks.repeat(task, pause, unit);
    }

    public ConfigurableTask repeat() {
        return Tasks.repeat(task);
    }

    public ConfigurableTask log(Logger logger, String name) {
        return Tasks.log(task, logger, name);
    }

    public ConfigurableTask swallowExceptions(Logger logger, long pause, TimeUnit unit) {
        return Tasks.swallowExceptions(task, logger, pause, unit);
    }

    public ConfigurableTask andThen(Task otherTask) {
        return Tasks.sequence(task, otherTask);
    }

    public ConfigurableTask closeAfterEachRun() {
        return Tasks.closeAfterEachRun(task);
    }

    public ConfigurableTask unstoppable() {
        return Tasks.unstoppable(task);
    }
}
