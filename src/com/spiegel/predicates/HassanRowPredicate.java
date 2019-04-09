package com.spiegel.predicates;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class HassanRowPredicate implements Predicate<Row>
{
    @Override
    public boolean test(final Row row)
    {
        /*
        Row : Date, Particulars, blank, blank, Vch Type(Reciept), Vch No., Debit, Credit
        Row : 0,    1,         , 2    , 3    , 4                , 5      , 6    , 7

        We are not checking if Vch No. if valid : We want to capture the invalids as not tallied
         */

        if (!Optional.ofNullable(row).map(rs -> row.getCell(2)).isPresent())
        {
            return false;
        }

        if (!Optional.ofNullable(row).map(rs -> row.getCell(6)).isPresent())
        {
            return false;
        }

        if (!Optional.ofNullable(row).map(rs -> row.getCell(5)).isPresent())
        {
            return false;
        }


        if (CellType.STRING != row.getCell(2).getCellTypeEnum())
        {
            return false;
        }

        if (CellType.STRING != row.getCell(6).getCellTypeEnum())
        {
            return false;
        }

        if (CellType.NUMERIC != row.getCell(5).getCellTypeEnum())
        {
            return false;
        }


        if (ALLOWED.contains(row.getCell(2).getStringCellValue().toLowerCase()))
        {
            if (row.getCell(5).getNumericCellValue() != 0.0D)
            {
                if (row.getCell(6).getStringCellValue().toLowerCase().contains(CREDIT))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private final static List<String> ALLOWED = Arrays.asList("new ref", "agst ref");
    private final static String CREDIT = "cr";
}
