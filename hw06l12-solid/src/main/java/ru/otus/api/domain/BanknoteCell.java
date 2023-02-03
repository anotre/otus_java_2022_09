package ru.otus.api.domain;

import ru.otus.domain.Banknote;
import ru.otus.exceptions.InsufficientBankotesAmount;
import java.util.List;

public interface BanknoteCell {
    public int getBanknoteValue();

    public int getBanknoteAmount();

    public long getSumByCell();

    public void addBanknotes(List<Banknote> banknotes);

    public List<Banknote> getBanknotes(int requiredBanknoteAmount) throws InsufficientBankotesAmount;
}
