/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class Tasks {
    public static Thread spawn(Task task) {
        Thread thread = new Thread(new TaskRunnable(task));
        thread.start();
        return thread;
    }

    public static ConfigurableTask runnable(Runnable runnable) {
        return new ConfigurableTask(new RunnableTask(runnable));
    }

    public static ConfigurableTask factory(TaskFactory runnable) {
        return new ConfigurableTask(new FactoryTask(runnable));
    }

    public static ConfigurableTask repeat(Task task, long pause, TimeUnit unit) {
        return new ConfigurableTask(new RepeatingTask(task, unit.toMillis(pause)));
    }

    public static ConfigurableTask repeat(Task task) {
        return repeat(task, 0, TimeUnit.MILLISECONDS);
    }

    public static ConfigurableTask log(Task task, Logger logger, String name) {
        return new ConfigurableTask(new LoggingTask(name, logger, task));
    }

    public static ConfigurableTask swallowExceptions(Task task, Logger logger, long pause, TimeUnit unit) {
        return new ConfigurableTask(new ExceptionSwallowingTask(task, logger, unit.toMillis(pause)));
    }

    public static ConfigurableTask doNothing() {
        return new ConfigurableTask(new DoNothingTask());
    }

    public static ConfigurableTask sequence(Task... tasks) {
        return new ConfigurableTask(new SequenceTask(tasks));
    }

    public static ConfigurableTask sequence(List<? extends Task> tasks) {
        Task[] taskArray = tasks.toArray(new Task[tasks.size()]);
        return sequence(taskArray);
    }

    public static ConfigurableTask parallel(Task... tasks) {
        return new ConfigurableTask(new ParallelTask(tasks));
    }

    public static ConfigurableTask parallel(List<? extends Task> tasks) {
        Task[] taskArray = tasks.toArray(new Task[tasks.size()]);
        return parallel(taskArray);
    }

    public static ConfigurableTask closeAfterEachRun(Task task) {
        return new ConfigurableTask(new CloseOnEachRunTask(task));
    }

    public static ConfigurableTask configure(Task task) {
        if (task instanceof ConfigurableTask)
            return (ConfigurableTask)task;
        else
            return new ConfigurableTask(task);
    }

    public static ConfigurableTask unstoppable(Task task) {
        return new ConfigurableTask(new UnstoppableTask(task));
    }
}
