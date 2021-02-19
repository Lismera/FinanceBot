import java.util.List;
public class csvReader {
    public static void main(String[] args) throws Exception {
        
            Customer Owner = new Customer();
            Account newAccount = null;
            Account snapshot = newAccount;
            for (List<String> record: Reader.read()) {
                Integer accountID = Integer.parseInt(record.get(0));
                String accountType = record.get(1);
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
                    Account systemAccount = new Account(Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getAccountID(), Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getAccountType());
                    Account systemAccountSaving = new Account(Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getAccountID(), Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getAccountType());
                    systemAccount.setInitiatorType(AccountTypes.SYSTEM.getAccountType()).setTransactionValue(Math.abs(overDraftAmount)).setDateTime(Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getDateTime());
                    systemAccountSaving.setInitiatorType(AccountTypes.SYSTEM.getAccountType()).setTransactionValue(overDraftAmount).setDateTime(Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).getDateTime());
                    Owner.getAccounts().get(AccountTypes.SAVINGS.getAccountType()).updateCurrentAmount(overDraftAmount);
                    Owner.getAccounts().get(AccountTypes.CURRENT.getAccountType()).updateCurrentAmount(Math.abs(overDraftAmount));
                    snapshot = systemAccount.createSnapshot();
                    Owner.updateTransactionHistory(snapshot);
                    snapshot = systemAccountSaving.createSnapshot();
                    Owner.updateTransactionHistory(snapshot);
                }
            }

            Writer records = new Writer();
            for (Account history: Owner.getTransactionHistory()) {
            records.setdataLines(history);
            records.writeCsv();
            }
        }
    }