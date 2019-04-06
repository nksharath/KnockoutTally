package com.spiegel.interfaces;

import java.util.List;

public interface ITallyEntry
{
    List<IRecordEntry> getRecieptEntry();

    List<IRecordEntry> getBangaloreEntry();

    List<IRecordEntry> getHassanEntry();

    List<IRecordEntry> getBankChargeEntry();
}
