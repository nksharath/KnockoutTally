package com.spiegel.io.write;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.RecieptEntry;
import com.spiegel.pojos.RowSum;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class RecieptRowWriter
{
    public RowSum fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        final List<IRecordEntry> recieptEntryList = tallyEntry.getRecieptEntry();
        int rowIndexCopyOne = rowIndex;
        double recieptTotal = 0.0D;

        for (final IRecordEntry recordEntry : recieptEntryList)
        {
            final RecieptEntry recieptEntry = (RecieptEntry) recordEntry;
            Row rowInternal;

            if (sheet.getRow(rowIndexCopyOne) != null)
            {
                rowInternal = sheet.getRow(rowIndexCopyOne++);
            }
            else
            {
                rowInternal = sheet.createRow(rowIndexCopyOne++);
            }

            Cell cell = rowInternal.createCell(0);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            cell.setCellValue(simpleDateFormat.format(recieptEntry.getDate()));

            cell = rowInternal.createCell(1);
            cell.setCellValue(recieptEntry.getVchNo());

            cell = rowInternal.createCell(2);
            cell.setCellValue(recieptEntry.getDebit());

            recieptTotal += recieptEntry.getDebit();

        }
        return new RowSum(rowIndexCopyOne, recieptTotal);
    }
}
