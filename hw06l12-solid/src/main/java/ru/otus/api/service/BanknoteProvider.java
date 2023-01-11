package ru.otus.api.service;

import java.util.List;

import ru.otus.domain.Banknote;

import ru.otus.exceptions.InsufficientBankotesAmount;

public interface BanknoteProvider {
    public List<Banknote> getBanknotesBySum(long requestedSum) throws InsufficientBankotesAmount;
}
