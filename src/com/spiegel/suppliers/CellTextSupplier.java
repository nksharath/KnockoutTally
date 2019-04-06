package com.spiegel.suppliers;

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

    public String get(final List<IRecordEntry> recordEntryList)
    {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final IRecordEntry recordEntry : recordEntryList)
        {
            if (recordEntry instanceof RecieptEntry)
            {
                //stringBuilder.append(RECIEPT_ENTRY);
                stringBuilder.append(cellRecieptTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof BangaloreEntry)
            {
                //stringBuilder.append(BANGALORE_ENTRY);
                stringBuilder.append(cellBangaloreTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof HassanEntry)
            {
                //stringBuilder.append(HASSAN_ENTRY);
                stringBuilder.append(cellHassanTextSupplier.get(recordEntry));
            }
            else if (recordEntry instanceof BankChargeEntry)
            {
                //stringBuilder.append(BANK_CHARGES_ENTRY);
                stringBuilder.append(cellBankChargesTextSupplier.get(recordEntry));
            }
        }
        return stringBuilder.toString();
    }

   /* private static String RECIEPT_ENTRY = "****Receipt Entries***\n";
    private static String BANGALORE_ENTRY = "****Bangalore Entries***\n";
    private static String HASSAN_ENTRY = "****Hassan Entries***\n";
    private static String BANK_CHARGES_ENTRY = "****BankCharges Entries***\n";*/

    private final CellRecieptTextSupplier cellRecieptTextSupplier;
    private final CellBangaloreTextSupplier cellBangaloreTextSupplier;
    private final CellHassanTextSupplier cellHassanTextSupplier;
    private final CellBankChargesTextSupplier cellBankChargesTextSupplier;
}
