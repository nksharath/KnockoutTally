package com.spiegel.io.write;

import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.BangaloreEntry;
import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.HassanEntry;
import com.spiegel.pojos.RecieptEntry;
import com.spiegel.pojos.TalliedEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.suppliers.CellTextSupplier;
import com.spiegel.suppliers.MaxNumberSupplier;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
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
                        final MaxNumberSupplier maxNumberSupplier)
    {
        this.cellTextSupplier = cellTextSupplier;
        this.maxNumberSupplier = maxNumberSupplier;
    }

    public void writeReport(final List<ITallyEntry> tallyEntryList)
    {
        final HSSFWorkbook workbook = new HSSFWorkbook();
        final HSSFSheet talliedSheet = workbook.createSheet("Tallied");
        final HSSFSheet unTalliedSheet = workbook.createSheet("Untallied");
        writeHeaders(workbook, talliedSheet);
        writeHeaders(workbook, unTalliedSheet);
        writeData(workbook, talliedSheet, unTalliedSheet, tallyEntryList);
        close(workbook);
    }

    private void writeHeaders(final HSSFWorkbook workbook, final HSSFSheet hssfSheet)
    {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.GREEN.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = hssfSheet.createRow(0);

        // Create cells
        for (int i = 0; i < COLUMNS.length; i++)
        {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(COLUMNS[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    private void writeData(final HSSFWorkbook workbook,
                           final HSSFSheet talliedSheet,
                           final HSSFSheet unTalliedSheet,
                           final List<ITallyEntry> tallyEntryList)
    {
        int talliedRowIndex = 1;
        int unTalliedRowIndex = 1;

        for (final ITallyEntry tallyEntry : tallyEntryList)
        {
            if (tallyEntry instanceof TalliedEntry)
            {
                final Row rowEntry = talliedSheet.createRow(talliedRowIndex);
                talliedRowIndex = fillRow(tallyEntry, rowEntry, talliedRowIndex, talliedSheet);
            }
            else if (tallyEntry instanceof UntalliedEntry)
            {
                final Row rowEntry = unTalliedSheet.createRow(unTalliedRowIndex);
                unTalliedRowIndex = fillRow(tallyEntry, rowEntry, unTalliedRowIndex, unTalliedSheet);
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

    private int fillRow(final ITallyEntry tallyEntry, final Row row, int rowIndex, final HSSFSheet sheet)
    {
        int columnIndex = 0;

        if (true)
        {
            final List<IRecordEntry> recieptEntryList = tallyEntry.getRecieptEntry();
            final List<IRecordEntry> bangaloreEntryList = tallyEntry.getBangaloreEntry();
            final List<IRecordEntry> hassanEntryList = tallyEntry.getHassanEntry();
            final List<IRecordEntry> bankChargeEntryList = tallyEntry.getBankChargeEntry();

            int rowIndexCopyOne = rowIndex;
            int rowIndexCopyTwo = rowIndex;
            int rowIndexCopyThree = rowIndex;
            int rowIndexCopyFour = rowIndex;

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
            }

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
            }

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
            }

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
            }

            return maxNumberSupplier.get(Arrays.asList(rowIndex,
                                                       rowIndexCopyOne,
                                                       rowIndexCopyTwo,
                                                       rowIndexCopyThree,
                                                       rowIndexCopyFour)) + 2;
        }
        else
        {
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
    }

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
                                             };
}
