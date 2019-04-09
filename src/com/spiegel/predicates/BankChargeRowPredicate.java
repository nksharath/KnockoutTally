package com.spiegel.predicates;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class BankChargeRowPredicate implements Predicate<Row>
{
    @Override
    public boolean test(final Row row)
    {
        /*
        Row : Date, Particulars, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3                , 4      , 5    , 6

        We are not checking if Vch No. if valid : We want to capture the invalids as not tallied
         */

        if (CellType.STRING != row.getCell(4).getCellTypeEnum())
        {
            return false;
        }


        if (ALLOWED.contains(row.getCell(4).getStringCellValue().toLowerCase()))
        {

            if (row.getCell(6).getNumericCellValue() != 0.0D)
            {
                return true;
            }
        }

        return false;
    }

    private final static List<String> ALLOWED = Arrays.asList("receipt", "journal");
}
