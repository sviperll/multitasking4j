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
public class ExceptionSwallowingTask implements Task {
    private final Task task;
    private final Logger logger;
    private final long pause;

    public ExceptionSwallowingTask(Task task, Logger logger, long pause) {
        this.task = task;
        this.logger = logger;
        this.pause = pause;
    }

    @Override
    public void stop() {
        try {
            task.stop();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex1) {
            }
        }
    }

    @Override
    public void run() {
        try {
            task.run();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex1) {
            }
        }
    }

    @Override
    public void close() {
        try {
            task.close();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex1) {
            }
        }
    }
}
