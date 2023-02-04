package ru.otus.domain;

import ru.otus.api.domain.BanknoteValue;

public class Banknote {
    private final BanknoteValue banknoteValue;

    public Banknote(BanknoteValue banknoteValue) {
        this.banknoteValue = banknoteValue;
    }

    public BanknoteValue getBanknoteValue() {
        return banknoteValue;
    }
}
