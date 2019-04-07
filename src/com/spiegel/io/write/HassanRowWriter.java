package com.spiegel.io.write;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.HassanEntry;
import com.spiegel.pojos.RowSum;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class HassanRowWriter
{
    public RowSum fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        int rowIndexCopyThree = rowIndex;
        double hassanTotal = 0.0D;

        final List<IRecordEntry> hassanEntryList = tallyEntry.getHassanEntry();
        for (final IRecordEntry recordEntry : hassanEntryList)
        {
            final HassanEntry hassanEntry = (HassanEntry) recordEntry;

            Row rowInternal;

            if (sheet.getRow(rowIndexCopyThree) != null)
            {
                rowInternal = sheet.getRow(rowIndexCopyThree++);
            }
            else
            {
                rowInternal = sheet.createRow(rowIndexCopyThree++);
            }

            Cell cell = rowInternal.createCell(5);
            cell.setCellValue(hassanEntry.getVchNo());

            cell = rowInternal.createCell(6);
            cell.setCellValue(hassanEntry.getCredit());

            hassanTotal += hassanEntry.getCredit();

        }
        return new RowSum(rowIndexCopyThree, hassanTotal);
    }
}
