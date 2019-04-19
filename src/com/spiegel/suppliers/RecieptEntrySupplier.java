package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.CellExpectation;
import com.spiegel.pojos.RecieptEntry;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;

public class RecieptEntrySupplier
{
    @Inject
    public RecieptEntrySupplier(final VchNoSupplier vchNoSupplier,
                                final CellObjectSupplier cellObjectSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
        this.cellObjectSupplier = cellObjectSupplier;

    }

    public RecieptEntry get(final Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7
         */
        return new RecieptEntry((Date) cellObjectSupplier.get(row.getCell(0), CellExpectation.DATE),
                                (String) cellObjectSupplier.get(row.getCell(1), CellExpectation.STRING),
                                (String) cellObjectSupplier.get(row.getCell(4), CellExpectation.STRING),
                                (String) cellObjectSupplier.get(row.getCell(5), CellExpectation.STRING),
                                vchNoSupplier.get((String) cellObjectSupplier.get(row.getCell(5), CellExpectation.STRING)),
                                (double) cellObjectSupplier.get(row.getCell(6), CellExpectation.NUMERIC),
                                (double) cellObjectSupplier.get(row.getCell(7), CellExpectation.NUMERIC));
    }

    private final VchNoSupplier vchNoSupplier;
    private final CellObjectSupplier cellObjectSupplier;
}
