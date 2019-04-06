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
            if (containsLetter(entry))
            {
                continue;
            }

            vchTokenList.add(entry);
        }
        return vchTokenList;
    }

    private boolean containsLetter(final String name)
    {
        char[] chars = name.toCharArray();

        for (char c : chars)
        {
            if (Character.isLetter(c))
            {
                return true;
            }
        }

        return false;
    }

    final static String DELIMITER = "/";
}
