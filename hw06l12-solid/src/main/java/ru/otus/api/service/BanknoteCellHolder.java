package ru.otus.api.service;

import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.domain.BanknoteValue;

import java.util.List;
import java.util.Set;

public interface BanknoteCellHolder {
    Long getCellBalance(BanknoteValue banknoteValue);
    Long getTotalBalance();
    BanknoteCell getCell(BanknoteValue banknoteValue);

    Set<BanknoteValue> getBanknoteValues();
}
