import java.util.*;

/* ---------- Domain layer ---------- */

class Customer {
    private final int id;
    private final String name;
    private final String phone;

    Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
    int getId()         { return id;    }
    String getName()    { return name;  }
    String getPhone()   { return phone; }

    @Override public String toString() {
        return "Customer#" + id + " (" + name + ")";
    }
}

/* Abstract **super-class** */
abstract class Account {
    private static int NEXT_NO = 1000;

    private final int accountNo = NEXT_NO++;
    private final Customer owner;
    protected double balance;      // protected → visible in subclasses

    Account(Customer owner, double openingBalance) {
        this.owner   = owner;
        this.balance = openingBalance;
    }

    /* Common behaviour */
    public void deposit(double amt) {
        if (amt <= 0) throw new IllegalArgumentException("Amount +ve only");
        balance += amt;
    }

    /** Subclasses may override to impose their own rules. */
    public abstract void withdraw(double amt) throws Exception;

    public int getAccountNo()   { return accountNo; }
    public double getBalance()  { return balance;   }
    public Customer getOwner()  { return owner;     }

    @Override public String toString() {
        return getClass().getSimpleName() + " #" + accountNo +
               " | Owner: " + owner.getName() +
               " | Balance: ₹" + String.format("%.2f", balance);
    }
}

/* ---------- Subclasses ---------- */

class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 500.0;
    private static final double INTEREST    = 0.04;  // 4 % p.a dummy rate

    SavingsAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    @Override
    public void withdraw(double amt) throws Exception {
        if (balance - amt < MIN_BALANCE)
            throw new Exception("Savings: must keep ₹" + MIN_BALANCE + " min balance");
        balance -= amt;
    }

    /** Simple yearly interest posting (not compounding daily). */
    public void addAnnualInterest() {
        balance += balance * INTEREST;
    }
}

class CurrentAccount extends Account {
    private static final double OD_LIMIT = 10000.0;   // overdraft limit

    CurrentAccount(Customer owner, double openingBalance) {
        super(owner, openingBalance);
    }

    @Override
    public void withdraw(double amt) throws Exception {
        if (balance - amt < -OD_LIMIT)
            throw new Exception("Current: overdraft limit ₹" + OD_LIMIT + " exceeded");
        balance -= amt;
    }
}

/* ---------- Bank aggregates many accounts ---------- */

class Bank {
    private final String name;
    private final List<Account> accounts = new ArrayList<>();

    Bank(String name) { this.name = name; }

    /* Factory helpers */
    public SavingsAccount openSavings(Customer c, double initAmt) {
        SavingsAccount a = new SavingsAccount(c, initAmt);
        accounts.add(a);
        return a;
    }
    public CurrentAccount openCurrent(Customer c, double initAmt) {
        CurrentAccount a = new CurrentAccount(c, initAmt);
        accounts.add(a);
        return a;
    }

    public Account find(int acctNo) {
        return accounts.stream()
                       .filter(a -> a.getAccountNo() == acctNo)
                       .findFirst()
                       .orElse(null);
    }

    public void transfer(int fromNo, int toNo, double amt) throws Exception {
        Account from = find(fromNo);
        Account to   = find(toNo);
        if (from == null || to == null) throw new Exception("Invalid account no.");
        from.withdraw(amt);   // may throw rule-violation
        to.deposit(amt);
    }

    public void printAll() { accounts.forEach(System.out::println); }

    @Override public String toString() { return name; }
}

/* ---------- Demo / test harness ---------- */
public class Task1Bankmanagment {
    public static void main(String[] args) {
        Bank hdfc = new Bank("HDFC");

        // create customers
        Customer alice = new Customer(1, "Alice", "9876543210");
        Customer bob   = new Customer(2, "Bob",   "9123456780");

        // open accounts
        SavingsAccount  aliceSav = hdfc.openSavings(alice, 2000.0);
        CurrentAccount  bobCur   = hdfc.openCurrent(bob,   5000.0);

        // basic ops
        try {
            aliceSav.withdraw(1200);        // leaves ₹800 (≥500 so OK)
            bobCur.withdraw(9000);          // balance = -₹4000 (within OD)
            hdfc.transfer(bobCur.getAccountNo(), aliceSav.getAccountNo(), 1500);
        } catch (Exception ex) {
            System.out.println("⚠️  " + ex.getMessage());
        }

        // interest at year-end
        aliceSav.addAnnualInterest();

        // summary
        System.out.println("---- Account Snapshot ----");
        hdfc.printAll();
    }
}