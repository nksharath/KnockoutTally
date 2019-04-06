package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.BankChargeEntry;
import org.apache.poi.ss.usermodel.Row;

public class BankChargeEntrySupplier
{
    @Inject
    public BankChargeEntrySupplier(final VchNoSupplier vchNoSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
    }

    public BankChargeEntry get(final Row row)
    {
        /*
        Row : Date, CR, Particulars, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,  2,         , 3    , 4                , 5      , 6    , 7

        We are not checking if Vch No. if valid : We want to capture the invalids as not tallied
         */
        return new BankChargeEntry(row.getCell(0).getDateCellValue(),
                                   row.getCell(2).getStringCellValue(),
                                   row.getCell(4).getStringCellValue(),
                                   row.getCell(5).getStringCellValue(),
                                   vchNoSupplier.get(row.getCell(5).getStringCellValue()),
                                   row.getCell(6).getNumericCellValue(),
                                   row.getCell(7).getNumericCellValue());
    }

    private final VchNoSupplier vchNoSupplier;
}
