package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.HassanEntry;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class HassanEntrySupplier
{
    @Inject
    public HassanEntrySupplier(final VchNoSupplier vchNoSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
    }

    public HassanEntry get(Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7
         */

        final String vchNo;
        if (CellType.STRING == row.getCell(3).getCellTypeEnum())
        {
            vchNo = row.getCell(3).getStringCellValue();
        }
        else
        {
            vchNo = Double.toString(row.getCell(3).getNumericCellValue());
        }

        return new HassanEntry(row.getCell(2).getStringCellValue(),
                               vchNo,
                               vchNoSupplier.get(vchNo),
                               row.getCell(5).getNumericCellValue(),
                               row.getCell(6).getStringCellValue());
    }

    private final VchNoSupplier vchNoSupplier;
}
