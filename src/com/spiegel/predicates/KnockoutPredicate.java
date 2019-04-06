package com.spiegel.predicates;

import com.google.inject.Inject;

import com.spiegel.interfaces.IRecordEntry;
import com.spiegel.suppliers.SumSupplier;
import java.util.List;

public class KnockoutPredicate
{
    @Inject
    public KnockoutPredicate(final SumSupplier sumSupplier)
    {
        this.sumSupplier = sumSupplier;
    }

    public boolean test(final List<IRecordEntry> recieptValueList,
                        final List<IRecordEntry> bangaloreValueList,
                        final List<IRecordEntry> hassanValueList,
                        final List<IRecordEntry> bankChargesValueList)
    {
        double recieptSum = sumSupplier.get(recieptValueList);
        double bangaloreSum = sumSupplier.get(bangaloreValueList);
        double hassanSum = sumSupplier.get(hassanValueList);
        double bankChargesSum = sumSupplier.get(bankChargesValueList);

        if (Math.abs((int) recieptSum) == Math.abs((int) (bangaloreSum + hassanSum - bankChargesSum)))
        {
            return true;
        }

        return false;
    }

    private SumSupplier sumSupplier;
}
