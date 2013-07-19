/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.sviperll.tasks;

/**
 *
 * @author vir
 */
class WithoutCloseTask implements Task {
    private final Task task;

    public WithoutCloseTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.run();
    }

    @Override
    public void stop() {
        task.stop();
    }

    @Override
    public void close() {
    }
    
}
