package org.example.bank;

public class Account {

    private String accountNum;
    private String name;
    private String pwd;
    private int balance;

    public Account(String accountNum, String name, String pwd, int balance) {
        this.accountNum = accountNum;
        this.name = name;
        this.pwd = pwd;
        this.balance = balance;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }
    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    public void withdraw(int amount) {
        if (amount < balance) {
            this.balance -= amount;

            System.out.println("출금이 완료되었습니다.");
            System.out.printf("잔액은 %s원 입니다.", this.balance);
        } else {
            System.out.println("잔액이 부족합니다.");
        }
    }

    @Override
    public String toString() {
        return "이름 : " + name +
              ", 계좌번호 : " + accountNum +
                ", 잔고 : " + balance +
                "\n";
    }
}
