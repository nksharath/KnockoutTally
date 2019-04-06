package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.HassanEntry;
import java.util.List;

public class CellHassanTextSupplier
{
    public List<String> getExcelFriendly(final IRecordEntry recordEntry)
    {
        final List<String> entryList = Lists.newArrayList();
        final HassanEntry hassanEntry = (HassanEntry) recordEntry;

        entryList.add(hassanEntry.getVchNo());
        entryList.add(Double.toString(hassanEntry.getCredit()));
        return entryList;
    }

    public String get(final IRecordEntry recordEntry)
    {
        final HassanEntry hassanEntry = (HassanEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(VCH_NO);
        stringBuilder.append(hassanEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(hassanEntry.getCredit());
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();

    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
