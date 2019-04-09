package com.spiegel.suppliers;

import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.UntalliedEntry;
import java.util.List;

public class UntalliedEntryCountSupplier
{
    public int get(final List<ITallyEntry> tallyEntryList)
    {
        int count = 0;
        for (final ITallyEntry tallyEntry : tallyEntryList)
        {
            if (tallyEntry instanceof UntalliedEntry)
            {
                count++;
            }
        }
        return count;
    }
}
