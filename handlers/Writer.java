package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import models.Account;

public class Writer {

    private List<Account> dataLines;

    public Writer() {
        this.dataLines = new ArrayList<>();
    }

    public Writer setdataLines(Account account) {
        this.dataLines.add(account);
        return this;
    }

    private String convertToCSV(Account account) {
        StringBuilder stringOfAccount = new StringBuilder();
        stringOfAccount.append(account.getAccountID()).append(",")
        .append(account.getAccountType()).append(",")
        .append(account.getInitiatorType()).append(",")
        .append(account.getDateTime()).append(",")
        .append(account.getTransactionValue());
        return stringOfAccount.toString();
    }

    public void writeCsv(String outputFile) throws FileNotFoundException {
        File csvOutputFile = new File("./output/record" + outputFile);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("AccountID,AccountType,InitiatorType,DateTime,TransactionValue");
            dataLines.forEach(entry -> {
                pw.println(convertToCSV(entry));              
            });
        }
    }
}
