package com.boaglio.poc.threads;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author Milan Karajovic <milan.karajovic.rs@gmail.com>
 *
 * https://dzone.com/articles/java-concurrency-evolution-virtual-threads-java21
 *
 *
 */

public class Example01CachedThreadPool {

    public String executeTasks(final int NUMBER_OF_TASKS) {

        System.out.println("=================================================================");
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        long totalMemoryInMB = totalMemory / (1024 * 1024);
        long freeMemoryInMB = freeMemory / (1024 * 1024);
        long usedMemoryInMB = usedMemory / (1024 * 1024);
        System.out.println("Total Memory (MB): " + totalMemoryInMB);
        System.out.println(" Free Memory (MB): " + freeMemoryInMB);
        System.out.println(" Used Memory (MB): " + usedMemoryInMB);
        System.out.println("=================================================================");

        final int BLOCKING_CALL = 1;

        final int dezPct = NUMBER_OF_TASKS / 10;
        AtomicInteger peace = new AtomicInteger(1);

        System.out.println("Number of tasks which executed using 'newCachedThreadPool()' " + NUMBER_OF_TASKS + " tasks each.");

        long startTime = System.currentTimeMillis();

        try (var executor = Executors.newCachedThreadPool()) {

            IntStream.range(0, NUMBER_OF_TASKS).forEach(i -> {
                executor.submit(() -> {
                    // simulate a blicking call (e.g. I/O or db operation)
                    Thread.sleep(Duration.ofSeconds(BLOCKING_CALL));
                    if (i == dezPct*peace.get()) {
                        System.out.println(">> "+peace.get() *10 + "%");
                        peace.getAndIncrement();
                    }
                    return i;
                });
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(">> 100%");

        long endTime = System.currentTimeMillis();

        var message = "For executing %06d tasks duration is: %06d ms".formatted(NUMBER_OF_TASKS,endTime - startTime);
        System.out.println(message);
        return message;
    }

    public static void main(String[] args) {
        var exe = new Example01CachedThreadPool();
        var t1 = exe.test_1000_tasks();
        var t2 = exe.test_10_000_tasks();
        var t3 = exe.test_100_000_tasks();
        System.out.println("Executors.newCachedThreadPool (Java 1.5)");
        System.out.println("  1_000 - "+t1);
        System.out.println(" 10_000 - "+t2);
        System.out.println("100_000 - "+t3);
    }

    public String test_1000_tasks() {
        Example01CachedThreadPool example01CachedThreadPool = new Example01CachedThreadPool();
        return example01CachedThreadPool.executeTasks(1000);
    }

    public String test_10_000_tasks() {
        Example01CachedThreadPool example01CachedThreadPool = new Example01CachedThreadPool();
        return example01CachedThreadPool.executeTasks(10_000);
    }

    public String test_100_000_tasks() {
        Example01CachedThreadPool example01CachedThreadPool = new Example01CachedThreadPool();
        return example01CachedThreadPool.executeTasks(100_000);
    }

    public String test_1_000_000_tasks() {
        Example01CachedThreadPool example01CachedThreadPool = new Example01CachedThreadPool();
        return example01CachedThreadPool.executeTasks(1_000_000);
    }

}