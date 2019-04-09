package com.spiegel.io.write;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.RecieptEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.suppliers.UntalliedEntrySupplier;
import com.spiegel.suppliers.VchNoSupplier;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        final Row row = duplicateSheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Duplicate Voucher Numbers not tallied");
        cell.setCellStyle(headerCellStyle);


        cell = row.createCell(1);
        cell.setCellValue("Count");
        cell.setCellStyle(headerCellStyle);

    }

    private void writeDuplicateVouchers(final HSSFWorkbook workbook,
                                        final HSSFSheet duplicateSheet,
                                        final List<ITallyEntry> tallyEntryList)
    {
        int rowIndex = 1;
        final List<UntalliedEntry> untalliedEntryList = untalliedEntrySupplier.get(tallyEntryList);
        final TreeMap<String, Integer> duplicateFrequencyMap = Maps.newTreeMap();
        for (final UntalliedEntry untalliedEntry : untalliedEntryList)
        {
            final List<IRecordEntry> recordEntryList = untalliedEntry.getRecieptEntry();
            for (final IRecordEntry recordEntry : recordEntryList)
            {
                final RecieptEntry recieptEntry = (RecieptEntry) recordEntry;
                final List<String> vchList = vchNoSupplier.get(recieptEntry.getVchNo());
                for (final String vchNo : vchList)
                {
                    if (duplicateFrequencyMap.containsKey(vchNo))
                    {
                        int frequency = duplicateFrequencyMap.get(vchNo);
                        duplicateFrequencyMap.put(vchNo, frequency + 1);
                    }
                    else
                    {
                        duplicateFrequencyMap.put(vchNo, 1);
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : duplicateFrequencyMap.entrySet())
        {
            if (entry.getValue() <= 1)
            {
                continue;
            }

            Row row = duplicateSheet.createRow(rowIndex++);

            Cell cell = row.createCell(0);
            cell.setCellValue(entry.getKey());

            cell = row.createCell(1);
            cell.setCellValue(entry.getValue());
        }

    }

    private UntalliedEntrySupplier untalliedEntrySupplier;
    private VchNoSupplier vchNoSupplier;
}
