package paydo.lv.fetcher.fetched;

public class CB {
    private String MerchantName;
    private String MID;
    private String ARN;
    private String MerchantReferenceID;
    private String TransactionDate;
    private String CardNumber;
    private String AuthCode;
    private String disputeCurrency;
    private String DisputedAmount;
    private String ReasonCode;
    private String ReasonDescription;
    private String date;

    public CB() {
    }

    public CB(String merchantName, String MID, String ARN, String merchantReferenceID, String transactionDate, String cardNumber, String authCode, String disputeCurrency, String disputedAmount, String reasonCode, String reasonDescription, String date) {
        MerchantName = merchantName;
        this.MID = MID;
        this.ARN = ARN;
        MerchantReferenceID = merchantReferenceID;
        TransactionDate = transactionDate;
        CardNumber = cardNumber;
        AuthCode = authCode;
        this.disputeCurrency = disputeCurrency;
        DisputedAmount = disputedAmount;
        ReasonCode = reasonCode;
        ReasonDescription = reasonDescription;
        this.date = date;
    }

    public String getMerchantName() {
        return MerchantName;
    }

    public void setMerchantName(String merchantName) {
        MerchantName = merchantName;
    }

    public String getMID() {
        return MID;
    }

    public void setMID(String MID) {
        this.MID = MID;
    }

    public String getARN() {
        return ARN;
    }

    public void setARN(String ARN) {
        this.ARN = ARN;
    }

    public String getMerchantReferenceID() {
        return MerchantReferenceID;
    }

    public void setMerchantReferenceID(String merchantReferenceID) {
        MerchantReferenceID = merchantReferenceID;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getAuthCode() {
        return AuthCode;
    }

    public void setAuthCode(String authCode) {
        AuthCode = authCode;
    }

    public String getDisputedAmount() {
        return DisputedAmount;
    }

    public void setDisputedAmount(String disputedAmount) {
        DisputedAmount = disputedAmount;
    }

    public String getReasonCode() {
        return ReasonCode;
    }

    public void setReasonCode(String reasonCode) {
        ReasonCode = reasonCode;
    }

    public String getReasonDescription() {
        return ReasonDescription;
    }

    public void setReasonDescription(String reasonDescription) {
        ReasonDescription = reasonDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDisputeCurrency() {
        return disputeCurrency;
    }

    public void setDisputeCurrency(String disputeCurrency) {
        this.disputeCurrency = disputeCurrency;
    }
}
