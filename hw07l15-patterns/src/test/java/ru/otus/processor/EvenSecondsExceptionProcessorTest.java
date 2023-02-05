package ru.otus.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EvenSecondsExceptionProcessorTest {
    private Processor processorEven;
    private Processor processorOdd;

    @BeforeEach
    void setUp() {
        DateTimeProvider stubEvenSecond = new DateTimeProviderStub(LocalDateTime.now().withSecond(1));
        DateTimeProvider stubOddSecond = new DateTimeProviderStub(LocalDateTime.now().withSecond(2));
        processorEven = new EvenSecondsExceptionProcessor(stubEvenSecond);
        processorOdd = new EvenSecondsExceptionProcessor(stubOddSecond);
    }

    @Test
    void throwExceptionProcessTest() {
        assertThrows(RuntimeException.class, () -> processorEven.process(null));
        processorOdd.process(null);
    }
}