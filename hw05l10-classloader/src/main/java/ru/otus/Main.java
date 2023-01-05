package ru.otus;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        TestLoggingInterface testLoggingProxied = Ioc.createTestLogging();
        for (int i = 0; i < 10000; i++) {
            testLoggingProxied.calculation(1);
            testLoggingProxied.calculation(2, 3);
            testLoggingProxied.calculation(4, 5, 6);
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }
}