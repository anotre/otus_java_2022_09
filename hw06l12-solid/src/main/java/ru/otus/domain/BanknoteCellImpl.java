package ru.otus.domain;

import java.util.List;
import java.util.ArrayList;
import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.exceptions.InsufficientBankotesAmount;

public class BanknoteCellImpl implements BanknoteCell {
    int banknoteValue;

    int banknoteAmount;

    List<Banknote> banknoteCell;

    public BanknoteCellImpl(int banknoteValue) {
        this.banknoteValue = banknoteValue;
        this.banknoteCell = new ArrayList<>();
        this.banknoteAmount = 0;
    }
    @Override
    public int getBanknoteValue() {
        return banknoteValue;
    }

    @Override
    public int getBanknoteAmount() {
        return banknoteAmount;
    }

    @Override
    public long getSumByCell() {
        return banknoteValue * banknoteAmount;
    }

    @Override
    public void addBanknotes(List<Banknote> banknotes) {
        banknoteCell.addAll(banknotes);
        updateBanknoteAmount();
    }

    @Override
    public List<Banknote> getBanknotes(int requiredBanknoteAmount) throws InsufficientBankotesAmount {
        if (banknoteAmount < requiredBanknoteAmount) {
            throw new InsufficientBankotesAmount("Not enough banknotes in the cell with value: " + banknoteValue);
        }

        List<Banknote> banknotesToOutput = popBanknotes(banknoteAmount - requiredBanknoteAmount, banknoteAmount);
        updateBanknoteAmount();

        return banknotesToOutput;
    }

    private void updateBanknoteAmount() {
        banknoteAmount = banknoteCell.size();
    }

    private List<Banknote> popBanknotes(int from, int to) {
        List<Banknote> removedBanknotes = new ArrayList<>();

        for (int i = from; i < to; i++) {
            removedBanknotes.add(banknoteCell.remove(i));
        }

        return removedBanknotes;
    }
}
