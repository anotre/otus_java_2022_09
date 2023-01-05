package ru.otus.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.api.domain.Atm;
import ru.otus.api.domain.Banknote;
import ru.otus.api.domain.BanknoteCell;
import ru.otus.api.service.BanknoteProvider;
import ru.otus.api.service.BanknoteReceiver;
import ru.otus.exceptions.InsufficientBankotesAmount;
import ru.otus.service.BanknoteProviderImpl;
import ru.otus.service.BanknoteReceiverImpl;
import ru.otus.api.domain.BanknoteValue;
import ru.otus.domain.BanknoteValueRuble;
import java.util.*;

public class AtmImplTest {

    Map<Integer, BanknoteCell> banknoteCells;
    BanknoteProvider banknoteProvider;
    BanknoteReceiver banknoteReceiver;
    Atm atm;

    @BeforeEach
    public void beforeEach() {
        banknoteCells = new HashMap<>();
        banknoteProvider = new BanknoteProviderImpl(banknoteCells);
        banknoteReceiver = new BanknoteReceiverImpl(banknoteCells);
        atm = new AtmImpl(banknoteReceiver, banknoteProvider, banknoteCells);
        for (BanknoteValueRuble banknoteValue : BanknoteValueRuble.values()) {
            int rawBanknoteValue = banknoteValue.getValue();
            banknoteCells.put(rawBanknoteValue, new BanknoteCellImpl(rawBanknoteValue));
        }

    }

    @Test
    @DisplayName("Balance check")
    public void getBalance() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.FIVE_THOUSAND)));
        assertEquals(5000, atm.getBalance());
    }

    @Test
    @DisplayName("Add money method check")
    public void addMoney() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.FIVE_THOUSAND)));
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.TEN)));
        assertEquals(5010, atm.getBalance());
        assertEquals(1, banknoteCells.get(BanknoteValueRuble.TEN.getValue()).getBanknoteAmount());
        assertEquals(1, banknoteCells.get(BanknoteValueRuble.FIVE_THOUSAND.getValue()).getBanknoteAmount());
    }


    @Test
    @DisplayName("Get money method check.")
    public void getMoney() {
        assertEquals(0, atm.getBalance());
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.FIVE_THOUSAND)));
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.TEN)));
        assertEquals(5010, atm.getBalance());
        List<Banknote> outputtedBanknotes = atm.getMoney(10);
        assertEquals(5000, atm.getBalance());
        long outputtedAmountOfMoney = 0;

        for (Banknote banknote : outputtedBanknotes) {
            outputtedAmountOfMoney += banknote.getBanknoteValue();
        }
        assertEquals(10, outputtedAmountOfMoney);
    }

    @Test
    @DisplayName("Get money method check. Request too much money")
    public void getTooMuchMoney() {
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.FIVE_THOUSAND)));
        assertEquals(5000, atm.getBalance());
        List<Banknote> banknotesList = atm.getMoney(6000);
        assertEquals(0, banknotesList.size());
    }

    @Test
    @DisplayName("Get money method check. Request incorrect amount of money")
    public void getMoneyIncorrect() {
        atm.addMoney(Arrays.asList(BanknoteImpl.createBanknote(BanknoteValueRuble.TEN)));
        assertEquals(10, atm.getBalance());
        List<Banknote> banknotesList = atm.getMoney(8);
        assertEquals(0, banknotesList.size());
    }

}