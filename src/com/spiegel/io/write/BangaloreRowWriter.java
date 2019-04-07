package com.spiegel.io.write;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.BangaloreEntry;
import com.spiegel.pojos.RowSum;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class BangaloreRowWriter
{
    public RowSum fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        int rowIndexCopyTwo = rowIndex;
        double bangaloreTotal = 0.0D;

        final List<IRecordEntry> bangaloreEntryList = tallyEntry.getBangaloreEntry();

        for (final IRecordEntry recordEntry : bangaloreEntryList)
        {
            final BangaloreEntry bangaloreEntry = (BangaloreEntry) recordEntry;

            Row rowInternal;

            if (sheet.getRow(rowIndexCopyTwo) != null)
            {
                rowInternal = sheet.getRow(rowIndexCopyTwo++);
            }
            else
            {
                rowInternal = sheet.createRow(rowIndexCopyTwo++);
            }

            Cell cell = rowInternal.createCell(3);
            cell.setCellValue(bangaloreEntry.getVchNo());

            cell = rowInternal.createCell(4);
            cell.setCellValue(bangaloreEntry.getCredit());

            bangaloreTotal += bangaloreEntry.getCredit();
        }

        return new RowSum(rowIndexCopyTwo, bangaloreTotal);
    }
}
