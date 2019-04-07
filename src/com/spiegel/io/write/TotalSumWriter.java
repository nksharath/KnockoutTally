package com.spiegel.io.write;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class TotalSumWriter
{
    public void fillRow(final HSSFSheet sheet,
                        int totalDisplayRow,
                        double recieptTotal,
                        double bangaloreTotal,
                        double hassanTotal,
                        double bankChargesTotal)
    {
        final Row totalRow;
        if (sheet.getRow(totalDisplayRow) != null)
        {
            totalRow = sheet.getRow(totalDisplayRow);
        }
        else
        {
            totalRow = sheet.createRow(totalDisplayRow);
        }

        Cell cell = totalRow.createCell(1);
        cell.setCellValue("Total");

        cell = totalRow.createCell(2);
        cell.setCellValue(recieptTotal);

        cell = totalRow.createCell(4);
        cell.setCellValue(bangaloreTotal);

        cell = totalRow.createCell(6);
        cell.setCellValue(hassanTotal);

        cell = totalRow.createCell(8);
        cell.setCellValue(bankChargesTotal);
    }
}
