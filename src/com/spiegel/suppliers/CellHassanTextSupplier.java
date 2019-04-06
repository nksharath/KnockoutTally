package com.spiegel.suppliers;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.HassanEntry;

public class CellHassanTextSupplier
{
    public String get(final IRecordEntry recordEntry)
    {
        final HassanEntry hassanEntry = (HassanEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(VCH_NO);
        stringBuilder.append(hassanEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(hassanEntry.getCredit());

        return stringBuilder.toString();

    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
