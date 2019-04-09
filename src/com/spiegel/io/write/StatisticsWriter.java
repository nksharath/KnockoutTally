package com.spiegel.io.write;

import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.suppliers.TalliedEntryCountSupplier;
import com.spiegel.suppliers.UntalliedEntryCountSupplier;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

public class StatisticsWriter
{
    @Inject
    public StatisticsWriter(final TalliedEntryCountSupplier talliedEntryCountSupplier,
                            final UntalliedEntryCountSupplier untalliedEntryCountSupplier)
    {
        this.talliedEntryCountSupplier = talliedEntryCountSupplier;
        this.untalliedEntryCountSupplier = untalliedEntryCountSupplier;
    }

    public void writeStatisticSheet(final HSSFWorkbook workbook,
                                    final HSSFSheet statisticSheet,
                                    final List<IRecordEntry> recieptEntryList,
                                    final List<IRecordEntry> bangaloreEntryList,
                                    final List<IRecordEntry> hassanEntryList,
                                    final List<IRecordEntry> bankChargeEntryList,
                                    final List<ITallyEntry> tallyEntryList)
    {
        writeStatisticSheetHeader(workbook, statisticSheet);
        writeStatistics(workbook,
                        statisticSheet,
                        recieptEntryList,
                        bangaloreEntryList,
                        hassanEntryList,
                        bankChargeEntryList,
                        tallyEntryList);

    }

    private void writeStatistics(final HSSFWorkbook workbook,
                                 final HSSFSheet statisticSheet,
                                 final List<IRecordEntry> recieptEntryList,
                                 final List<IRecordEntry> bangaloreEntryList,
                                 final List<IRecordEntry> hassanEntryList,
                                 final List<IRecordEntry> bankChargeEntryList,
                                 final List<ITallyEntry> tallyEntryList)
    {
        int recieptCount = recieptEntryList.size();
        int bangaloreCount = bangaloreEntryList.size();
        int hassanCount = hassanEntryList.size();
        int bankChargesCount = bankChargeEntryList.size();
        int talliedCount = talliedEntryCountSupplier.get(tallyEntryList);
        int untalliedCount = untalliedEntryCountSupplier.get(tallyEntryList);

        final Row row = statisticSheet.createRow(1);

        Cell cell = row.createCell(0);
        cell.setCellValue(recieptCount);

        cell = row.createCell(1);
        cell.setCellValue(bangaloreCount);

        cell = row.createCell(2);
        cell.setCellValue(hassanCount);

        cell = row.createCell(3);
        cell.setCellValue(bankChargesCount);

        cell = row.createCell(4);
        cell.setCellValue(talliedCount);

        cell = row.createCell(5);
        cell.setCellValue(untalliedCount);
    }

    private void writeStatisticSheetHeader(final HSSFWorkbook workbook,
                                           final HSSFSheet statisticSheet)
    {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        final Row row = statisticSheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Reciept Entries Count");
        cell.setCellStyle(headerCellStyle);

        cell = row.createCell(1);
        cell.setCellValue("Bangalore Entries Count");
        cell.setCellStyle(headerCellStyle);

        cell = row.createCell(2);
        cell.setCellValue("Hassan Entries Count");
        cell.setCellStyle(headerCellStyle);

        cell = row.createCell(3);
        cell.setCellValue("Bank Charges Entries Count");
        cell.setCellStyle(headerCellStyle);

        cell = row.createCell(4);
        cell.setCellValue("Tallied Entries Count");
        cell.setCellStyle(headerCellStyle);

        cell = row.createCell(5);
        cell.setCellValue("Untallied Entries Count");
        cell.setCellStyle(headerCellStyle);

    }

    private final TalliedEntryCountSupplier talliedEntryCountSupplier;
    private final UntalliedEntryCountSupplier untalliedEntryCountSupplier;
}
