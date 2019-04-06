package com.spiegel.suppliers;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BangaloreEntry;

public class CellBangaloreTextSupplier
{
    public String get(final IRecordEntry recordEntry)
    {
        final BangaloreEntry bangaloreEntry = (BangaloreEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(VCH_NO);
        stringBuilder.append(bangaloreEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(bangaloreEntry.getCredit());

        return stringBuilder.toString();
    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
