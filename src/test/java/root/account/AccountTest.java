package root.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    @Test
    void getMaxCredit() {
        Account account = new Account();
        account.block();
        account.setMaxCredit(100);
        assertEquals(account.getMaxCredit(), 100);
        boolean check = account.setMaxCredit(-300);
        assertFalse(check);
    }

    @Test
    void depositUnblocked() {
        Account account = new Account();

        account.deposit(50);
        assertEquals(50, account.getBalance());

        account.deposit(70);
        assertEquals(120, account.getBalance());
    }

    @Test
    void depositBlocked() {
        Account account = new Account();
        account.block();

        boolean result = account.deposit(50);
        assertFalse(result);
    }

    @Test
    void withdrawUnblockedExceedMaxCredit() {
        Account account = new Account();

        boolean withdrawResult = account.withdraw(2000);

        assertFalse(withdrawResult);
    }

    @Test
    void withdrawUnblocked() {
        Account account = new Account();
        int previous = account.getBalance();

        boolean withdrawResult = account.withdraw(200);
        int current = account.getBalance();

        assertTrue(withdrawResult);
        assertEquals(previous - 200, current);

    }

    @Test
    void withdrawBlocked() {
        Account account = new Account();
        int previous = account.getBalance();

        account.block();
        boolean withdrawResult = account.withdraw(200);
        int current = account.getBalance();

        assertFalse(withdrawResult);
        assertEquals(previous, current);

    }

    @Test
    void changeMaxCreditUnblocked() {
        Account account = new Account();
        int previous = account.getMaxCredit();
        boolean answer = account.setMaxCredit(3000);
        int current = account.getMaxCredit();
        assertFalse(answer);
        assertEquals(previous, current);
    }

    @Test
    void changeMaxCreditBlocked() {
        Account account = new Account();
        account.block();
        boolean check = account.setMaxCredit(100);
        assertTrue(check);
        assertEquals(account.getMaxCredit(), 100);
    }

    @Test
    void changeMaxCreditInvalidArgUnblocked() {
        Account account = new Account();
        int previous = account.getMaxCredit();

        boolean answer = account.setMaxCredit(-100);

        int current = account.getMaxCredit();

        assertFalse(answer);
        assertEquals(previous, current);

        boolean secondAnswer = account.setMaxCredit(-account.getBound() - 10);
        assertFalse(secondAnswer);
        assertEquals(previous, account.getMaxCredit());

        boolean thirdAnswer = account.setMaxCredit(account.getBound() + 10);
        assertFalse(thirdAnswer);
        assertEquals(previous, account.getMaxCredit());
    }

    @Test
    void changeMaxCreditInvalidArgBlocked() {
        Account account = new Account();
        account.block();

        int previous = account.getMaxCredit();

        boolean answer = account.setMaxCredit(-100);

        int current = account.getMaxCredit();

        assertFalse(answer);
        assertEquals(previous, current);

        boolean secondAnswer = account.setMaxCredit(-account.getBound() - 10);
        assertFalse(secondAnswer);
        assertEquals(previous, account.getMaxCredit());

        boolean thirdAnswer = account.setMaxCredit(account.getBound() + 10);
        assertFalse(thirdAnswer);
        assertEquals(previous, account.getMaxCredit());
    }

    @Test
    void unblock() {
        Account account = new Account();
        account.withdraw(300);
        account.block();
        account.setMaxCredit(100);
        boolean checkUnblock = account.unblock();
        assertFalse(checkUnblock);
        assertTrue(account.isBlocked());
        account.setMaxCredit(300);
        checkUnblock = account.unblock();
        assertTrue(checkUnblock);
        assertFalse(account.isBlocked());
    }

    @Test
    void depositNegative() {
        Account account = new Account();

        boolean depositResult = account.deposit(-500);
        assertFalse(depositResult);

        assertEquals(0, account.getBalance());
    }

    @Test
    void depositZero() {
        Account account = new Account();
        boolean check = account.deposit(0);
        assertTrue(check);
    }

    @Test
    void withdrawNegative() {
        Account account = new Account();

        boolean withdrawResult = account.withdraw(-500);
        assertFalse(withdrawResult);

        assertEquals(0, account.getBalance());
    }

    @Test
    void depositArgExceedBound() {
        Account account = new Account();

        boolean depositResult = account.deposit(account.getBound() + 1);
        assertFalse(depositResult);

        assertEquals(0, account.getBalance());
    }

    @Test
    void withdrawArgExceedBound() {
        Account account = new Account();

        boolean withdrawResult = account.withdraw(account.getBound() + 1);
        assertFalse(withdrawResult);

        assertEquals(0, account.getBalance());
    }

    @Test
    void depositResultExceedBound() {
        Account account = new Account();

        boolean firstDepositResult = account.deposit(account.getBound() / 2);
        assertTrue(firstDepositResult);

        assertEquals(account.getBound() / 2, account.getBalance());

        boolean secondDepositResult = account.deposit((account.getBound() / 2) + 10);
        assertFalse(secondDepositResult);

        assertEquals(account.getBound() / 2, account.getBalance());
    }

    @Test
    void withdrawResultExceedBound() {
        Account account = new Account();
        int previous = account.getBalance();
        boolean check = account.deposit(1000001);
        int current = account.getBalance();
        assertFalse(check);
        assertEquals(previous, current);
        check = account.withdraw(1000001);
        assertFalse(check);
    }
}