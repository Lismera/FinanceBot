import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class csvReader {
    public static void main(String[] args) throws Exception {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("customer-1234567-ledger.csv"))) {
            String line;
            String headerLine = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.replaceAll("\"", "").split(",");
                records.add(Arrays.asList(values));
            }
        
            //mapping the accounts/owner
            Customer Owner = new Customer();
            Account newAccount = null;
            Account snapshot = newAccount;
            for (List<String> record: records) {
                Integer accountID = Integer.parseInt(record.get(0));
                String accountType = record.get(1);
                String initiatortype = record.get(2);
                String datetime = record.get(3);
                double transactionValue = Double.parseDouble(record.get(4));
                if (!Owner.getAccounts().containsKey(accountType)) {
                    // Creating account if one does not exist
                    newAccount = new Account(accountID, accountType);
                    Owner.addAccount(newAccount);
                }
                if(AccountTypes.CURRENT.getAccountType().equals(accountType)){
                    Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).setTransactionValue(transactionValue)
                    .setDateTime(datetime)
                    .updateCurrentAmount(transactionValue);
                    snapshot = Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).createSnapshot();
                    Owner.updateTransactionHistory(snapshot);
                }
                else if(AccountTypes.SAVINGS.getAccountType().equals(accountType)) {
                    Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType())
                        .setTransactionValue(transactionValue)
                        .setDateTime(datetime)
                        .updateCurrentAmount(transactionValue);
                    snapshot = Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).createSnapshot();
                    Owner.updateTransactionHistory(snapshot);
                }
                if(Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getCurrentAmount() < 0 && Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getCurrentAmount() > 0 - Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getCurrentAmount()) {
                    double overDraftAmount = Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getCurrentAmount();
                    Account systemAccount = new Account(Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getAccountID(), Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getAccountType());
                    systemAccount.setInitiatorType(AccountTypes.SYSTEM.getAccountType()).setTransactionValue(Math.abs(overDraftAmount)).setDateTime(Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getDateTime());
                    Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).updateCurrentAmount(overDraftAmount);
                    Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).updateCurrentAmount(Math.abs(overDraftAmount));
                    snapshot = systemAccount.createSnapshot();
                    Owner.updateTransactionHistory(snapshot);
                }
            }
            for(Account history: Owner.getTransactionHistory()) {
                System.out.println("-------------------------------------------");
                System.out.println(history.getAccountID());
                System.out.println(history.getAccountType());
                System.out.println(history.getInitiatorType());
                System.out.println("Transaction:");
                System.out.println(history.getTransactionValue());
                System.out.println("BALANCE:");
                System.out.println(history.getCurrentAmount());
                System.out.println("TIME:");
                System.out.println(history.getDateTime());
            }
            System.out.println(Owner.getTransactionHistory().size()); 
        }
    }
}