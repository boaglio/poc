package com.boaglio.poc.threads;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 *
 * @author Milan Karajovic <milan.karajovic.rs@gmail.com>
 *
 * java -Xms1g -Xss512k -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xlog:os+thread=trace,async=false -XX:+UnlockDiagnosticVMOptions -XX:+TraceThreadEvents -XX:+PrintNMT -XX:+LogVMOutput -XX:LogFile=jvm.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=heapdump.hprof Example02FixedThreadPool.java
 */

public class Example02FixedThreadPool {

    public String executeTasks(final int NUMBER_OF_TASKS) {

        final int BLOCKING_CALL = 1;
        System.out.println("Number of tasks which executed using 'newFixedThreadPool(500)' " + NUMBER_OF_TASKS + " tasks each.");

        final int dezPct = NUMBER_OF_TASKS / 10;
        final int onePct = NUMBER_OF_TASKS / 100;
        AtomicInteger peace = new AtomicInteger(1);

        long startTime = System.currentTimeMillis();

        try (var executor = Executors.newFixedThreadPool(500)) {

            IntStream.range(0, NUMBER_OF_TASKS).forEach(i -> {
               executor.submit(() -> {

                Thread.sleep(Duration.ofSeconds(BLOCKING_CALL));
                if (i % onePct == 0) {
                    System.out.print("#");
                }

                if (i == dezPct*peace.get()) {
                    System.out.println();
                    System.out.println(">> "+peace.get() *10 + "%");
                    peace.getAndIncrement();
                }
                  return i;
               });
            });

        }   catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(">> 100%");

        long endTime = System.currentTimeMillis();

        var message = "For executing %06d tasks duration is: %06d ms".formatted(NUMBER_OF_TASKS,endTime - startTime);
        System.out.println(message);
        return message;
    }


    public static void main(String[] args) {
        var exe = new Example02FixedThreadPool();
        var t1 = exe.test_1000_tasks();
        var t2 = exe.test_10_000_tasks();
        var t3 = exe.test_100_000_tasks();
        System.out.println("Executors.newFixedThreadPool (Java 1.5)");
        System.out.println("  1_000 - "+t1);
        System.out.println(" 10_000 - "+t2);
        System.out.println("100_000 - "+t3);
    }

    public String test_1000_tasks() {
        Example02FixedThreadPool example02FixedThreadPool = new Example02FixedThreadPool();
        return example02FixedThreadPool.executeTasks(1000);
    }

    public String test_10_000_tasks() {
        Example02FixedThreadPool example02FixedThreadPool = new Example02FixedThreadPool();
        return example02FixedThreadPool.executeTasks(10_000);
    }

    public String test_100_000_tasks() {
        Example02FixedThreadPool example02FixedThreadPool = new Example02FixedThreadPool();
        return example02FixedThreadPool.executeTasks(100_000);
    }

    public String test_1_000_000_tasks() {
        Example02FixedThreadPool example02FixedThreadPool = new Example02FixedThreadPool();
        return example02FixedThreadPool.executeTasks(1_000_000);
    }

}