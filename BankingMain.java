import java.util.List;

import Models.Account;
import Models.Customer;
import controllers.CustomerController;
import enums.AccountTypes;
import handlers.Reader;
import handlers.Writer;

public class BankingMain {
    public static void main(String[] args) throws Exception {
        
            Customer owner = new Customer();
            CustomerController customerController = new CustomerController(owner);
            Writer records = new Writer();
            Reader reader = new Reader();

            for (List<String> record: reader.read(args[0])) {
                Integer accountID = Integer.parseInt(record.get(0));
                String accountType = record.get(1);
                String dateTime = record.get(3);
                double transactionValue = Double.parseDouble(record.get(4));

                if (!customerController.getCustomer().getAccounts().containsKey(accountType)) {
                    customerController.createNewAccount(accountID, accountType);
                }

                customerController.updateCustomerTransactionHistory(accountType, transactionValue, dateTime);

                if(customerController.getCustomer().getAccounts().get(AccountTypes.CURRENT.getAccountType()).getCurrentAmount() < 0 && customerController.getCustomer().getAccounts().get(AccountTypes.SAVINGS.getAccountType()).getCurrentAmount() > 0 - customerController.getCustomer().getAccounts().get(AccountTypes.CURRENT.getAccountType()).getCurrentAmount()) {
                    Account accountOne = customerController.getCustomer().getAccounts().get(AccountTypes.CURRENT.getAccountType());
                    Account accountTwo = customerController.getCustomer().getAccounts().get(AccountTypes.SAVINGS.getAccountType());
                    customerController.handleOverdraft(accountOne, accountTwo);
                }
            }

            for (Account history: customerController.getCustomer().getTransactionHistory()) {
            records.setdataLines(history);
            records.writeCsv(args[1]);
            }
        }
    }