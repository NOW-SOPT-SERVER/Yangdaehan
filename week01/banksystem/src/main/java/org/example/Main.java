package org.example;

import org.example.bank.Account;
import org.example.bank.BankInfo;
import org.example.bank.RandomUtils;

import java.util.Scanner;

public class Main {

    private static final Account[] ACCOUNT = new Account[100];
    private static int index = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            init();
            int menu = sc.nextInt();

            if (menu == 5) {
                break;
            }
            run(menu, sc);
        }
    }

    private static void init() {
        System.out.println("==== 신한 은행 ====");
        System.out.println("1. 계좌개설 ");
        System.out.println("2. 입금");
        System.out.println("3. 출금");
        System.out.println("4. 계좌 잔액 조회");
        System.out.println("5. 종료");
        System.out.println("항목을 입력하세요. ");
    }

    private static void run(int menu, Scanner scanner) {
        if (menu == 1) {
            createAccount(scanner);
        }

        if (menu == 2) {
            deposit(scanner);
        }

        if (menu == 3) {
            withdraw(scanner);
        }

        if (menu == 4) {
            searchAccountNum(scanner);
        }
    }

    /*private static void withdraw(Scanner scanner) {
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
    }*/

    private static void deposit(Scanner scanner) {
        System.out.println("입금하려는 계좌 번호를 입력하세요. ");
        String accountNum = scanner.next();

        Account account = findAccountByAccountNumber(accountNum);

        if (account == null) {
            System.out.println("계좌번호가 존재하지 않습니다.");
            return;
        }

        System.out.println("입금하려는 금액을 입력하세요.");
        int amount = scanner.nextInt();
        account.deposit(amount);
        System.out.println("입금이 완료되었습니다.");
    }

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


    private static void searchAccountNum(Scanner scanner) {
        System.out.println("조회하려는 계좌 번호를 입력하세요. ");
        String accountNum = scanner.next();

        Account account = findAccountByAccountNumber(accountNum);

        if (account == null) {
            System.out.println("계좌번호가 존재하지 않습니다.");
            return;

        }
        System.out.println("조회하려는 계좌의 비밀번호를 입력하세요.");
        String pwd = scanner.next();

        if (!account.getPwd().equals(pwd)) {
            System.out.println("비밀번호가 올바르지 않습니다.");
            return;
        }

        System.out.println(account);
    }


    private static void createAccount(Scanner scanner) {
        System.out.println("이름 : ");
        String name = scanner.nextLine();

        System.out.println("비밀번호 : ");
        String pwd = scanner.nextLine();

        BankInfo ShinHan = BankInfo.SHINHAN;
        String AccountNum = RandomUtils.generateAccountNum(ShinHan.accountLength());

        Account account = new Account(AccountNum, name, pwd, 0);
        Main.ACCOUNT[index++] = account;

        System.out.println(account);
    }
}
