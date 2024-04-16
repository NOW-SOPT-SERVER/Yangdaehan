package org.example.bank;

import java.util.Scanner;

public class Withdraw {
    static void withdraw(Scanner scanner) {
        System.out.println("출금 계좌 번호를 입력하세요. ");
        String accountNum = scanner.next();

        Account account = findAccountByAccountNumber(accountNum);

        if (account == null) {
            System.out.println("계좌번호가 존재하지 않습니다.");
            return;
        }

        System.out.println("출금하려는 계좌의 비밀번호를 입력하세요.");
        String pwd = scanner.next();

        if (!account.getPwd().equals(pwd)) {
            System.out.println("비밀번호가 올바르지 않습니다.");
            return;
        }

        System.out.println("출금하려는 금액을 입력하세요.");
        int amount = Integer.parseInt(scanner.next());

        account.withdraw(amount);
    }
}
