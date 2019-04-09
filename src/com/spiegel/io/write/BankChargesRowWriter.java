package com.spiegel.io.write;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.RowSum;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class BankChargesRowWriter
{
    public RowSum fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        int rowIndexCopyFour = rowIndex;
        double bankChargesTotal = 0.0D;

        final List<IRecordEntry> bankChargeEntryList = tallyEntry.getBankChargeEntry();
        for (final IRecordEntry recordEntry : bankChargeEntryList)
        {
            final BankChargeEntry bankChargeEntry = (BankChargeEntry) recordEntry;

            Row rowInternal;

            if (sheet.getRow(rowIndexCopyFour) != null)
            {
                rowInternal = sheet.getRow(rowIndexCopyFour++);
            }
            else
            {
                rowInternal = sheet.createRow(rowIndexCopyFour++);
            }

            Cell cell = rowInternal.createCell(7);
            cell.setCellValue(bankChargeEntry.getVchNo());

            cell = rowInternal.createCell(8);
            cell.setCellValue(bankChargeEntry.getDebit());

            cell = rowInternal.createCell(9);
            cell.setCellValue(bankChargeEntry.getParticulars());

            bankChargesTotal += bankChargeEntry.getDebit();

        }
        return new RowSum(rowIndexCopyFour, bankChargesTotal);
    }
}
