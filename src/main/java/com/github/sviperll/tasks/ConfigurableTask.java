/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Wrapper that adds convinience methods to any task
 */
public class ConfigurableTask implements Task {
    private final Task task;
    /**
     * 
     * @param task task to inherit behaviour from
     */
    public ConfigurableTask(Task task) {
        this.task = task;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void stop() {
        task.stop();
    }


    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        task.run();
    }


    /**
     * @inheritDoc
     */
    @Override
    public void close() {
        task.close();
    }

    /**
     * Returns new task. When run new task calls #run method of this task
     * repeatly with the pause passed as a parameter
     * 
     * @param pause pause between invocations of this task's #run method
     * @param unit TimeUnit to use for pause
     * @return returns new task
     */
    public ConfigurableTask repeat(long pause, TimeUnit unit) {
        return Tasks.repeat(task, pause, unit);
    }

    /**
     * Returns new task. When run new task calls #run method of this task
     * repeatly without pauses
     * 
     * @return returns new task
     */
    public ConfigurableTask repeat() {
        return Tasks.repeat(task);
    }

    /**
     * Returns new task.
     * When methods of new task are called their invocations are logged by the logger passed as a parameter
     * 
     * @param logger logger that is used to log method invocations
     * @param name name of the task to use in log records
     * @return returns new task
     */
    public ConfigurableTask log(Logger logger, String name) {
        return Tasks.log(task, logger, name);
    }

    /**
     * Returns new task.
     * When methods of new task it calls according methods of this task
     * When any exceptions are thrown by the invocation of this task's methods
     * exceptions are not propogated, instead they are logged by given logger
     * and suppressed
     * 
     * @param logger logger that is used to log exceptions
     * @return returns new task
     */
    public ConfigurableTask swallowExceptions(Logger logger, long pause, TimeUnit unit) {
        return Tasks.swallowExceptions(task, logger, pause, unit);
    }

    /**
     * Returns new task.
     * When #run method is called, at first the #run method of this task is called
     * and then the #run method of thatTask is called
     * 
     * @param thatTask task to run after this task
     * @return returns new task
     */
    public ConfigurableTask andThen(Task thatTask) {
        return Tasks.sequence(this.task, thatTask);
    }

    /**
     * Returns new task.
     * When #run method is called, at first the #run method of this task is called
     * and then the #close method of this task is called
     * <p>
     * Close method of resulting task does nothing
     * 
     * @return returns new task
     */
    public ConfigurableTask closeAfterEachRun() {
        return Tasks.closeAfterEachRun(task);
    }

    /**
     * Returns new task.
     * New task is unstoppable, i. e. #stop method of resulting task does nothing
     * 
     * @return returns new task
     */
    public ConfigurableTask unstoppable() {
        return Tasks.unstoppable(task);
    }

    /**
     * Returns new task.
     * New task performs no cleanup, i. e. #close method of resulting task does nothing
     * 
     * @return returns new task
     */
    public ConfigurableTask withoutClose() {
        return Tasks.withoutClose(task);
    }

    /**
     * Returns new task.
     * New task performs cleanup of this task as it's main work, i. e.
     * when #run method of resulting task is called #close method of this task is called in response
     * 
     * @return returns new task
     */
    public ConfigurableTask closingTask() {
        return Tasks.closingTaskFor(task);
    }

    /**
     * Returns new task.
     * New task performs cleanup action in additinal to this task original cleanup, i. e.
     * when #close method of resulting task is called #close method of this task is called in response
     * and than closingAction passed as a parameter is called
     * 
     * @param closingAction additinal closing action
     * @return returns new task
     */
    public ConfigurableTask withAdditinalClosingAction(Runnable closingAction) {
        return Tasks.withAdditinalClosingAction(task, closingAction);
    }
}
