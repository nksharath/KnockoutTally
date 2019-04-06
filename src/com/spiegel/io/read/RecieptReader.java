package com.spiegel.io.read;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.predicates.RecieptRowPredicate;
import com.spiegel.suppliers.DelimiterSupplier;
import com.spiegel.suppliers.RecieptEntrySupplier;
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
public class RecieptReader
{
    @Inject
    public RecieptReader(final RecieptRowPredicate recieptRowPredicate,
                         final RecieptEntrySupplier recieptEntrySupplier,
                         final DelimiterSupplier delimiterSupplier)
    {
        this.recieptRowPredicate = recieptRowPredicate;
        this.recieptEntrySupplier = recieptEntrySupplier;
        this.delimiterSupplier = delimiterSupplier;
    }

    public List<IRecordEntry> readInput(final String folderPath)
    {
        final List<IRecordEntry> recieptEntryList = Lists.newArrayList();
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
                if (!recieptRowPredicate.test(currentRow))
                {
                    continue;
                }

                recieptEntryList.add(recieptEntrySupplier.get(currentRow));

            }
        }
        catch (IOException e)
        {
            log.error("Exception while parsing R.xls file", e);
        }

        return recieptEntryList;
    }


    private final String FILE_NAME = "R.xls";

    private final RecieptRowPredicate recieptRowPredicate;
    private final RecieptEntrySupplier recieptEntrySupplier;
    private final DelimiterSupplier delimiterSupplier;
}
