package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.UntalliedEntry;
import java.util.List;

public class UntalliedEntrySupplier
{
    public List<UntalliedEntry> get(final List<ITallyEntry> tallyEntryList)
    {
        final List<UntalliedEntry> untalliedEntryList = Lists.newArrayList();
        for (final ITallyEntry tallyEntry : tallyEntryList)
        {
            if (tallyEntry instanceof UntalliedEntry)
            {
                untalliedEntryList.add((UntalliedEntry) tallyEntry);
            }
        }
        return untalliedEntryList;
    }
}
