package paydo.lv.fetcher.excel;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import paydo.lv.fetcher.MainAppController;
import paydo.lv.fetcher.fetched.Fraud;
import org.apache.poi.ss.usermodel.CellStyle;

import java.io.*;
import java.util.ArrayList;

public class FraudExcel {
    private static String[] columns = {"ARN", "Destination BIN", "Fraud amount", "Source BIN", "Fraud currency code",
        "Acct number extension", "VIC Processing Date", "Purchase date", "Convenience check", "Merchant name", "Fraud type", "Merchant city",
        "Card expiration date", "Merchant country code", "Merchant postal code", "Date from email"};
    private static final String FILE_PATH = "src/main/java/paydo/lv/fetcher/assets/fraud.xlsx";

    public boolean isFileFraudExist() {
        File f = new File(FILE_PATH);
        if(f.exists() && !f.isDirectory()) return true;
        return false;
    }

    public void writeFraud(ArrayList<Fraud> fraudArr) {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        Sheet sheet = workbook.createSheet("fraud");

        Row headerRow = sheet.createRow(0);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        for (Fraud fr : fraudArr) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(fr.getARN());
            row.createCell(1).setCellValue(fr.getDestinationBIN());
            row.createCell(2).setCellValue(fr.getFraudAmount());
            row.createCell(3).setCellValue(fr.getSourceBIN());
            row.createCell(4).setCellValue(fr.getFraudCurrencyCode());
            row.createCell(5).setCellValue(fr.getAcctNumberExtension());
            row.createCell(6).setCellValue(fr.getVicProcessingDate());
            row.createCell(7).setCellValue(fr.getPurchaseDate());
            row.createCell(8).setCellValue(fr.getConvenienceCheck());
            row.createCell(9).setCellValue(fr.getMerchantName());
            row.createCell(10).setCellValue(fr.getFraudType());
            row.createCell(11).setCellValue(fr.getMerchantCity());
            row.createCell(12).setCellValue(fr.getCardExpirationDate());
            row.createCell(13).setCellValue(fr.getMerchantCountryCode());
            row.createCell(14).setCellValue(fr.getMerchantPostalCode());
            row.createCell(15).setCellValue(fr.getDate());
        }

        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(FILE_PATH);
            workbook.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            MainAppController.addToLog("file not found or used by any app", "r");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
                MainAppController.addToLog("finish writing fraud.xlsx", "g");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateFraud(ArrayList<Fraud> fraudArr) {
        ArrayList<Fraud> fraudArrNotAdded = new ArrayList<Fraud>();
        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(new FileInputStream(FILE_PATH));
        } catch (EmptyFileException e) {
            writeFraud(fraudArr);
            return;
        } catch (IOException e) {
            MainAppController.addToLog("There is no such file", "r");
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            MainAppController.addToLog("Wrong file format", "r");
            e.printStackTrace();
        }

        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        int fraudArrSize = fraudArr.size();
        int equalsCount;
        for (int i = 0; i < fraudArrSize; i++) {
            equalsCount = 0;
            for (int j = 1; j <= rowCount; j++) {
                Row row = sheet.getRow(j);
                //check if there is some equalness
                if (row == null || row.getCell(0) == null);
                else if (fraudArr.get(i).getARN().equals(row.getCell(0).toString()) ) equalsCount++;
            }
            if ( equalsCount == 0 ) fraudArrNotAdded.add(fraudArr.get(i));
        }
        //add new row if find something new
        if ( fraudArrNotAdded.size() > 0 ) {
            rowCount++;
            for (Fraud fr : fraudArrNotAdded) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(0).setCellValue(fr.getARN());
                row.createCell(1).setCellValue(fr.getDestinationBIN());
                row.createCell(2).setCellValue(fr.getFraudAmount());
                row.createCell(3).setCellValue(fr.getSourceBIN());
                row.createCell(4).setCellValue(fr.getFraudCurrencyCode());
                row.createCell(5).setCellValue(fr.getAcctNumberExtension());
                row.createCell(6).setCellValue(fr.getVicProcessingDate());
                row.createCell(7).setCellValue(fr.getPurchaseDate());
                row.createCell(8).setCellValue(fr.getConvenienceCheck());
                row.createCell(9).setCellValue(fr.getMerchantName());
                row.createCell(10).setCellValue(fr.getFraudType());
                row.createCell(11).setCellValue(fr.getMerchantCity());
                row.createCell(12).setCellValue(fr.getCardExpirationDate());
                row.createCell(13).setCellValue(fr.getMerchantCountryCode());
                row.createCell(14).setCellValue(fr.getMerchantPostalCode());
                row.createCell(15).setCellValue(fr.getDate());
            }

            try {
                FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
                workbook.write(fileOut);

                fileOut.close();
                workbook.close();
                MainAppController.addToLog("finish updating fraud.xlsx", "g");
            } catch (FileNotFoundException e) {
                MainAppController.addToLog("There is no such file", "r");
                e.printStackTrace();
            } catch (IOException e) {
                MainAppController.addToLog("Problem to write update data in excel", "r");
                e.printStackTrace();
            }
        }
    }
}
