package com.spiegel.io.write;

import com.google.inject.Inject;

import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.TalliedEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.suppliers.CellTextSupplier;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public ReportWriter(final CellTextSupplier cellTextSupplier)
    {
        this.cellTextSupplier = cellTextSupplier;
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
                final Row rowEntry = talliedSheet.createRow(talliedRowIndex++);
                fillRow(tallyEntry, rowEntry, workbook, talliedSheet);
            }
            else if (tallyEntry instanceof UntalliedEntry)
            {
                final Row rowEntry = unTalliedSheet.createRow(unTalliedRowIndex++);
                fillRow(tallyEntry, rowEntry, workbook, unTalliedSheet);
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

    private void fillRow(final ITallyEntry tallyEntry, final Row row, final HSSFWorkbook workbook, final HSSFSheet sheet)
    {
        int columnIndex = 0;

        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);
        row.setHeightInPoints((10 * sheet.getDefaultRowHeightInPoints()));


        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getRecieptEntry()));
        cell.setCellStyle(cs);
        sheet.autoSizeColumn(columnIndex);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getBangaloreEntry()));
        cell.setCellStyle(cs);
        sheet.autoSizeColumn(columnIndex);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getHassanEntry()));
        cell.setCellStyle(cs);
        sheet.autoSizeColumn(columnIndex);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(cellTextSupplier.get(tallyEntry.getBankChargeEntry()));
        cell.setCellStyle(cs);
        sheet.autoSizeColumn(columnIndex);
    }

    private final CellTextSupplier cellTextSupplier;
    private static final String FILE_NAME = "TallyReport.xls";
    private static final String[] COLUMNS = {"Receipt",
                                             "Bangalore",
                                             "Hassan",
                                             "Bank Charges"};
}
