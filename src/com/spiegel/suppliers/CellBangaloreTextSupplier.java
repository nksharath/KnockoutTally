package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BangaloreEntry;
import java.util.List;

public class CellBangaloreTextSupplier
{
    public List<String> getExcelFriendly(final IRecordEntry recordEntry)
    {
        final List<String> entryList = Lists.newArrayList();
        final BangaloreEntry bangaloreEntry = (BangaloreEntry) recordEntry;

        entryList.add(bangaloreEntry.getVchNo());
        entryList.add(Double.toString(bangaloreEntry.getCredit()));
        return entryList;
    }

    public String get(final IRecordEntry recordEntry)
    {
        final BangaloreEntry bangaloreEntry = (BangaloreEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();


        stringBuilder.append(VCH_NO);
        stringBuilder.append(bangaloreEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(bangaloreEntry.getCredit());
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
