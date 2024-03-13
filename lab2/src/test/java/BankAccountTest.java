import org.example.domain.BankAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    @Test
    public void testCreateAccount() {
        BankAccount account = new BankAccount("John Doe", 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testWithdraw(BankAccount account) {
        account.withdraw(50.0);
        assertEquals(50.0, account.getBalance());
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testDeposit(BankAccount account) {
        account.deposit(50.0);
        assertEquals(150.0, account.getBalance());
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testWithdrawInsufficientFunds(BankAccount account) {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(150.0));
    }

    @Test
    public void testInvalidInitialAmount() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("John Doe", -100.0));
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testInvalidWithdrawAmount(BankAccount account) {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-50.0));
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testInvalidDepositAmount(BankAccount account) {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-50.0));
    }

    @Test
    public void testNegativeInitialBalance() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("John Doe", -100.0));
    }

    @MethodSource("arguments")
    @ParameterizedTest
    public void testLargeNumberOfTransactions(BankAccount account) {
        for (int i = 0; i < 1000; i++) {
            account.withdraw(1.0);
            account.deposit(1.0);
        }
        assertEquals(100.0, account.getBalance());
    }

    @Test
    public void testEmptyAccount() {
        BankAccount account = new BankAccount("John Doe", 0.0);
        assertEquals(0.0, account.getBalance());

        account.withdraw(0.0);
        assertEquals(0.0, account.getBalance());

        account.deposit(0.0);
        assertEquals(0.0, account.getBalance());
    }

    static Arguments[] arguments() {
        return new Arguments[] {
                Arguments.of(
                        new BankAccount("John Doe", 100.0)
                ),
        };
    }
}
