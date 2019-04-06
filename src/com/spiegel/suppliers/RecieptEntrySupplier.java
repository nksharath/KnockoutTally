package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.RecieptEntry;
import org.apache.poi.ss.usermodel.Row;

public class RecieptEntrySupplier
{
    @Inject
    public RecieptEntrySupplier(final VchNoSupplier vchNoSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
    }

    public RecieptEntry get(final Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7
         */
        return new RecieptEntry(row.getCell(0).getDateCellValue(),
                                row.getCell(1).getStringCellValue(),
                                row.getCell(4).getStringCellValue(),
                                row.getCell(5).getStringCellValue(),
                                vchNoSupplier.get(row.getCell(5).getStringCellValue()),
                                row.getCell(6).getNumericCellValue(),
                                row.getCell(7).getNumericCellValue());
    }

    private final VchNoSupplier vchNoSupplier;
}
