package com.spiegel.io.read;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.predicates.BangaloreRowPredicate;
import com.spiegel.suppliers.BangaloreEntrySupplier;
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
public class BangaloreReader
{
    @Inject
    public BangaloreReader(final DelimiterSupplier delimiterSupplier,
                           final BangaloreRowPredicate bangaloreRowPredicate,
                           final BangaloreEntrySupplier bangaloreEntrySupplier)
    {
        this.delimiterSupplier = delimiterSupplier;
        this.bangaloreRowPredicate = bangaloreRowPredicate;
        this.bangaloreEntrySupplier = bangaloreEntrySupplier;
    }

    public List<IRecordEntry> readInput(final String folderPath)
    {
        final List<IRecordEntry> bangaloreEntryList = Lists.newArrayList();
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
                if (!bangaloreRowPredicate.test(currentRow))
                {
                    continue;
                }

                bangaloreEntryList.add(bangaloreEntrySupplier.get(currentRow));

            }
        }
        catch (IOException e)
        {
            log.error("Exception while parsing R.xls file", e);
        }

        return bangaloreEntryList;
    }

    private final String FILE_NAME = "B.xls";

    private final DelimiterSupplier delimiterSupplier;
    private final BangaloreRowPredicate bangaloreRowPredicate;
    private final BangaloreEntrySupplier bangaloreEntrySupplier;
}
