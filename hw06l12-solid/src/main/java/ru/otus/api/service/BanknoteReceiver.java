package ru.otus.api.service;

import java.util.List;
import ru.otus.api.domain.Banknote;
import ru.otus.exceptions.WrongBanknoteValueException;

public interface BanknoteReceiver {
    public void addBanknotes(List<Banknote> banknoteList) throws WrongBanknoteValueException;
}
