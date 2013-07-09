/*
 * Copyright 2013 Victor Nazarov <asviraspossible@gmail.com>.
 */
package com.github.sviperll.tasks;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Victor Nazarov <asviraspossible@gmail.com>
 */
public class LoggingTask implements Task {
    private final String name;
    private final Logger logger;
    private final Task task;
    public LoggingTask(String name, Logger logger, Task task) {
        this.name = name;
        this.logger = logger;
        this.task = task;
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, "{0}: exiting...", name);
        task.stop();
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "{0}: started", name);
        task.run();
        logger.log(Level.INFO, "{0}: finished", name);
    }

    @Override
    public void close() {
        logger.log(Level.INFO, "{0}: closing...", name);
        task.close();
        logger.log(Level.INFO, "{0}: closed", name);
    }
}
