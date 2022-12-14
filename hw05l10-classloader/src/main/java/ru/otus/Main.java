package ru.otus;

public class Main {

    public static void main(String[] args) {
        TestLoggingInterface testLoggingProxied = Ioc.createTestLogging();
        testLoggingProxied.calculation(1);
        testLoggingProxied.calculation(2, 3);
        testLoggingProxied.calculation(4, 5, 6);
    }
}