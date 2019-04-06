package com.spiegel.suppliers;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.RecieptEntry;

public class CellRecieptTextSupplier
{
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

        return stringBuilder.toString();
    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
