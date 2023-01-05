package ru.otus.api.domain;

import java.util.List;

public interface Atm {
    public long getBalance();
    public boolean addMoney(List<Banknote> amountOfMoney);
    public List<Banknote> getMoney(long requiredAmountOfMoney);
}
