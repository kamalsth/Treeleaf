package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        BankingSystemImpl bankingSystem = new BankingSystemImpl();
        ATMImpl atm = new ATMImpl();
        Scanner scanner = new Scanner(System.in);
        String accountNumber;
        BigDecimal amount;
        while (true) {
            System.out.println("Please enter your account number: ");
            accountNumber = scanner.nextLine();
            try {
                bankingSystem.getAccountBalance(accountNumber);
                break;
            } catch (AccountNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        while (true) {
            System.out.println("Please enter the amount you wish to withdraw: ");
            amount = scanner.nextBigDecimal();
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                break;
            }
        }
        List<Banknote> withdrawnBanknotes = atm.withdraw(accountNumber, amount);
        System.out.println("Withdrawn banknotes: " + withdrawnBanknotes);


    }
}
