import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
    private Integer accountID;
    private String accountType;
    private String initiatorType;
    private String dateTime;
    private Double transactionValue;
    private Double currentAmount;

    public Account(Integer accountID, String accountType) {
        this.accountID = accountID;
        this.accountType = accountType;
        this.transactionValue = 0.00;
        this.currentAmount = 0.00;
        this.initiatorType = "ACCOUNT-HOLDER";
    }

    public String getAccountType() {
        return this.accountType;
    }

    public Integer getAccountID() {
        return this.accountID;
    }

    public String getInitiatorType() {
        return this.initiatorType;
    }

    public Account setInitiatorType(String initiator) {
        initiatorType = initiator;
        return this;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public Account setDateTime(String systemTime) {
       dateTime=systemTime;
       return this;
    }

    public Double getTransactionValue() {
        return this.transactionValue;
    }

    public Account setTransactionValue(Double updateValue) {
        transactionValue = updateValue;
        return this;
    }

    public Account updateCurrentAmount(Double amount) {
        currentAmount = currentAmount + amount;
        BigDecimal bd = new BigDecimal(this.currentAmount).setScale(2, RoundingMode.HALF_UP);
        currentAmount = bd.doubleValue();
        return this;
    }

    public Double getCurrentAmount() {
        BigDecimal bd = new BigDecimal(this.currentAmount).setScale(2, RoundingMode.HALF_UP);
        double amount = bd.doubleValue();
        return amount;
    }

    public Account createSnapshot() {
        Account snapshot = new Account(this.accountID, this.accountType);
        snapshot.setInitiatorType(this.initiatorType);
        snapshot.setTransactionValue(this.transactionValue);
        snapshot.setDateTime(this.dateTime);
        snapshot.updateCurrentAmount(this.currentAmount);
        return snapshot;

    }

}