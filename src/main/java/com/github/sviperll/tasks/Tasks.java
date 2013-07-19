/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Facade-class for Task-object
 */
public class Tasks {
    private static final ConfigurableTask DO_NOTHING_TASK = new ConfigurableTask(new DoNothingTask());
    /**
     * Runs given task in a new thread
     * @param task task to run in new thread
     * @return running thread
     */
    public static Thread spawn(Task task) {
        Thread thread = new Thread(new TaskRunnable(task));
        thread.start();
        return thread;
    }

    /**
     * Creates task from Runnable object
     * <p>
     * When Task#run method of created task is called Runnable#run method is called in response
     * <p>
     * Task#close and Task#stop methods of created task does nothong
     * 
     * @param runnable runnable to base task on
     * @return new task
     */
    public static ConfigurableTask runnable(Runnable runnable) {
        return new ConfigurableTask(new RunnableTask(runnable));
    }

    /**
     * Creates task for given factory
     * <p>
     * When Task#run method of created task is called
     * TaskFactory#createWorkTask method of factory is called
     * and resulting task is runned. Cleanup for resulting task
     * is performed after.
     * <p>
     * When Task#close method of created task is called
     * TaskFactory#createClosingTask method of factory is called
     * and resulting task is runned. Cleanup for resulting task
     * is performed after.
     * 
     * @param factory to create "main" and "cleanup" tasks
     * @return new task
     */
    public static ConfigurableTask factory(TaskFactory factory) {
        return new ConfigurableTask(new FactoryTask(factory));
    }

    /**
     * Repeat given task with pauses between task invocation
     * @param task task to repeat
     * @param pause pause between invocations of argument task
     * @param unit time unit of pause
     * @return new task that when runned repeats task passed as a parameter
     */
    public static ConfigurableTask repeat(Task task, long pause, TimeUnit unit) {
        return new ConfigurableTask(new RepeatingTask(task, unit.toMillis(pause)));
    }

    /**
     * Repeat given task
     * @param task task to repeat
     * @return new task that when runned repeats task passed as a parameter
     */
    public static ConfigurableTask repeat(Task task) {
        return repeat(task, 0, TimeUnit.MILLISECONDS);
    }

    /**
     * Create task that logs invocation of it's methods
     * @param task task to perform actual work
     * @param logger logger to log method invocation by
     * @param name name to use in log messages
     * @return new task that logs it's methods invocation
     */
    public static ConfigurableTask log(Task task, Logger logger, String name) {
        return new ConfigurableTask(new LoggingTask(name, logger, task));
    }

    /**
     * Creates new task that catches all exceptions of it's subtask.
     * Catches all exceptions, each exception is logged and not rethrown.
     * 
     * @param task subtask to perform action work
     * @param logger logger to log messages with
     * @param pause time to sleep after caught exception
     * @param unit time unit of pause
     * @return new task that catches all exceptions
     */
    public static ConfigurableTask swallowExceptions(Task task, Logger logger, long pause, TimeUnit unit) {
        return new ConfigurableTask(new ExceptionSwallowingTask(task, logger, unit.toMillis(pause)));
    }

    /**
     * @return new task that does nothing
     */
    public static ConfigurableTask doNothing() {
        return DO_NOTHING_TASK;
    }

    /**
     * @param tasks array of subtasks
     * @return new task that performs each of it's subtasks in order
     */
    public static ConfigurableTask sequence(Task... tasks) {
        return new ConfigurableTask(new SequenceTask(tasks));
    }

    /**
     * @param tasks list of subtasks
     * @return new task that performs each of it's subtasks in order
     */
    public static ConfigurableTask sequence(List<? extends Task> tasks) {
        Task[] taskArray = tasks.toArray(new Task[tasks.size()]);
        return sequence(taskArray);
    }

    /**
     * @param tasks array of subtasks
     * @return new task that performs all it's subtasks in parallel
     */
    public static ConfigurableTask parallel(Task... tasks) {
        return new ConfigurableTask(new ParallelTask(tasks));
    }

    /**
     * @param tasks list of subtasks
     * @return new task that performs all it's subtasks in parallel
     */
    public static ConfigurableTask parallel(List<? extends Task> tasks) {
        Task[] taskArray = tasks.toArray(new Task[tasks.size()]);
        return parallel(taskArray);
    }

    /**
     * Returns task that behaves the same as a Task passed
     * but performes cleanup as the last action performed by run method.
     * <p>
     * #close method does nothing.
     * When #run method is called, #run method of the original task is called at first
     * and than #close method of the original task is called
     * 
     * @param task to base resulting task on
     * @return new task that performs subtask cleanup after subtask's invocation
     */
    public static ConfigurableTask closeAfterEachRun(Task task) {
        return new ConfigurableTask(new CloseOnEachRunTask(task));
    }

    /**
     * Wrapps task. Creates ConfigurableTask based on Task.
     * Behaviour of the resulting task is the same as original task
     * only convinience methods are added.
     * @param task task to wrap
     * @return  task with convinience methods
     */
    public static ConfigurableTask configure(Task task) {
        if (task instanceof ConfigurableTask)
            return (ConfigurableTask)task;
        else
            return new ConfigurableTask(task);
    }

    /**
     * Returns task that can't be stopped externally. Task#stop method of resulting task does nothing
     * @param task to base behaviour on
     * @return new task that behaves like original task bu can't be stopped
     */
    public static ConfigurableTask unstoppable(Task task) {
        return new ConfigurableTask(new UnstoppableTask(task));
    }

    /**
     * Returns task that doesn't perfom cleanup. Task#close method of resulting task does nothing
     * @param task to base behaviour on
     * @return new task that behaves like original task but doesn't perform cleanup
     */
    public static ConfigurableTask withoutClose(Task task) {
        return new ConfigurableTask(new WithoutCloseTask(task));
    }

    /**
     * Returns task that performs cleanup for the original task when run.
     * Resulting task's Task#run method calls original task's Task#close method
     * 
     * @param task to base behaviour on
     * @return new task that performs cleanup for the original task
     */
    public static ConfigurableTask closingTaskFor(Task task) {
        return new ConfigurableTask(new ClosingTask(task));
    }

    /**
     * Returns task that behaves like the original task but performs additinal cleanup action
     * Resulting task's Task#close method calls original task's Task#close method and
     * given closingAction
     * 
     * @param task to base behaviour on
     * @param closingAction action to close on cleanup
     * @return new task that performs additinal cleanup action
     */
    public static ConfigurableTask withAdditinalClosingAction(Task task, Runnable closingAction) {
        return new ConfigurableTask(new AdditionalClosingActionTask(task, closingAction));
    }
}
