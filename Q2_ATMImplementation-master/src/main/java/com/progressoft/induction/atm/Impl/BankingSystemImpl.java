package com.progressoft.induction.atm.Impl;

import com.progressoft.induction.atm.BankingSystem;
import com.progressoft.induction.atm.Banknote;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BankingSystemImpl implements BankingSystem {
   Map<String, BigDecimal> accountBalanceMap = new HashMap<String, BigDecimal>();
   EnumMap<Banknote,Integer> atmCashMap = new EnumMap<>(Banknote.class);

    public BankingSystemImpl() {
        atmCashMap.put(Banknote.FIFTY_JOD,10);
        atmCashMap.put(Banknote.TWENTY_JOD,20);
        atmCashMap.put(Banknote.TEN_JOD,100);
        atmCashMap.put(Banknote.FIVE_JOD,100);

        accountBalanceMap.put("123456789", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("111111111", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("222222222", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("333333333", BigDecimal.valueOf(1000.0));
        accountBalanceMap.put("444444444", BigDecimal.valueOf(1000.0));
    }

    public BigDecimal sumOfMoneyInAtm(){
        BigDecimal sum=BigDecimal.ZERO;
        for(Banknote banknote:Banknote.values()){
            sum = sum.add((banknote.getValue()).multiply(BigDecimal.valueOf(atmCashMap.get(banknote))));
        }
        return sum;
    }


    @Override
    public BigDecimal getAccountBalance(String accountNumber){
        BigDecimal balance= accountBalanceMap.get(accountNumber);
        if(balance==null){
            throw new AccountNotFoundException("Account not found of accountNumber: "+accountNumber);
        }
        return balance;
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        BigDecimal balance=getAccountBalance(accountNumber);
        if(balance.compareTo(amount)<0){
            throw new InsufficientFundsException("Insufficient funds in accountNumber: "+accountNumber);
        }
        accountBalanceMap.put(accountNumber,balance.subtract(amount));
    }
}
