package ru.otus.service;

import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.api.service.BanknoteCellHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BanknoteCellHolderImpl implements BanknoteCellHolder {

    Map<BanknoteValue, BanknoteCell> banknoteCellHolder;

    public BanknoteCellHolderImpl(Map<BanknoteValue, BanknoteCell> banknoteCells) {
        banknoteCellHolder = new HashMap<>();
        banknoteCellHolder.putAll(banknoteCells);
    }

    @Override
    public Long getCellBalance(BanknoteValue banknoteValue) {
        return banknoteCellHolder.get(banknoteValue).getSumByCell();
    }

    @Override
    public Long getTotalBalance() {
        long totalBalance = 0;
        for (Map.Entry<BanknoteValue, BanknoteCell> cell : banknoteCellHolder.entrySet()) {
            totalBalance += cell.getValue().getSumByCell();
        }
        return totalBalance;
    }

    @Override
    public BanknoteCell getCell(BanknoteValue banknoteValue) {
        return banknoteCellHolder.get(banknoteValue);
    }

    @Override
    public Set<BanknoteValue> getBanknoteValues() {
        return banknoteCellHolder.keySet();
    };
}
