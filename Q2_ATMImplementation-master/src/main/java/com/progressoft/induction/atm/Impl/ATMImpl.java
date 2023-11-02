package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.ATM;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ATMImpl implements ATM {
    private final BankingSystemImpl bankingSystem=new BankingSystemImpl();
    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BigDecimal accountBalance=bankingSystem.getAccountBalance(accountNumber);
        if(accountBalance.compareTo(amount)<0){
            throw new InsufficientFundsException("Insufficient funds in accountNumber: "+accountNumber);
        }
        if(bankingSystem.sumOfMoneyInAtm().compareTo(amount)<0){
            throw new NotEnoughMoneyInATMException("Not enough money in ATM");
        }


        List<Banknote> banknotesToDispense = new ArrayList<>();

        // Reorder Banknote values in descending order (highest to lowest denomination)
        List<Banknote> orderedBanknotes = Arrays.stream(Banknote.values())
                .sorted((bn1, bn2) -> bn2.getValue().compareTo(bn1.getValue()))
                .collect(Collectors.toList());

        for (Banknote banknote : orderedBanknotes) {
            BigDecimal banknoteValue = banknote.getValue();

            while (amount.compareTo(banknoteValue) >= 0) {
                if (bankingSystem.atmCashMap.get(banknote) > 0) {
                    banknotesToDispense.add(banknote);
                    amount = amount.subtract(banknoteValue);
                    bankingSystem.atmCashMap.put(banknote, bankingSystem.atmCashMap.get(banknote) - 1);
                }
            }
        }

        if (amount.compareTo(BigDecimal.ZERO) != 0) {
            throw new NotEnoughMoneyInATMException("Not enough money in the ATM");
        }

        bankingSystem.debitAccount(accountNumber, amount);
        return banknotesToDispense;


    }


    @Override
    public BigDecimal checkBalance(String accountNumber) {
        return bankingSystem.getAccountBalance(accountNumber);
    }
}
