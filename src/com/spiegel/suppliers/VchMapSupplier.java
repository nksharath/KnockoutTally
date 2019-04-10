package com.spiegel.suppliers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.pojos.BankChargeEntry;
import com.spiegel.pojos.RecieptEntry;
import java.util.List;
import java.util.Map;

public class VchMapSupplier
{
    public Map<String, List<IRecordEntry>> get(final List<IRecordEntry> recordEntryList)
    {
        final Map<String, List<IRecordEntry>> vchMap = Maps.newTreeMap();
        for (final IRecordEntry entry : recordEntryList)
        {
            if (entry instanceof RecieptEntry)
            {
                if (entry.getVchTokens().size() > 1)
                {
                    final RecieptEntry recieptEntry = (RecieptEntry) entry;
                    if (vchMap.containsKey(recieptEntry.getVchNo()))
                    {
                        final List<IRecordEntry> entries = vchMap.get(recieptEntry.getVchNo());
                        entries.add(entry);
                    }
                    else
                    {
                        vchMap.put(((RecieptEntry) entry).getVchNo(), Lists.newArrayList(entry));
                    }
                    continue;
                }
            }
            else if (entry instanceof BankChargeEntry)
            {
                if (entry.getVchTokens().size() > 1)
                {
                    final BankChargeEntry bankChargeEntry = (BankChargeEntry) entry;
                    if (vchMap.containsKey(bankChargeEntry.getVchNo()))
                    {
                        final List<IRecordEntry> entries = vchMap.get(bankChargeEntry.getVchNo());
                        entries.add(entry);
                    }
                    else
                    {
                        vchMap.put(bankChargeEntry.getVchNo(), Lists.newArrayList(entry));
                    }
                    continue;
                }
            }

            final List<String> vchTokens = entry.getVchTokens();
            for (final String vchToken : vchTokens)
            {
                if (vchMap.containsKey(vchToken))
                {
                    vchMap.get(vchToken).add(entry);
                }
                else
                {
                    vchMap.put(vchToken, Lists.newArrayList(entry));
                }
            }
        }

        return vchMap;
    }
}
