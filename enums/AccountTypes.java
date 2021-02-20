package enums;

public enum AccountTypes {
    CURRENT("CURRENT"),
    SAVINGS("SAVINGS"),
    SYSTEM("SYSTEM");

    private String value;

    public String getAccountType() {
        return value;
    }

    AccountTypes(String value){
        this.value = value;
    }
}
