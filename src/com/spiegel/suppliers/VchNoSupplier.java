package com.spiegel.suppliers;

import com.google.common.collect.Lists;

import java.util.List;

public class VchNoSupplier
{
    public List<String> get(final String vchText)
    {
        final String[] splitArray = vchText.split(DELIMITER);
        final List<String> vchTokenList = Lists.newArrayList();

        for (final String entry : splitArray)
        {
            vchTokenList.add(entry);
        }

        return vchTokenList;
    }


    final static String DELIMITER = "/";
}
