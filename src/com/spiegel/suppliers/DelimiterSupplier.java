package com.spiegel.suppliers;

import java.util.function.Supplier;

public class DelimiterSupplier implements Supplier<String>
{
    @Override
    public String get()
    {
        if (isWindows())
        {
            return WIN_DELIMITER;
        }

        if (isMac())
        {
            return MAC_DELIMITER;
        }

        throw new RuntimeException("Unable to determine Windows or Mac");
    }

    public static boolean isWindows()
    {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac()
    {

        return (OS.indexOf("mac") >= 0);

    }

    private static final String WIN_DELIMITER = "//";
    private static final String MAC_DELIMITER = "/";
    private static String OS = System.getProperty("os.name").toLowerCase();

}
