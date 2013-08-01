sviperll-task
=============

Java multitask programming library.

Installation
------------

Use maven dependency:

```xml
        <dependency>
            <groupId>com.github.sviperll</groupId>
            <artifactId>sviperll-task</artifactId>
            <version>1.0</version>
        </dependency>
```

Quick Start
-----------

Library operates on tasks. Here is an example of two tasks running in parallel:

```java
ConfigurableTask a = Tasks.runnable(new Runnable() {
    @Override
    public void run() {
        System.out.println("A");
    }
});
a = a.repeat(1, TimeUnit.SECOND).withAdditinalClosingAction(new Runnable() {
    @Override
    public void run() {
        System.out.println("A is closing");
    }
});
ConfigurableTask b = Tasks.runnable(new Runnable() {
    @Override
    public void run() {
        System.out.println("B");
    }
});
b = b.repeat(2, TimeUnit.SECOND).withAdditinalClosingAction(new Runnable() {
    @Override
    public void run() {
        System.out.println("B is closing");
    }
});
try (ConfigurableTask composition = Tasks.parallel(a, b)) {
    Thread thread = Tasks.spawn(composition);
    Thread.sleep(5000);
    composition.stop();
    thread.join();
}

```

In this example two tasks are created and a composed into one task.
Task A prints "A" every one second.
Task B prints "B" every two seconds.
Composed task prints "A"'s and "B"'s in parallel.
Composed task is started in it's own thread and is stopped after 5 seconds of work.
When composed task finishes it's work and consequently it's thread exits it's cleanup routine is called.
The result of this example might be something like:

```
A
B
A
A
B
A
A
B
A is closing
B is closing
```

Task is any instance of interface:

```java
interface Task extends Closeable {
    void run();

    void stop();

    @Override
    void close();
}

```

Task represents some logically united actions to be performed.
When run method is called task starts and moves towards completion.
When run method finishes task is known to be completed.
close method of task should be called after run method to cleanup after task and
to free any allocated resources.
stop method can be called before completion to interrupt current task actions.
