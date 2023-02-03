package ru.otus.api.domain;

public interface Banknote {
    int getBanknoteValue();
    static Banknote createBanknote(BanknoteValue banknoteValue) {
        return null;
    };
}
