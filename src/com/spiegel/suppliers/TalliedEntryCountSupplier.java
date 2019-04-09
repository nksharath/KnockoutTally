package com.spiegel.suppliers;

import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.TalliedEntry;
import java.util.List;

public class TalliedEntryCountSupplier
{
    public int get(final List<ITallyEntry> tallyEntryList)
    {
        int count = 0;
        for (final ITallyEntry tallyEntry : tallyEntryList)
        {
            if (tallyEntry instanceof TalliedEntry)
            {
                count++;
            }
        }
        return count;
    }
}
