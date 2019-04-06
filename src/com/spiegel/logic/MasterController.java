package com.spiegel.logic;

import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.interfaces.ITallyEntry;
import com.spiegel.io.read.BangaloreReader;
import com.spiegel.io.read.BankChargesReader;
import com.spiegel.io.read.HassanReader;
import com.spiegel.io.read.RecieptReader;
import com.spiegel.io.write.ReportWriter;
import java.util.List;

public class MasterController
{
    @Inject
    public MasterController(final RecieptReader recieptReader,
                            final BangaloreReader bangaloreReader,
                            final HassanReader hassanReader,
                            final BankChargesReader bankChargesReader,
                            final ReportWriter reportWriter,
                            final KnockoutLogic knockoutLogic)
    {
        this.recieptReader = recieptReader;
        this.bangaloreReader = bangaloreReader;
        this.hassanReader = hassanReader;
        this.bankChargesReader = bankChargesReader;
        this.reportWriter = reportWriter;
        this.knockoutLogic = knockoutLogic;
    }

    public void masterRead(final String folderPath)
    {
        final List<IRecordEntry> recieptEntryList = recieptReader.readInput(folderPath);
        final List<IRecordEntry> bangaloreEntryList = bangaloreReader.readInput(folderPath);
        final List<IRecordEntry> hassanEntryList = hassanReader.readInput(folderPath);
        final List<IRecordEntry> bankChargeEntryList = bankChargesReader.readInput(folderPath);
        final List<ITallyEntry> tallyResultList = knockoutLogic.getTalliedEntries(recieptEntryList,
                                                                                  bangaloreEntryList,
                                                                                  hassanEntryList,
                                                                                  bankChargeEntryList);
        reportWriter.writeReport(tallyResultList);

    }

    private final RecieptReader recieptReader;
    private final BangaloreReader bangaloreReader;
    private final HassanReader hassanReader;
    private final BankChargesReader bankChargesReader;
    private final ReportWriter reportWriter;
    private final KnockoutLogic knockoutLogic;
}
