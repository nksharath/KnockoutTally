package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.CellExpectation;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;

public class BankChargeEntrySupplier
{
    @Inject
    public BankChargeEntrySupplier(final VchNoSupplier vchNoSupplier,
                                   final CellObjectSupplier cellObjectSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
        this.cellObjectSupplier = cellObjectSupplier;
    }

    public BankChargeEntry get(final Row row)
    {
        /*
        Row : Date, CR, Particulars, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,  2,         , 3    , 4                , 5      , 6    , 7

        We are not checking if Vch No. if valid : We want to capture the invalids as not tallied
         */
        return new BankChargeEntry((Date) cellObjectSupplier.get(row.getCell(0), CellExpectation.DATE),
                                   (String) cellObjectSupplier.get(row.getCell(2), CellExpectation.STRING),
                                   (String) cellObjectSupplier.get(row.getCell(4), CellExpectation.STRING),
                                   (String) cellObjectSupplier.get(row.getCell(5), CellExpectation.STRING),
                                   vchNoSupplier.get((String) cellObjectSupplier.get(row.getCell(5), CellExpectation.STRING)),
                                   (double) cellObjectSupplier.get(row.getCell(6), CellExpectation.NUMERIC),
                                   (double) cellObjectSupplier.get(row.getCell(7), CellExpectation.NUMERIC));
    }

    private final VchNoSupplier vchNoSupplier;
    private final CellObjectSupplier cellObjectSupplier;
}
