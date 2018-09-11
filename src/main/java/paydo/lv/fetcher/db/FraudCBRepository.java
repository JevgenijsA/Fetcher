package paydo.lv.fetcher.db;

import paydo.lv.fetcher.fetched.CB;
import paydo.lv.fetcher.fetched.Fraud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FraudCBRepository {
    Connection conn;
    PreparedStatement st;

    public void insertFraud(Fraud entity) {
        String sql = "INSERT INTO fraud (ARN, DestinationBIN, FraudAmount, SourceBIN, FraudCurrencyCode, AcctNumberExtension," +
                "VICProcessingDate, PurchaseDate, ConvenienceCheck, MerchantName, FraudType, MerchantCity, " +
                "CardExpirationDate, MerchantCountryCode, MerchantPostalCode, Date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, entity.getARN());
            st.setString(2, entity.getDestinationBIN());
            st.setString(3, entity.getFraudAmount());
            st.setString(4, entity.getSourceBIN());
            st.setString(5, entity.getFraudCurrencyCode());
            st.setString(6, entity.getAcctNumberExtension());
            st.setString(7, entity.getVicProcessingDate());
            st.setString(8, entity.getPurchaseDate());
            st.setString(9, entity.getConvenienceCheck());
            st.setString(10, entity.getMerchantName());
            st.setString(11, entity.getFraudType());
            st.setString(12, entity.getMerchantCity());
            st.setString(13, entity.getCardExpirationDate());
            st.setString(14, entity.getMerchantCountryCode());
            st.setString(15, entity.getMerchantPostalCode());
            st.setString(16, entity.getDate());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Fraud> getAllFraud() {
        String sql = "SELECT ARN, DestinationBIN, FraudAmount, SourceBIN, FraudCurrencyCode, AcctNumberExtension," +
                "VICProcessingDate, PurchaseDate, ConvenienceCheck, MerchantName, FraudType, MerchantCity," +
                "CardExpirationDate, MerchantCountryCode, MerchantPostalCode, Date FROM fraud";
        ArrayList<Fraud> list = new ArrayList<Fraud>();
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                Fraud p = new Fraud();
                p.setARN(rs.getString(1));
                p.setDestinationBIN(rs.getString(2));
                p.setFraudAmount(rs.getString(3));
                p.setSourceBIN(rs.getString(4));
                p.setFraudCurrencyCode(rs.getString(5));
                p.setAcctNumberExtension(rs.getString(6));
                p.setVicProcessingDate(rs.getString(7));
                p.setPurchaseDate(rs.getString(8));
                p.setConvenienceCheck(rs.getString(9));
                p.setMerchantName(rs.getString(10));
                p.setFraudType(rs.getString(11));
                p.setMerchantCity(rs.getString(12));
                p.setCardExpirationDate(rs.getString(13));
                p.setMerchantCountryCode(rs.getString(14));
                p.setMerchantPostalCode(rs.getString(15));
                p.setDate(rs.getString(16));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public void insertCB(CB entity) {
        String sql = "INSERT INTO cb (MerchantName, MID, ARN, MerchantReferenceID, TransactionDate, " +
                "CardNumber, AuthCode, DisputeCurrency, DisputedAmount, ReasonCode, ReasonDescription, Date) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            st.setString(1, entity.getMerchantName());
            st.setString(2, entity.getMID());
            st.setString(3, entity.getARN());
            st.setString(4, entity.getMerchantReferenceID());
            st.setString(5, entity.getTransactionDate());
            st.setString(6, entity.getCardNumber());
            st.setString(7, entity.getAuthCode());
            st.setString(8, entity.getDisputeCurrency());
            st.setString(9, entity.getDisputedAmount());
            st.setString(10, entity.getReasonCode());
            st.setString(11, entity.getReasonDescription());
            st.setString(12, entity.getDate());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<CB> getAllCB() {
        String sql = "SELECT MerchantName, MID, ARN, MerchantReferenceID, TransactionDate, " +
                "CardNumber, AuthCode, DisputeCurrency, DisputedAmount, ReasonCode, ReasonDescription, Date FROM cb";
        ArrayList<CB> list = new ArrayList<CB>();
        try {
            conn = MyConnection.getConnection();
            st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                CB p = new CB();
                p.setMerchantName(rs.getString(1));
                p.setMID(rs.getString(2));
                p.setARN(rs.getString(3));
                p.setMerchantReferenceID(rs.getString(4));
                p.setTransactionDate(rs.getString(5));
                p.setCardNumber(rs.getString(6));
                p.setAuthCode(rs.getString(7));
                p.setDisputeCurrency(rs.getString(8));
                p.setDisputedAmount(rs.getString(9));
                p.setReasonCode(rs.getString(10));
                p.setReasonDescription(rs.getString(11));
                p.setDate(rs.getString(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
