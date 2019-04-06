package com.spiegel.pojos;

import com.spiegel.interfaces.IRecordEntry;
import java.util.Date;
import java.util.List;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class BankChargeEntry implements IRecordEntry
{
    final private Date date;
    final private String particulars;
    final private String vchType;
    final private String vchNo;
    final private List<String> vchTokens;
    final private double debit;
    final private double credit;
}
