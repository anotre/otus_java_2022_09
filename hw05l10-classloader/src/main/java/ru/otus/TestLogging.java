package ru.otus;

import ru.otus.annotations.Log;

public class TestLogging implements TestLoggingInterface {
    @Override
    public void calculation(int arg) {
        System.out.println(String.format("Expected result: executed method: calculation, param: %d", arg));
    }
    @Log
    @Override
    public void calculation(int argA, int argB) {
        System.out.println(String.format("Expected result: executed method: calculation, param: %d, %d", argA, argB));
    }

    @Log
    @Override
    public void calculation(int argA, int argB, int argC) {
        System.out.println(String.format("Expected result: executed method: calculation, param: %d, %d, %d", argA, argB, argC));
    }
}
