package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.RecieptEntry;
import java.util.List;

public class CellRecieptTextSupplier
{
    public List<String> getExcelFriendly(final IRecordEntry recordEntry)
    {
        final List<String> entryList = Lists.newArrayList();
        final RecieptEntry recieptEntry = (RecieptEntry) recordEntry;

        entryList.add(recieptEntry.getDate().toString());
        entryList.add(recieptEntry.getVchNo());
        entryList.add(Double.toString(recieptEntry.getDebit()));
        return entryList;
    }

    public String get(final IRecordEntry recordEntry)
    {
        final RecieptEntry recieptEntry = (RecieptEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(recieptEntry.getDate());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(VCH_NO);
        stringBuilder.append(recieptEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(recieptEntry.getDebit());
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
