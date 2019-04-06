package com.spiegel.logic;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.pojos.TalliedEntry;
import com.spiegel.pojos.UntalliedEntry;
import com.spiegel.predicates.KnockoutPredicate;
import com.spiegel.suppliers.VchMapSupplier;
import java.util.List;
import java.util.Map;

public class KnockoutLogic
{
    @Inject
    public KnockoutLogic(final VchMapSupplier vchMapSupplier,
                         final KnockoutPredicate knockoutPredicate)
    {
        this.vchMapSupplier = vchMapSupplier;
        this.knockoutPredicate = knockoutPredicate;
    }

    public List<ITallyEntry> getTalliedEntries(final List<IRecordEntry> recieptEntryList,
                                               final List<IRecordEntry> bangaloreEntryList,
                                               final List<IRecordEntry> hassanEntryList,
                                               final List<IRecordEntry> bankChargeEntryList)
    {
        final Map<String, List<IRecordEntry>> recieptMap = vchMapSupplier.get(recieptEntryList);
        final Map<String, List<IRecordEntry>> bangaloreMap = vchMapSupplier.get(bangaloreEntryList);
        final Map<String, List<IRecordEntry>> hassanMap = vchMapSupplier.get(hassanEntryList);
        final Map<String, List<IRecordEntry>> bankChargesMap = vchMapSupplier.get(bankChargeEntryList);
        return knockout(recieptMap, bangaloreMap, hassanMap, bankChargesMap);
    }

    private List<ITallyEntry> knockout(final Map<String, List<IRecordEntry>> recieptMap,
                                       final Map<String, List<IRecordEntry>> bangaloreMap,
                                       final Map<String, List<IRecordEntry>> hassanMap,
                                       final Map<String, List<IRecordEntry>> bankChargesMap)
    {
        final List<ITallyEntry> tallyEntryList = Lists.newArrayList();
        int untalliedCount = 0;
        int talliedCount = 0;

        for (final Map.Entry<String, List<IRecordEntry>> recieptEntry : recieptMap.entrySet())
        {
            final String recieptKey = recieptEntry.getKey();
            final List<IRecordEntry> recieptValueList = recieptEntry.getValue();


            final List<IRecordEntry> bangaloreValueList = Lists.newArrayList();
            final List<IRecordEntry> hassanValueList = Lists.newArrayList();
            final List<IRecordEntry> bankChargesValueList = Lists.newArrayList();

            if (recieptKey.split(DELIMITER).length > 1)
            {
                for (String splitEntry : recieptKey.split(DELIMITER))
                {
                    if (bangaloreMap.containsKey(splitEntry))
                    {
                        bangaloreValueList.addAll(bangaloreMap.get(splitEntry));
                    }

                    if (hassanMap.containsKey(splitEntry))
                    {
                        hassanValueList.addAll(hassanMap.get(splitEntry));
                    }
                }

                if (bankChargesMap.containsKey(recieptKey))
                {
                    bankChargesValueList.addAll(bankChargesMap.get(recieptKey));
                }
            }
            else
            {
                if (bangaloreMap.containsKey(recieptKey))
                {
                    bangaloreValueList.addAll(bangaloreMap.get(recieptKey));
                }

                if (hassanMap.containsKey(recieptKey))
                {
                    hassanValueList.addAll(hassanMap.get(recieptKey));
                }

                if (bankChargesMap.containsKey(recieptKey))
                {
                    bankChargesValueList.addAll(bankChargesMap.get(recieptKey));
                }

            }

            if (knockoutPredicate.test(recieptValueList, bangaloreValueList, hassanValueList, bankChargesValueList))
            {
                tallyEntryList.add(new TalliedEntry(recieptValueList, bangaloreValueList, hassanValueList, bankChargesValueList));
                talliedCount++;
            }
            else
            {
                tallyEntryList.add(new UntalliedEntry(recieptValueList, bangaloreValueList, hassanValueList, bankChargesValueList));
                untalliedCount++;
            }
        }
        return tallyEntryList;
    }


    public List<UntalliedEntry> getUntalliedEntries(final List<IRecordEntry> recieptEntryList,
                                                    final List<IRecordEntry> bangaloreEntryList,
                                                    final List<IRecordEntry> hassanEntryList,
                                                    final List<IRecordEntry> bankChargeEntryList)
    {
        return null;
    }

    private final VchMapSupplier vchMapSupplier;
    private final KnockoutPredicate knockoutPredicate;
    private static String DELIMITER = "/";

}
