package com.spiegel.pojos;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import java.util.List;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class UntalliedEntry implements ITallyEntry
{
    private final List<IRecordEntry> recieptEntry;
    private final List<IRecordEntry> bangaloreEntry;
    private final List<IRecordEntry> hassanEntry;
    private final List<IRecordEntry> bankChargeEntry;
}
