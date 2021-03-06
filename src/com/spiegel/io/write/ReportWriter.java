package com.spiegel.io.write;

import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.RowSum;
import com.spiegel.pojos.TalliedEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.suppliers.CellTextSupplier;
import com.spiegel.suppliers.MaxNumberSupplier;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

@Slf4j
public class ReportWriter
{
    @Inject
    public ReportWriter(final CellTextSupplier cellTextSupplier,
                        final MaxNumberSupplier maxNumberSupplier,
                        final DuplicateWriter duplicateWriter,
                        final RecieptRowWriter recieptRowWriter,
                        final BangaloreRowWriter bangaloreRowWriter,
                        final HassanRowWriter hassanRowWriter,
                        final BankChargesRowWriter bankChargesRowWriter,
                        final TotalSumWriter totalSumWriter,
                        final StatisticsWriter statisticsWriter)
    {
        this.cellTextSupplier = cellTextSupplier;
        this.maxNumberSupplier = maxNumberSupplier;
        this.duplicateWriter = duplicateWriter;
        this.recieptRowWriter = recieptRowWriter;
        this.bangaloreRowWriter = bangaloreRowWriter;
        this.hassanRowWriter = hassanRowWriter;
        this.bankChargesRowWriter = bankChargesRowWriter;
        this.totalSumWriter = totalSumWriter;
        this.statisticsWriter = statisticsWriter;
    }

    public void writeReport(final List<IRecordEntry> recieptEntryList,
                            final List<IRecordEntry> bangaloreEntryList,
                            final List<IRecordEntry> hassanEntryList,
                            final List<IRecordEntry> bankChargeEntryList,
                            final List<ITallyEntry> tallyEntryList)
    {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet talliedSheet = workbook.createSheet("Tallied");
        final HSSFSheet unTalliedSheet = workbook.createSheet("Untallied");
        final HSSFSheet duplicateSheet = workbook.createSheet("Duplicate");
        final HSSFSheet statisticSheet = workbook.createSheet("Statistics");
        // 0=yes, 1=no, 2=cancel
        int legacySetting = JOptionPane.showConfirmDialog(null, "Do you want Printable Summary Report instead of Excel?");
        boolean printableReport = (0 == legacySetting) ? true : false;

        writeHeaders(workbook, talliedSheet, printableReport);
        writeHeaders(workbook, unTalliedSheet, printableReport);
        writeData(workbook, talliedSheet, unTalliedSheet, tallyEntryList, printableReport);
        duplicateWriter.writeDuplicateSheet(workbook, duplicateSheet, tallyEntryList);
        statisticsWriter.writeStatisticSheet(workbook,
                                             statisticSheet,
                                             recieptEntryList,
                                             bangaloreEntryList,
                                             hassanEntryList,
                                             bankChargeEntryList,
                                             tallyEntryList);

        close(workbook);
    }

    private void writeHeaders(final HSSFWorkbook workbook, final HSSFSheet hssfSheet, final boolean printableReport)
    {
        final String[] columns = (printableReport == true) ? LEGACY_COLUMNS : COLUMNS;

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = hssfSheet.createRow(0);

        // Create cells
        for (int i = 0; i < columns.length; i++)
        {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void writeData(final HSSFWorkbook workbook,
                           final HSSFSheet talliedSheet,
                           final HSSFSheet unTalliedSheet,
                           final List<ITallyEntry> tallyEntryList,
                           final boolean printableReport)
    {
        int talliedRowIndex = 1;
        int unTalliedRowIndex = 1;


        for (final ITallyEntry tallyEntry : tallyEntryList)
        {
            if (tallyEntry instanceof TalliedEntry)
            {
                final Row rowEntry = talliedSheet.createRow(talliedRowIndex);
                talliedRowIndex = fillRow(tallyEntry, rowEntry, talliedRowIndex, talliedSheet, printableReport);
            }
            else if (tallyEntry instanceof UntalliedEntry)
            {
                final Row rowEntry = unTalliedSheet.createRow(unTalliedRowIndex);
                unTalliedRowIndex = fillRow(tallyEntry, rowEntry, unTalliedRowIndex, unTalliedSheet, printableReport);
            }
        }
    }

    private void close(final HSSFWorkbook workbook)
    {
        try
        {
            final FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        }
        catch (IOException e)
        {
            log.error("Unable to flush results", e);
        }
    }

    private int fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet, boolean printableReport)
    {
        if (!printableReport)
        {

            final RowSum recieptRow = recieptRowWriter.fillRow(tallyEntry, row, rowIndex, sheet);
            final RowSum bangaloreRow = bangaloreRowWriter.fillRow(tallyEntry, row, rowIndex, sheet);
            final RowSum hassanRow = hassanRowWriter.fillRow(tallyEntry, row, rowIndex, sheet);
            final RowSum bankChargesRow = bankChargesRowWriter.fillRow(tallyEntry, row, rowIndex, sheet);


            int totalDisplayRow = maxNumberSupplier.get(Arrays.asList(rowIndex,
                                                                      recieptRow.getUpdatedRow(),
                                                                      bangaloreRow.getUpdatedRow(),
                                                                      hassanRow.getUpdatedRow(),
                                                                      bankChargesRow.getUpdatedRow())) + 2;


            totalSumWriter.fillRow(sheet,
                                   totalDisplayRow,
                                   recieptRow.getSum(),
                                   bangaloreRow.getSum(),
                                   hassanRow.getSum(),
                                   bankChargesRow.getSum());


            return totalDisplayRow + 3;
        }
        else
        {
            return fillRowPrintable(tallyEntry, row, rowIndex, sheet);
        }
    }

    private int fillRowPrintable(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        int columnIndex = 0;
        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getRecieptEntry()));


        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getBangaloreEntry()));


        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getHassanEntry()));


        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getBankChargeEntry()));

        return rowIndex + 1;
    }

    private final RecieptRowWriter recieptRowWriter;
    private final BangaloreRowWriter bangaloreRowWriter;
    private final HassanRowWriter hassanRowWriter;
    private final BankChargesRowWriter bankChargesRowWriter;
    private final TotalSumWriter totalSumWriter;
    private final StatisticsWriter statisticsWriter;

    private final DuplicateWriter duplicateWriter;
    private final MaxNumberSupplier maxNumberSupplier;
    private final CellTextSupplier cellTextSupplier;
    private static final String FILE_NAME = "TallyReport.xls";
    private static final String[] COLUMNS = {"Receipt Date",
                                             "Receipt VchNo",
                                             "Receipt Amount",
                                             "Bangalore VchNo",
                                             "Bangalore Amount",
                                             "Hassan VchNo",
                                             "Hassan Amount",
                                             "BankCharges VchNo",
                                             "BankCharges Amount",
                                             "BankCharges Particulars",
                                             };

    private static final String[] LEGACY_COLUMNS = {"Receipt",
                                                    "Bangalore",
                                                    "Hassan",
                                                    "Bank Charges"};
}
