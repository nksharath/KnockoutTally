package com.spiegel.suppliers;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BangaloreEntry;
import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.HassanEntry;
import com.spiegel.pojos.RecieptEntry;
import java.util.List;

public class SumSupplier
{
    public double get(final List<IRecordEntry> valueList)
    {
        double sum = 0.0D;
        for (final IRecordEntry recordEntry : valueList)
        {
            if (recordEntry instanceof RecieptEntry)
            {
                sum += ((RecieptEntry) recordEntry).getDebit();
            }

            else if (recordEntry instanceof BankChargeEntry)
            {
                sum += ((BankChargeEntry) recordEntry).getDebit();
            }

            else if (recordEntry instanceof BangaloreEntry)
            {
                sum += ((BangaloreEntry) recordEntry).getCredit();
            }

            else if (recordEntry instanceof HassanEntry)
            {
                sum += ((HassanEntry) recordEntry).getCredit();
            }
        }

        return sum;
    }
}
