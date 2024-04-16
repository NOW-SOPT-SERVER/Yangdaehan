package org.example.bank;

public enum BankInfo {

    SHINHAN(12);

    final int bankAccountLength;

    BankInfo(int length) {
        bankAccountLength = length;
    }

    public int accountLength() {
        return bankAccountLength;
    }
}
