package ru.otus.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.api.domain.Atm;
import ru.otus.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.service.BanknoteCellHolder;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.service.BanknoteCellHolderImpl;
import ru.otus.service.BanknoteProviderImpl;
import ru.otus.service.BanknoteReceiverImpl;
import ru.otus.api.domain.BanknoteValue;

import java.util.*;

public class AtmImplTest {

    Map<BanknoteValue, BanknoteCell> banknoteCells;
    BanknoteProvider banknoteProvider;
    BanknoteReceiver banknoteReceiver;
    BanknoteCellHolder banknoteCellHolder;
    Atm atm;

    @BeforeEach
    public void beforeEach() {
        banknoteCells = new HashMap<>();
        for (BanknoteValue banknoteValue : BanknoteValueRuble.values()) {
            banknoteCells.put(banknoteValue, new BanknoteCellImpl(banknoteValue));
        }
        banknoteCellHolder = new BanknoteCellHolderImpl(banknoteCells);
        banknoteProvider = new BanknoteProviderImpl(banknoteCellHolder);
        banknoteReceiver = new BanknoteReceiverImpl(banknoteCellHolder);
        atm = new AtmImpl(banknoteReceiver, banknoteProvider, banknoteCellHolder);

    }

    @Test
    @DisplayName("Balance check")
    public void getBalance() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.FIVE_THOUSAND)));
        assertEquals(5000, atm.getBalance());
    }

    @Test
    @DisplayName("Add money method check")
    public void addMoney() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.FIVE_THOUSAND)));
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.TEN)));
        assertEquals(5010, atm.getBalance());
        assertEquals(1, banknoteCells.get(BanknoteValueRuble.TEN).getBanknoteAmount());
        assertEquals(1, banknoteCells.get(BanknoteValueRuble.FIVE_THOUSAND).getBanknoteAmount());
    }



    @Test
    @DisplayName("Get money method check.")
    public void getMoney() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.FIVE_THOUSAND)));
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.TEN)));
        assertEquals(5010, atm.getBalance());
        List<Banknote> outputtedBanknotes = atm.getMoney(10);
        assertEquals(5000, atm.getBalance());
        long outputtedAmountOfMoney = 0;

        for (Banknote banknote : outputtedBanknotes) {
            outputtedAmountOfMoney += banknote.getBanknoteValue().getValue();
        }
        assertEquals(10, outputtedAmountOfMoney);
    }


    @Test
    @DisplayName("Get money method check. Request too much money")
    public void getTooMuchMoney() {
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.FIVE_THOUSAND)));
        assertEquals(5000, atm.getBalance());
        List<Banknote> banknotesList = atm.getMoney(6000);
        assertEquals(0, banknotesList.size());
    }

    @Test
    @DisplayName("Get money method check. Request incorrect amount of money")
    public void getMoneyIncorrect() {
        atm.addMoney(Arrays.asList(new Banknote(BanknoteValueRuble.TEN)));
        assertEquals(10, atm.getBalance());
        List<Banknote> banknotesList = atm.getMoney(8);
        assertEquals(0, banknotesList.size());
    }

}