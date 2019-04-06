package com.spiegel.suppliers;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BangaloreEntry;
import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.HassanEntry;
import com.spiegel.pojos.RecieptEntry;
import java.util.List;

public class CellTextSupplier
{
    @Inject
    public CellTextSupplier(final CellRecieptTextSupplier cellRecieptTextSupplier,
                            final CellBangaloreTextSupplier cellBangaloreTextSupplier,
                            final CellHassanTextSupplier cellHassanTextSupplier,
                            final CellBankChargesTextSupplier cellBankChargesTextSupplier)
    {
        this.cellRecieptTextSupplier = cellRecieptTextSupplier;
        this.cellBangaloreTextSupplier = cellBangaloreTextSupplier;
        this.cellHassanTextSupplier = cellHassanTextSupplier;
        this.cellBankChargesTextSupplier = cellBankChargesTextSupplier;
    }

    public List<String> getExcelFriendly(final List<IRecordEntry> recordEntryList)
    {
        final List<String> entryList = Lists.newArrayList();

        for (final IRecordEntry recordEntry : recordEntryList)
        {
            if (recordEntry instanceof RecieptEntry)
            {
                entryList.addAll(cellRecieptTextSupplier.getExcelFriendly(recordEntry));
            }
            else if (recordEntry instanceof BangaloreEntry)
            {
                entryList.addAll(cellBangaloreTextSupplier.getExcelFriendly(recordEntry));
            }
            else if (recordEntry instanceof HassanEntry)
            {
                entryList.addAll(cellHassanTextSupplier.getExcelFriendly(recordEntry));
            }
            else if (recordEntry instanceof BankChargeEntry)
            {
                entryList.addAll(cellBankChargesTextSupplier.getExcelFriendly(recordEntry));
            }
        }

        return entryList;
    }

    public String get(final List<IRecordEntry> recordEntryList)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final IRecordEntry recordEntry : recordEntryList)
        {
            if (recordEntry instanceof RecieptEntry)
            {
                stringBuilder.append(cellRecieptTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof BangaloreEntry)
            {
                stringBuilder.append(cellBangaloreTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof HassanEntry)
            {
                stringBuilder.append(cellHassanTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof BankChargeEntry)
            {
                stringBuilder.append(cellBankChargesTextSupplier.get(recordEntry));
            }
        }
        return stringBuilder.toString();
    }

    private final CellRecieptTextSupplier cellRecieptTextSupplier;
    private final CellBangaloreTextSupplier cellBangaloreTextSupplier;
    private final CellHassanTextSupplier cellHassanTextSupplier;
    private final CellBankChargesTextSupplier cellBankChargesTextSupplier;
}
