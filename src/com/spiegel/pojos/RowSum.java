package com.spiegel.pojos;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class RowSum
{
    private final int updatedRow;
    private final double sum;
}
