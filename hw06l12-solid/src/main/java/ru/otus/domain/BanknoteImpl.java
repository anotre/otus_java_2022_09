package ru.otus.domain;

import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteValue;

public class BanknoteImpl implements Banknote {
    int banknoteValue;

    private BanknoteImpl(BanknoteValue banknoteValue) {
        this.banknoteValue = banknoteValue.getValue();
    }

    @Override
    public int getBanknoteValue() {
        return banknoteValue;
    }

    public static Banknote createBanknote(BanknoteValue banknoteValue) {
        return new BanknoteImpl(banknoteValue);
    }
}
