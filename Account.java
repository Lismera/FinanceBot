import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    private Integer AccountID;
    private String AccountType;
    private String InitiatorType;
    private String DateTime;
    private Double TransactionValue;
    private Double CurrentAmount;

    public Account(Integer AccountID, String AccountType) {
        this.AccountID = AccountID;
        this.AccountType = AccountType;
        this.TransactionValue = 0.00;
        this.CurrentAmount = 0.00;
        this.InitiatorType = "ACCOUNT-HOLDER";
    }

    public String getAccountType() {
        return this.AccountType;
    }

    public Integer getAccountID() {
        return this.AccountID;
    }

    public String getInitiatorType() {
        return this.InitiatorType;
    }

    public Account setInitiatorType(String Initiator) {
        InitiatorType = Initiator;
        return this;
    }

    public String getDateTime() {
        return this.DateTime;
    }

    public Account setDateTime(String SystemTime) {
       DateTime=SystemTime;
       return this;
    }

    public double getTransactionValue() {
        return this.TransactionValue;
    }

    public Account setTransactionValue(Double UpdateValue) {
        TransactionValue = UpdateValue;
        return this;
    }

    public Account updateCurrentAmount(Double Amount) {
        CurrentAmount = CurrentAmount + Amount;
        BigDecimal bd = new BigDecimal(this.CurrentAmount).setScale(2, RoundingMode.HALF_UP);
        CurrentAmount = bd.doubleValue();
        return this;
    }

    public Double getCurrentAmount() {
        BigDecimal bd = new BigDecimal(this.CurrentAmount).setScale(2, RoundingMode.HALF_UP);
        double amount = bd.doubleValue();
        return amount;
    }

    public Account createSnapshot() {
        Account snapshot = new Account(this.AccountID, this.AccountType);
        snapshot.setInitiatorType(this.InitiatorType);
        snapshot.setTransactionValue(this.TransactionValue);
        snapshot.setDateTime(this.DateTime);
        snapshot.updateCurrentAmount(this.CurrentAmount);
        return snapshot;

    }

}