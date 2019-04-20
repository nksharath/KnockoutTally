package com.spiegel.suppliers;

import com.spiegel.pojos.CellExpectation;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellObjectSupplier
{
    public Object get(final Cell cell, CellExpectation cellExpectation)
    {
        try
        {
            if (cell == null)
            {
                return buildExpectedCell(cellExpectation);
            }

            if (CellType.NUMERIC == cell.getCellTypeEnum() && cellExpectation == CellExpectation.DATE)
            {
                return cell.getDateCellValue();
            }

            if (CellType.STRING == cell.getCellTypeEnum() && cellExpectation == CellExpectation.DATE)
            {
                return new Date(cell.getStringCellValue());
            }

            if (CellType.STRING == cell.getCellTypeEnum())
            {
                return cell.getStringCellValue();
            }

            if (CellType.NUMERIC == cell.getCellTypeEnum())
            {
                return cell.getNumericCellValue();
            }

            if (CellType.BLANK == cell.getCellTypeEnum())
            {
                return buildExpectedCell(cellExpectation);
            }
        }
        catch (Exception ex)
        {
            return buildExpectedCell(cellExpectation);
        }

        return buildExpectedCell(cellExpectation);
    }

    private Object buildExpectedCell(final CellExpectation cellExpectation)
    {
        if (cellExpectation == CellExpectation.DATE)
        {
            return new Date();
        }

        if (cellExpectation == CellExpectation.STRING)
        {
            return "";
        }

        if (cellExpectation == CellExpectation.NUMERIC)
        {
            return 0.0;
        }

        return "";
    }
}
