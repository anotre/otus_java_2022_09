package ru.otus.domain;

import ru.otus.api.domain.Atm;
import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.exceptions.InsufficientBankotesAmount;
import ru.otus.exceptions.WrongBanknoteValueException;
import java.util.*;

public class AtmImpl implements Atm {
    private BanknoteReceiver banknoteReceiver;
    private BanknoteProvider banknoteProvider;
    private Map<Integer, BanknoteCell> banknoteCells;
    public AtmImpl(BanknoteReceiver banknoteReceiver, BanknoteProvider banknoteProvider, Map<Integer, BanknoteCell> banknoteCells) {
        this.banknoteReceiver = banknoteReceiver;
        this.banknoteProvider = banknoteProvider;
        this.banknoteCells = banknoteCells;
    }

    @Override
    public long getBalance() {
        long totalSumByCells = 0;

        for (BanknoteCell cell: banknoteCells.values()) {
            totalSumByCells += cell.getSumByCell();
        }

        return totalSumByCells;
    }

    @Override
    // переименовать параметр
    public boolean addMoney(List<Banknote> amountOfMoney) {
        boolean transactionResult = false;
        try {
            banknoteReceiver.addBanknotes(amountOfMoney);
            transactionResult = true;
        } catch (WrongBanknoteValueException ex) {
            System.out.println("Wrong banknote value");
            System.out.println("Operation has interrupted");
        }
        return transactionResult;
    }

    @Override
    public List<Banknote> getMoney(long requiredAmountOfMoney) {
        int minBanknoteValue = getMinBanknoteValue();
        List<Banknote> banknotesToOutput = new ArrayList<>();
        if (requiredAmountOfMoney % minBanknoteValue == 0) {
            try {
                banknotesToOutput = banknoteProvider.getBanknotesBySum(requiredAmountOfMoney);
            } catch (InsufficientBankotesAmount ex) {
                System.out.println("Insufficient amount of money");
            }
        } else {
            System.out.println("Incorrect required amount of money. It must be a multiple of " + minBanknoteValue);
            System.out.println("Operation has interrupted");
        }

        return banknotesToOutput;
    }

    private int getMinBanknoteValue() {
        TreeMap<Integer, BanknoteCell> banknoteCells = new TreeMap<Integer, BanknoteCell>(this.banknoteCells);
        return banknoteCells.firstKey();
    }
}
