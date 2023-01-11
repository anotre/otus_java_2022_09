package ru.otus.domain;

import ru.otus.api.domain.Atm;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.api.service.BanknoteCellHolder;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.exceptions.InsufficientBankotesAmount;
import ru.otus.exceptions.WrongBanknoteValueException;
import java.util.*;

public class AtmImpl implements Atm {
    private final BanknoteReceiver banknoteReceiver;
    private final BanknoteProvider banknoteProvider;
    private final Set<BanknoteValue> banknoteValues;
    private final BanknoteCellHolder banknoteCellHolder;
    public AtmImpl(BanknoteReceiver banknoteReceiver, BanknoteProvider banknoteProvider, BanknoteCellHolder banknoteCellHolder) {
        this.banknoteReceiver = banknoteReceiver;
        this.banknoteProvider = banknoteProvider;
        this.banknoteCellHolder = banknoteCellHolder;
        this.banknoteValues = banknoteCellHolder.getBanknoteValues();
    }

    @Override
    public long getBalance() {
        long totalSumByCells = 0;

        for (BanknoteValue banknoteValue: banknoteValues) {
            totalSumByCells += banknoteCellHolder.getCellBalance(banknoteValue);
        }

        return totalSumByCells;
    }

    @Override
    public boolean addMoney(List<Banknote> amountOfMoney) {
        boolean transactionResult = false;
        try {
            banknoteReceiver.addBanknotes(amountOfMoney);
            transactionResult = true;
        } catch (WrongBanknoteValueException ex) {
            System.out.println("Banknote verification error occurred during balance top up.");
            System.out.println(ex.getMessage());
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
        TreeSet<BanknoteValue> banknoteValues = new TreeSet<>(Comparator.comparingInt(banknoteValue -> banknoteValue.getValue()));
        //TreeMap<BanknoteValue, BanknoteCell> banknoteCells = new TreeMap<BanknoteValue, BanknoteCell>(Comparator.comparingInt(banknoteValue -> banknoteValue.getValue()));
        banknoteValues.addAll(this.banknoteValues);
        return banknoteValues.first().getValue();
    }
}
