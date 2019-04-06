package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.BangaloreEntry;
import org.apache.poi.ss.usermodel.Row;

public class BangaloreEntrySupplier
{
    @Inject
    public BangaloreEntrySupplier(final VchNoSupplier vchNoSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
    }

    public BangaloreEntry get(Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7
         */

        return new BangaloreEntry(row.getCell(2).getStringCellValue(),
                                  row.getCell(3).getStringCellValue(),
                                  vchNoSupplier.get(row.getCell(3).getStringCellValue()),
                                  row.getCell(5).getNumericCellValue(),
                                  row.getCell(6).getStringCellValue());
    }

    private final VchNoSupplier vchNoSupplier;
}
