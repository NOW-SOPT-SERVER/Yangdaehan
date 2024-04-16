package org.example.bank;

import static org.example.Main.ACCOUNT;
import static org.example.Main.index;

public class FindAccount(String accountNumber) {
    public static Account findAccountByAccountNumber(String accountNumber) {
        for (int i = 0; i < index; i++) {
            if (ACCOUNT[i].getAccountNum().equals(accountNumber)) {
                return ACCOUNT[i];
            }
        }
        return null;
    }
}
