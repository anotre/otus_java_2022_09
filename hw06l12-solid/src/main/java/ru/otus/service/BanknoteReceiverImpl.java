package ru.otus.service;

import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.exceptions.WrongBanknoteValueException;

import java.util.*;

public class BanknoteReceiverImpl implements BanknoteReceiver {

    private final Map<Integer, BanknoteCell> banknoteCells;
    private final Set<Integer> banknoteValues;

    public BanknoteReceiverImpl(Map<Integer, BanknoteCell> banknoteCells) {
        this.banknoteCells = banknoteCells;
        this.banknoteValues = banknoteCells.keySet();
    }

    @Override
    public void addBanknotes(List<Banknote> banknoteList) throws WrongBanknoteValueException {
        int banknoteListSize = banknoteList.size();
        Map<Integer, List<Banknote>> banknoteBuffer = banknoteBufferInit();

        for (int i = 0; i < banknoteListSize; i++) {
            Banknote banknote = banknoteList.get(i);
            int banknoteValue = banknote.getBanknoteValue();

            if (!banknoteCells.containsKey(banknoteValue)) {
                throw new WrongBanknoteValueException("There is no exists " + banknoteValue + " banknote value.");
            }

            banknoteBuffer.get(banknoteValue).add(banknote);
        }

        addBanknotesByCells(banknoteBuffer);
    }

    private void addBanknotesByCells(Map<Integer, List<Banknote>> banknotesByValue) {
        for (int banknoteValue : banknoteValues) {
            banknoteCells.get(banknoteValue).addBanknotes(banknotesByValue.get(banknoteValue));
        }
    }

    private Map<Integer, List<Banknote>> banknoteBufferInit() {
        Map<Integer, List<Banknote>> banknoteBuffer = new HashMap<>();

        for (int key : banknoteValues) {
            banknoteBuffer.put(key, new ArrayList<>());
        }

        return banknoteBuffer;
    }
}
