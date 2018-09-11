package paydo.lv.fetcher.excel;

import org.apache.poi.EmptyFileException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import paydo.lv.fetcher.MainAppController;
import paydo.lv.fetcher.fetched.CB;
import paydo.lv.fetcher.fetched.Fraud;

import java.io.*;
import java.util.ArrayList;

public class CBExcel {
    private static String[] columns = {"Merchant name", "MID", "ARN", "Merchant reference ID", "Transaction date",
            "Card number", "Auth code", "Dispute currency", "Disputed amount", "Reason code", "Reason description",
            "Date from email"};
    private static final String FILE_PATH = "src/main/java/paydo/lv/fetcher/assets/cb.xlsx";

    public boolean isFileCBExist() {
        File f = new File(FILE_PATH);
        if(f.exists() && !f.isDirectory()) return true;
        return false;
    }

    public void writeCB(ArrayList<CB> cbArr) {
        Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
        Sheet sheet = workbook.createSheet("cb");

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
        for (CB cb : cbArr) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(cb.getMerchantName());
            row.createCell(1).setCellValue(cb.getMID());
            row.createCell(2).setCellValue(cb.getARN());
            row.createCell(3).setCellValue(cb.getMerchantReferenceID());
            row.createCell(4).setCellValue(cb.getTransactionDate());
            row.createCell(5).setCellValue(cb.getCardNumber());
            row.createCell(6).setCellValue(cb.getAuthCode());
            row.createCell(7).setCellValue(cb.getDisputeCurrency());
            row.createCell(8).setCellValue(cb.getDisputedAmount());
            row.createCell(9).setCellValue(cb.getReasonCode());
            row.createCell(10).setCellValue(cb.getReasonDescription());
            row.createCell(11).setCellValue(cb.getDate());
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
                MainAppController.addToLog("finish writing cb.xlsx", "g");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateCB(ArrayList<CB> cbArr) {
        ArrayList<CB> cbArrNotAdded = new ArrayList<CB>();
        Workbook workbook = null;

        try {
            workbook = WorkbookFactory.create(new FileInputStream(FILE_PATH));
        } catch (EmptyFileException e) {
            writeCB(cbArr);
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
        int cbArrSize = cbArr.size();
        int equalsCount;
        for (int i = 0; i < cbArrSize; i++) {
            equalsCount = 0;
            for (int j = 1; j <= rowCount; j++) {
                Row row = sheet.getRow(j);
                //check if there is some equalness
                if (row == null || row.getCell(2) == null);
                else if (cbArr.get(i).getARN().equals(row.getCell(2).toString()) ) equalsCount++;
            }
            if ( equalsCount == 0 ) cbArrNotAdded.add(cbArr.get(i));
        }
        //add new row if find something new
        if ( cbArrNotAdded.size() > 0 ) {
            rowCount++;
            for (CB cb : cbArrNotAdded) {
                Row row = sheet.createRow(rowCount++);

                row.createCell(0).setCellValue(cb.getMerchantName());
                row.createCell(1).setCellValue(cb.getMID());
                row.createCell(2).setCellValue(cb.getARN());
                row.createCell(3).setCellValue(cb.getMerchantReferenceID());
                row.createCell(4).setCellValue(cb.getTransactionDate());
                row.createCell(5).setCellValue(cb.getCardNumber());
                row.createCell(6).setCellValue(cb.getAuthCode());
                row.createCell(7).setCellValue(cb.getDisputeCurrency());
                row.createCell(8).setCellValue(cb.getDisputedAmount());
                row.createCell(9).setCellValue(cb.getReasonCode());
                row.createCell(10).setCellValue(cb.getReasonDescription());
                row.createCell(11).setCellValue(cb.getDate());
            }

            try {
                FileOutputStream fileOut = new FileOutputStream(FILE_PATH);
                workbook.write(fileOut);

                fileOut.close();
                workbook.close();
                MainAppController.addToLog("finish updating cb.xlsx", "g");
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
