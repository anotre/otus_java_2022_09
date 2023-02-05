package ru.otus.processor;

import java.time.LocalDateTime;

public class DateTimeProviderStub implements DateTimeProvider {

    private final LocalDateTime localDateTime;

    public DateTimeProviderStub(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
    @Override
    public LocalDateTime getDate() {
        return localDateTime;
    }
}
