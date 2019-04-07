package com.spiegel.io.write;

import com.google.common.collect.Sets;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.RecieptEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.suppliers.UntalliedEntrySupplier;
import com.spiegel.suppliers.VchNoSupplier;
import java.util.List;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class DuplicateWriter
{
    @Inject
    public DuplicateWriter(final VchNoSupplier vchNoSupplier,
                           final UntalliedEntrySupplier untalliedEntrySupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
        this.untalliedEntrySupplier = untalliedEntrySupplier;
    }

    public void writeDuplicateSheet(final HSSFWorkbook workbook,
                                    final HSSFSheet duplicateSheet,
                                    final List<ITallyEntry> tallyEntryList)
    {
        writeDuplicateSheetHeader(workbook, duplicateSheet);
        writeDuplicateVouchers(workbook, duplicateSheet, tallyEntryList);
    }

    private void writeDuplicateSheetHeader(final HSSFWorkbook workbook,
                                           final HSSFSheet duplicateSheet)
    {
        final Row row = duplicateSheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("Duplicate Voucher Numbers not tallied");
    }

    private void writeDuplicateVouchers(final HSSFWorkbook workbook,
                                        final HSSFSheet duplicateSheet,
                                        final List<ITallyEntry> tallyEntryList)
    {
        int rowIndex = 1;
        final List<UntalliedEntry> untalliedEntryList = untalliedEntrySupplier.get(tallyEntryList);
        final Set<String> collisionFinder = Sets.newHashSet();
        for (final UntalliedEntry untalliedEntry : untalliedEntryList)
        {
            final List<IRecordEntry> recordEntryList = untalliedEntry.getRecieptEntry();
            for (final IRecordEntry recordEntry : recordEntryList)
            {
                final RecieptEntry recieptEntry = (RecieptEntry) recordEntry;
                final List<String> vchList = vchNoSupplier.get(recieptEntry.getVchNo());
                for (final String vchNo : vchList)
                {
                    if (collisionFinder.contains(vchNo))
                    {
                        Row row = duplicateSheet.createRow(rowIndex++);
                        Cell cell = row.createCell(0);
                        cell.setCellValue(vchNo);
                    }
                    else
                    {
                        collisionFinder.add(vchNo);
                    }
                }
            }
        }
    }

    private UntalliedEntrySupplier untalliedEntrySupplier;
    private VchNoSupplier vchNoSupplier;
}
