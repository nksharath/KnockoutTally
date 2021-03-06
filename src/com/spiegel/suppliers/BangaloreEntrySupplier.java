package com.spiegel.suppliers;

import com.google.inject.Inject;

import com.spiegel.pojos.BangaloreEntry;
import com.spiegel.pojos.CellExpectation;
import org.apache.poi.ss.usermodel.Row;

public class BangaloreEntrySupplier
{
    @Inject
    public BangaloreEntrySupplier(final VchNoSupplier vchNoSupplier,
                                  final CellObjectSupplier cellObjectSupplier)
    {
        this.vchNoSupplier = vchNoSupplier;
        this.cellObjectSupplier = cellObjectSupplier;
    }

    public BangaloreEntry get(Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7
         */

        return new BangaloreEntry((String) cellObjectSupplier.get(row.getCell(2), CellExpectation.STRING),
                                  (String) cellObjectSupplier.get(row.getCell(3), CellExpectation.STRING),
                                  vchNoSupplier.get((String) cellObjectSupplier.get(row.getCell(3), CellExpectation.STRING)),
                                  (double) cellObjectSupplier.get(row.getCell(5), CellExpectation.NUMERIC),
                                  (String) cellObjectSupplier.get(row.getCell(6), CellExpectation.STRING));
    }

    private final VchNoSupplier vchNoSupplier;
    private final CellObjectSupplier cellObjectSupplier;
}
