package paydo.lv.fetcher.fetched;

public class Fraud {
    private String arn;
    private String destinationBIN;
    private String fraudAmount;
    private String sourceBIN;
    private String fraudCurrencyCode;
    private String acctNumberExtension;
    private String vicProcessingDate;
    private String purchaseDate;
    private String convenienceCheck;
    private String merchantName;
    private String fraudType;
    private String merchantCity;
    private String cardExpirationDate;
    private String merchantCountryCode;
    private String merchantPostalCode;
    private String date;

    public Fraud(String arn, String destinationBIN, String fraudAmount, String sourceBIN, String fraudCurrencyCode, String acctNumberExtension, String vicProcessingDate, String purchaseDate, String convenienceCheck, String merchantName, String fraudType, String merchantCity, String cardExpirationDate, String merchantCountryCode, String merchantPostalCode) {
        this.arn = arn;
        this.destinationBIN = destinationBIN;
        this.fraudAmount = fraudAmount;
        this.sourceBIN = sourceBIN;
        this.fraudCurrencyCode = fraudCurrencyCode;
        this.acctNumberExtension = acctNumberExtension;
        this.vicProcessingDate = vicProcessingDate;
        this.purchaseDate = purchaseDate;
        this.convenienceCheck = convenienceCheck;
        this.merchantName = merchantName;
        this.fraudType = fraudType;
        this.merchantCity = merchantCity;
        this.cardExpirationDate = cardExpirationDate;
        this.merchantCountryCode = merchantCountryCode;
        this.merchantPostalCode = merchantPostalCode;
    }

    public Fraud(){};

    public String getARN() {
        return arn;
    }

    public void setARN(String arn) {
        this.arn = arn;
    }

    public String getDestinationBIN() {
        return destinationBIN;
    }

    public void setDestinationBIN(String destinationBIN) {
        this.destinationBIN = destinationBIN;
    }

    public String getFraudAmount() {
        return fraudAmount;
    }

    public void setFraudAmount(String fraudAmount) {
        this.fraudAmount = fraudAmount;
    }

    public String getSourceBIN() {
        return sourceBIN;
    }

    public void setSourceBIN(String sourceBIN) {
        this.sourceBIN = sourceBIN;
    }

    public String getFraudCurrencyCode() {
        return fraudCurrencyCode;
    }

    public void setFraudCurrencyCode(String fraudCurrencyCode) {
        this.fraudCurrencyCode = fraudCurrencyCode;
    }

    public String getAcctNumberExtension() {
        return acctNumberExtension;
    }

    public void setAcctNumberExtension(String acctNumberExtension) {
        this.acctNumberExtension = acctNumberExtension;
    }

    public String getVicProcessingDate() {
        return vicProcessingDate;
    }

    public void setVicProcessingDate(String vicProcessingDate) {
        this.vicProcessingDate = vicProcessingDate;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getConvenienceCheck() {
        return convenienceCheck;
    }

    public void setConvenienceCheck(String convenienceCheck) {
        this.convenienceCheck = convenienceCheck;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getFraudType() {
        return fraudType;
    }

    public void setFraudType(String fraudType) {
        this.fraudType = fraudType;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public void setCardExpirationDate(String cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    public String getMerchantCountryCode() {
        return merchantCountryCode;
    }

    public void setMerchantCountryCode(String merchantCountryCode) {
        this.merchantCountryCode = merchantCountryCode;
    }

    public String getMerchantPostalCode() {
        return merchantPostalCode;
    }

    public void setMerchantPostalCode(String merchantPostalCode) {
        this.merchantPostalCode = merchantPostalCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
