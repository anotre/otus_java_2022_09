package ru.otus.service;

import ru.otus.domain.Banknote;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.api.service.BanknoteCellHolder;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.exceptions.InsufficientBankotesAmount;

import java.util.*;

public class BanknoteProviderImpl implements BanknoteProvider {
    private final Set<BanknoteValue> banknoteValues;
    private BanknoteCellHolder banknoteCellHolder;
    public BanknoteProviderImpl(BanknoteCellHolder banknoteCellHolder) {
        this.banknoteCellHolder = banknoteCellHolder;
        this.banknoteValues = new TreeSet<>(banknoteCellHolder.getBanknoteValues());
    }
    @Override
    public List<Banknote> getBanknotesBySum(long requestedSum) throws InsufficientBankotesAmount {
        try {
            Map<BanknoteValue, Integer> transactionData = calculateTransaction(requestedSum);

            List<Banknote> banknotesToOutput = new ArrayList<>();
            for (Map.Entry<BanknoteValue, Integer> transactionDataElement : transactionData.entrySet()) {
                if (transactionDataElement.getValue() == 0) {
                    continue;
                }

                int requiredBanknoteAmount = transactionDataElement.getValue();
                List<Banknote> requiredBanknotesFromCell = banknoteCellHolder.getCell(transactionDataElement.getKey()).getBanknotes(requiredBanknoteAmount);
                banknotesToOutput.addAll(requiredBanknotesFromCell);
            }

            return banknotesToOutput;
        } catch (InsufficientBankotesAmount ex) {
            throw new InsufficientBankotesAmount(ex.getMessage());
        }
    }

    private Map<BanknoteValue, Integer> calculateTransaction(long requestedSum) throws InsufficientBankotesAmount {
        long bufferRequestedSum = requestedSum;
        Map<BanknoteValue, Integer> transactionData = new HashMap<>();
        Map<BanknoteValue, Integer> banknoteBalanceByCells = getBanknoteBalanceByCells();

        for (BanknoteValue banknoteValue : banknoteValues) {
            int currentBanknoteValue = banknoteValue.getValue();

            if (currentBanknoteValue > bufferRequestedSum) {
                transactionData.put(banknoteValue, 0);
                continue;
            }

            int banknoteAmount = (int) bufferRequestedSum / currentBanknoteValue;
            int banknoteBalance = banknoteBalanceByCells.get(banknoteValue);
            int resultBanknoteAmount = (banknoteBalance < banknoteAmount) ? banknoteBalance : banknoteAmount;

            transactionData.put(banknoteValue, resultBanknoteAmount);
            bufferRequestedSum -= ((long) currentBanknoteValue * resultBanknoteAmount);
        }

        if (bufferRequestedSum != 0) {
            throw new InsufficientBankotesAmount("Insufficient amount of banknotes");
        }

        return transactionData;
    }

    private Map<BanknoteValue, Integer> getBanknoteBalanceByCells() {
        Map<BanknoteValue, Integer> cellsBalance = new HashMap<>();

        for (BanknoteValue banknoteValue : banknoteValues) {
            cellsBalance.put(banknoteValue, banknoteCellHolder.getCell(banknoteValue).getBanknoteAmount());
        }

        return cellsBalance;
    }
}
