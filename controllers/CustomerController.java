package controllers;

import enums.AccountTypes;
import models.Account;
import models.Customer;

public class CustomerController {

    private Customer owner;
    private Account snapshot;

    public CustomerController(Customer owner) {
        this.owner = owner;
        this.snapshot = new Account(0,"SNAPSHOT");
    }

    public CustomerController handleOverdraft(Account accountOne, Account accountTwo) {
        double overDraftAmount = accountOne.getCurrentAmount();
        Account accountOneSystem = new Account(accountOne.getAccountID(), accountOne.getAccountType());
        Account accountTwoSystem = new Account(accountTwo.getAccountID(), accountTwo.getAccountType());
        accountOneSystem.setInitiatorType(AccountTypes.SYSTEM.getAccountType())
            .setTransactionValue(Math.abs(overDraftAmount))
            .setDateTime(accountOne.getDateTime())
            .updateCurrentAmount(accountOne.getCurrentAmount());
        accountTwoSystem.setInitiatorType(AccountTypes.SYSTEM.getAccountType())
            .setTransactionValue(overDraftAmount)
            .setDateTime(accountOne.getDateTime())
            .updateCurrentAmount(accountTwo.getCurrentAmount());
        accountOne.updateCurrentAmount(Math.abs(overDraftAmount));
        accountTwo.updateCurrentAmount(overDraftAmount);
        this.snapshot = accountOneSystem.createSnapshot();
        owner.updateTransactionHistory(snapshot);
        this.snapshot = accountTwoSystem.createSnapshot();
        owner.updateTransactionHistory(snapshot);
        return this;
    }

    public CustomerController createNewAccount(int accountID, String accountType) {
            Account newAccount = new Account(accountID, accountType);
            this.owner.addAccount(newAccount);
            return this;
    }

    public CustomerController updateCustomerTransactionHistory(String accountType, double transactionValue, String dateTime) {
        this.owner.getAccounts().get(accountType)
                    .setTransactionValue(transactionValue)
                    .setDateTime(dateTime)
                    .updateCurrentAmount(transactionValue);
        snapshot = this.owner.getAccounts().get(accountType).createSnapshot();
        this.owner.updateTransactionHistory(snapshot);
        return this;
    }

    public Customer getCustomer() {
        return this.owner;
    }
}
