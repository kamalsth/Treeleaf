package com.progressoft.induction.atm;

import com.progressoft.induction.atm.Impl.ATMImpl;
import com.progressoft.induction.atm.Impl.BankingSystemImpl;
import com.progressoft.induction.atm.exceptions.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        BankingSystemImpl bankingSystem = new BankingSystemImpl();
        ATMImpl atm = new ATMImpl();
        Scanner scanner = new Scanner(System.in);
        String accountNumber;
        BigDecimal amount;

        while (true) {
            System.out.println("Welcome to the ATM. Please choose an option:");
            System.out.println("1. Withdraw Money");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    while (true) {
                        System.out.println("Please enter your account number: ");
                        accountNumber = scanner.next();
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

                    System.out.println("Withdrawn banknotes: " + atm.withdraw(accountNumber, amount));
                    break;

                case 2:
                    System.out.println("Exiting the ATM. Goodbye!");
                    exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
