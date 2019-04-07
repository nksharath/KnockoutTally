package com.spiegel.suppliers;

import java.util.List;

public class MaxNumberSupplier
{
    public int get(final List<Integer> numberList)
    {
        int max = -1;
        for (final Integer number : numberList)
        {
            if (number > max)
            {
                max = number;
            }
        }

        return max;
    }
}
