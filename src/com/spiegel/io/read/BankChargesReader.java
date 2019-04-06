package com.spiegel.io.read;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.predicates.BankChargeRowPredicate;
import com.spiegel.suppliers.BankChargeEntrySupplier;
import com.spiegel.suppliers.DelimiterSupplier;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

@Slf4j
public class BankChargesReader
{
    @Inject
    public BankChargesReader(final BankChargeRowPredicate bankChargeRowPredicate,
                             final BankChargeEntrySupplier bankChargeEntrySupplier,
                             final DelimiterSupplier delimiterSupplier)
    {
        this.bankChargeRowPredicate = bankChargeRowPredicate;
        this.bankChargeEntrySupplier = bankChargeEntrySupplier;
        this.delimiterSupplier = delimiterSupplier;
    }

    public List<IRecordEntry> readInput(final String folderPath)
    {
        final List<IRecordEntry> bankChargeEntryList = Lists.newArrayList();
        try
        {
            final StringBuilder stringBuilder = new StringBuilder(folderPath);
            final FileInputStream excelFile = new FileInputStream(new File(stringBuilder.append(delimiterSupplier.get())
                                                                                        .append(FILE_NAME).toString()));
            final Workbook workbook = new HSSFWorkbook(excelFile);
            final Sheet sheet = workbook.getSheetAt(0);
            final Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext())
            {

                final Row currentRow = iterator.next();
                if (!bankChargeRowPredicate.test(currentRow))
                {
                    continue;
                }

                bankChargeEntryList.add(bankChargeEntrySupplier.get(currentRow));

            }
        }
        catch (IOException e)
        {
            log.error("Exception while parsing R.xls file", e);
        }

        return bankChargeEntryList;
    }


    private final String FILE_NAME = "BC.xls";

    private final BankChargeRowPredicate bankChargeRowPredicate;
    private final BankChargeEntrySupplier bankChargeEntrySupplier;
    private final DelimiterSupplier delimiterSupplier;
}
