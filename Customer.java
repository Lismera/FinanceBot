import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer {
    private Map<String, Account> accounts;
    private List<Account> transactionHistory;

    public Customer() {
        this.accounts = new HashMap<>();
        this.transactionHistory = new ArrayList<>();
    }

    public Customer addAccount(Account account) {
        accounts.put(account.getAccountType(), account);
        return this;
    }

    public Customer updateTransactionHistory(Account account) {
        transactionHistory.add(account);
        return this;
    }

    public List<Account> getTransactionHistory(){
        return this.transactionHistory;
    }

    public Map<String, Account> getAccounts() {
        return this.accounts;
    }

}
