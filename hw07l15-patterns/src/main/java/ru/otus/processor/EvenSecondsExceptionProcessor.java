package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class EvenSecondsExceptionProcessor implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public EvenSecondsExceptionProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 != 0) {
            throw new RuntimeException();
        }
        return message;
    }
}
