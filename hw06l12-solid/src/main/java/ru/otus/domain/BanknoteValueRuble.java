package ru.otus.domain;

import ru.otus.api.domain.BanknoteValue;

public enum BanknoteValueRuble implements BanknoteValue {
    TEN(10),
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000)
    ;
    private final int banknoteValue;
    private BanknoteValueRuble(int banknoteValue) {
        this.banknoteValue = banknoteValue;
    }

    public int getValue() {
        return banknoteValue;
    }
}
