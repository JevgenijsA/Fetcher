package paydo.lv.fetcher.fetched;

import paydo.lv.fetcher.process.ReadEmail;

import java.util.ArrayList;
import java.util.Arrays;

public class TextConvertor {

    public void generateFraud(String text, String date, String subject) {
        Fraud fraud = new Fraud();
        //if we have normal text
        if (text.length() != 0 || text != null) {

            String[] splitText = text.split(" ");
            ArrayList<String> arr = new ArrayList<>(Arrays.asList(splitText));
            String del = null;
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains("http:")) {
                    del = arr.get(i);
                }
            }
            arr.remove(del);

            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains("Destination")) {
                    fraud.setARN((subject.split(" "))[3]);
                    if (arr.get(i + 2).equals("Fraud")) arr.add(i + 2, "none");
                    else fraud.setDestinationBIN(arr.get(i + 2));
                    if (arr.get(i + 5).equals("BIN")) arr.add(i + 5, "none");
                    else fraud.setFraudAmount((arr.get(i + 5).split(System.lineSeparator())[0]));
                    if (arr.get(i + 7).equals("Fraud")) arr.add(i + 7, "none");
                    else fraud.setSourceBIN(arr.get(i + 7));
                    if (arr.get(i + 11).equals("Number")) arr.add(i + 11, "none");
                    else fraud.setFraudCurrencyCode((arr.get(i + 11).split(System.lineSeparator())[0]));
                    if (arr.get(i + 15).equals("VIC")) arr.add(i + 15, "none");
                    else fraud.setAcctNumberExtension(arr.get(i + 15));
                    if (arr.get(i + 19).equals("Date")) arr.add(i + 19, "none");
                    else fraud.setVicProcessingDate((arr.get(i + 19).split(System.lineSeparator())[0]));
                    if (arr.get(i + 21).equals("Convenience")) arr.add(i + 21, "none");
                    else fraud.setPurchaseDate(arr.get(i + 21));
                    if (arr.get(i + 24).equals("Name")) arr.add(i + 24, "none");
                    else fraud.setConvenienceCheck((arr.get(i + 24).split(System.lineSeparator())[0]));
                    if (arr.get(i + 26).equals("Fraud")) arr.add(i + 26, "none");
                    else fraud.setMerchantName(arr.get(i + 26));
                    if (arr.get(i + 29).equals("City")) arr.add(i + 29, "none");
                    else fraud.setFraudType((arr.get(i + 29).split(System.lineSeparator())[0]));
                    if (arr.get(i + 31).equals("Card")) arr.add(i + 31, "none");
                    else fraud.setMerchantCity(arr.get(i + 31));
                    if (arr.get(i + 35).equals("Country")) arr.add(i + 35, "none");
                    else fraud.setCardExpirationDate((arr.get(i + 35).split(System.lineSeparator())[0]));
                    if (arr.get(i + 38).equals("Merchant")) arr.add(i + 38, "none");
                    else fraud.setMerchantCountryCode(arr.get(i + 38));
                    if (arr.get(i + 42).equals("Amounts")) arr.add(i + 42, "none");
                    else fraud.setMerchantPostalCode((arr.get(i + 42).split(System.lineSeparator())[0]));
                    fraud.setDate(date);
                }
            }

            ReadEmail.fr.insertFraud(fraud);
        } else return;
    }

    public void generateCB(String text, String date) {
        if (text.length() != 0 || text != null) {
            CB cb = new CB();
            String[] splitText = (text.split(" "));
            ArrayList<String> arrL = new ArrayList<>();
            int index = new ArrayList<String>(Arrays.asList(splitText)).indexOf("Name:");

            while (!splitText[index].contains(System.lineSeparator() + System.lineSeparator())) {
                arrL.add(splitText[index]);
                index++;
            }
            arrL.add(splitText[index]);
            for (int i = 0; i < arrL.size(); i++) {
                if (arrL.get(i).contains("<http:")) {
                    arrL.set(i, arrL.get(i).substring(arrL.get(i).indexOf(">") + 1, arrL.get(i).length() - 1));
                }
            }
            cb.setMerchantName(stringTillEnter("Name", arrL));
            cb.setMID(stringTillEnter("MID", arrL));
            cb.setARN(stringTillEnter("ARN", arrL));
            cb.setMerchantReferenceID(stringTillEnter("Reference", arrL, 1));
            cb.setTransactionDate(stringTillEnter("Date:", arrL));
            cb.setCardNumber(stringTillEnter("Number:", arrL));
            cb.setAuthCode(stringTillEnter("Code:", arrL));
            cb.setDisputeCurrency((stringTillEnter("Amount:", arrL)).split(" ")[0]);
            cb.setDisputedAmount((stringTillEnter("Amount:", arrL)).split(" ")[1]);
            cb.setReasonCode(stringTillEnter("Reason", arrL, 1));
            cb.setReasonDescription(stringTillEnter("Description:", arrL));
            cb.setDate(date);

            ReadEmail.fr.insertCB(cb);
        } else return;

    }

    public String stringTillEnter(String name, ArrayList<String> arr) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while(!arr.get(index).contains(name)){
            if (index == arr.size() - 1) return null;
            index++;
        }
        index++;
        while(!arr.get(index).contains(System.lineSeparator())){
            sb.append(arr.get(index)).append(" ");
            index++;
        }
        sb.append(arr.get(index).split(System.lineSeparator())[0]);
        return sb.toString();
    }

    public String stringTillEnter(String name, ArrayList<String> arr, int slide) {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while(!arr.get(index).contains(name)){
            if (index == arr.size() - 1) return null;
            index++;
        }
        index = index + 1 + slide;
        while(!arr.get(index).contains(System.lineSeparator())){
            sb.append(arr.get(index)).append(" ");
            index++;
        }
        sb.append(arr.get(index).split(System.lineSeparator())[0]);
        return sb.toString();
    }
}
