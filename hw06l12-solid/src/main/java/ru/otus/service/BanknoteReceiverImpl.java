package ru.otus.service;

import ru.otus.domain.Banknote;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.api.service.BanknoteCellHolder;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.exceptions.WrongBanknoteValueException;

import java.util.*;

public class BanknoteReceiverImpl implements BanknoteReceiver {

    BanknoteCellHolder banknoteCellHolder;
    private final Set<BanknoteValue> banknoteValues;

    public BanknoteReceiverImpl(BanknoteCellHolder banknoteCellHolder) {
        this.banknoteCellHolder = banknoteCellHolder;
        this.banknoteValues = banknoteCellHolder.getBanknoteValues();
    }

    @Override
    public void addBanknotes(List<Banknote> banknoteList) throws WrongBanknoteValueException {
        int banknoteListSize = banknoteList.size();
        Map<BanknoteValue, List<Banknote>> banknoteBuffer = banknoteBufferInit();

        for (int i = 0; i < banknoteListSize; i++) {
            Banknote banknote = banknoteList.get(i);
            BanknoteValue banknoteValue = banknote.getBanknoteValue();

            if (!banknoteValues.contains(banknoteValue)) {
                throw new WrongBanknoteValueException("There is no exists " + banknoteValue + " banknote value.");
            }

            banknoteBuffer.get(banknoteValue).add(banknote);
        }

        addBanknotesByCells(banknoteBuffer);
    }

    private void addBanknotesByCells(Map<BanknoteValue, List<Banknote>> banknotesByValue) {
        for (BanknoteValue banknoteValue : banknoteValues) {
            banknoteCellHolder.getCell(banknoteValue).addBanknotes(banknotesByValue.get(banknoteValue));
        }
    }

    private Map<BanknoteValue, List<Banknote>> banknoteBufferInit() {
        Map<BanknoteValue, List<Banknote>> banknoteBuffer = new HashMap<>();

        for (BanknoteValue key : banknoteValues) {
            banknoteBuffer.put(key, new ArrayList<>());
        }

        return banknoteBuffer;
    }
}
