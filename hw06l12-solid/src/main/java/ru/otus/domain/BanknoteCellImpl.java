package ru.otus.domain;

import java.util.List;
import java.util.ArrayList;

import ru.otus.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.exceptions.InsufficientBankotesAmount;

public class BanknoteCellImpl implements BanknoteCell {
    private final BanknoteValue banknoteValue;

    private final List<Banknote> banknoteCell;

    public BanknoteCellImpl(BanknoteValue banknoteValue) {
        this.banknoteValue = banknoteValue;
        this.banknoteCell = new ArrayList<>();
    }
    @Override
    public int getBanknoteValue() {
        return banknoteValue.getValue();
    }

    @Override
    public int getBanknoteAmount() {
        return banknoteCell.size();
    }

    @Override
    public long getSumByCell() {
        return banknoteValue.getValue() * banknoteCell.size();
    }

    @Override
    public void addBanknotes(List<Banknote> banknotes) {
        banknoteCell.addAll(banknotes);

    }

    @Override
    public List<Banknote> getBanknotes(int requiredBanknoteAmount) throws InsufficientBankotesAmount {
        int banknoteAmount = banknoteCell.size();
        if (banknoteAmount < requiredBanknoteAmount) {
            throw new InsufficientBankotesAmount("Not enough banknotes in the cell with value: " + banknoteValue);
        }

        List<Banknote> banknotesToOutput = popBanknotes(banknoteAmount - requiredBanknoteAmount, banknoteAmount);

        return banknotesToOutput;
    }

    private List<Banknote> popBanknotes(int from, int to) {
        List<Banknote> removedBanknotes = new ArrayList<>();

        for (int i = from; i < to; i++) {
            removedBanknotes.add(banknoteCell.remove(i));
        }

        return removedBanknotes;
    }
}
