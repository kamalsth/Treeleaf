package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ATMImpl implements ATM {
    private final BankingSystemImpl bankingSystem = new BankingSystemImpl();

    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance = bankingSystem.getAccountBalance(accountNumber);

        if (accountBalance.compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds in accountNumber: " + accountNumber);
        }

        if (bankingSystem.sumOfMoneyInAtm().compareTo(amount) < 0) {
            throw new NotEnoughMoneyInATMException("Not enough money in ATM");
        }

        List<Banknote> banknotesToDispense = new ArrayList<>();
        BigDecimal requestAmount = amount;
        BigDecimal minBanknoteInAtm = Arrays.stream(Banknote.values())
                .map(Banknote::getValue)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        while (amount.compareTo(BigDecimal.ZERO) != 0 && amount.compareTo(minBanknoteInAtm) >= 0) {
            for (Banknote banknote : Banknote.values()) {
                if (amount.compareTo(banknote.getValue()) >= 0 && bankingSystem.atmCashMap.get(banknote) > 0) {
                    banknotesToDispense.add(banknote);
                    amount = amount.subtract(banknote.getValue());
                    bankingSystem.atmCashMap.put(banknote, bankingSystem.atmCashMap.get(banknote) - 1);
                }
            }
        }

        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            throw new NotEnoughMoneyInATMException("No available banknotes for this amount in the ATM");
        }

        bankingSystem.debitAccount(accountNumber, requestAmount);
        //Arranging the banknotes in descending order
        banknotesToDispense.sort(Collections.reverseOrder());
        return banknotesToDispense;
    }


    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}


