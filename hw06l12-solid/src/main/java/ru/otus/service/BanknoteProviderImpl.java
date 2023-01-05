package ru.otus.service;

import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.exceptions.InsufficientBankotesAmount;

import java.util.*;

public class BanknoteProviderImpl implements BanknoteProvider {
    Set<Integer> banknoteValues;
    Map<Integer, BanknoteCell> banknoteCells;
    public BanknoteProviderImpl(Map<Integer, BanknoteCell> banknoteCells) {
        this.banknoteCells = banknoteCells;
        this.banknoteValues = new TreeSet<>(banknoteCells.keySet());
    }
    @Override
    public List<Banknote> getBanknotesBySum(long requestedSum) throws InsufficientBankotesAmount {
        try {
            Map<Integer, Integer> transactionData = calculateTransaction(requestedSum);

            List<Banknote> banknotesToOutput = new ArrayList<>();
            for (Map.Entry<Integer, BanknoteCell> cell : banknoteCells.entrySet()) {
                int requiredBanknotesAmount = transactionData.get(cell.getKey());
                List<Banknote> requiredBanknotesFromCell = cell.getValue().getBanknotes(requiredBanknotesAmount);
                banknotesToOutput.addAll(requiredBanknotesFromCell);
            }
            return banknotesToOutput;
        } catch (InsufficientBankotesAmount ex) {
            throw new InsufficientBankotesAmount(ex.getMessage());
        }
    }

    private Map<Integer, Integer> calculateTransaction(long requestedSum) throws InsufficientBankotesAmount {
        long bufferRequestedSum = requestedSum;
        Map<Integer, Integer> transactionData = new HashMap<>();
        Map<Integer, Integer> banknoteBalanceByCells = getBanknoteBalanceByCells();

        for (Map.Entry<Integer, BanknoteCell> cell : banknoteCells.entrySet()) {
            int banknoteValue = cell.getKey();

            if (banknoteValue > bufferRequestedSum) {
                transactionData.put(banknoteValue, 0);
                continue;
            }

            int banknoteAmount = (int) bufferRequestedSum / banknoteValue;
            int banknoteBalance = banknoteBalanceByCells.get(banknoteValue);
            int resultBanknoteAmount = (banknoteBalance < banknoteAmount) ? banknoteBalance : banknoteAmount;

            transactionData.put(banknoteValue, resultBanknoteAmount);
            bufferRequestedSum -= ((long) banknoteValue * resultBanknoteAmount);
        }

        if (bufferRequestedSum != 0) {
            throw new InsufficientBankotesAmount("Insufficient amount of banknotes");
        }

        return transactionData;
    }

    private Map<Integer, Integer> getBanknoteBalanceByCells() {
        Map<Integer, Integer> cellsBalance = new HashMap<>();

        for (Map.Entry<Integer, BanknoteCell> cell : banknoteCells.entrySet()) {
            cellsBalance.put(cell.getKey(), cell.getValue().getBanknoteAmount());
        }

        return cellsBalance;
    }
}
