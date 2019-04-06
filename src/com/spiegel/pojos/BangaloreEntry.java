package com.spiegel.pojos;

import com.spiegel.interfaces.IRecordEntry;
import java.util.List;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class BangaloreEntry implements IRecordEntry
{
    final private String referenceType;
    final private String vchNo;
    final private List<String> vchTokens;
    final private double credit;
    final String creditStr;
}
