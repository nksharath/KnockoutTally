package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BankChargeEntry;
import java.util.List;

public class CellBankChargesTextSupplier
{
    public List<String> getExcelFriendly(final IRecordEntry recordEntry)
    {
        final List<String> entryList = Lists.newArrayList();
        final BankChargeEntry bankChargeEntry = (BankChargeEntry) recordEntry;

        entryList.add(bankChargeEntry.getVchNo());
        entryList.add(Double.toString(bankChargeEntry.getDebit()));
        return entryList;
    }

    public String get(final IRecordEntry recordEntry)
    {
        final BankChargeEntry bankChargeEntry = (BankChargeEntry) recordEntry;
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(bankChargeEntry.getDate());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(bankChargeEntry.getParticulars());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(VCH_NO);
        stringBuilder.append(bankChargeEntry.getVchNo());
        stringBuilder.append(NEW_LINE);

        stringBuilder.append(AMOUNT);
        stringBuilder.append(bankChargeEntry.getDebit());
        stringBuilder.append(NEW_LINE);

        return stringBuilder.toString();
    }

    private static String NEW_LINE = "\n";
    private static String VCH_NO = "VchNo = ";
    private static String AMOUNT = "Amount = ";
}
