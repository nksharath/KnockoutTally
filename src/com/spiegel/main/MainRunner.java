package com.spiegel.main;

import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;

import com.spiegel.guice.BeanModule;
import com.spiegel.logic.MasterController;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.swing.*;

public class MainRunner
{
    public static void main(String[] args)
    {
        if (validateFiles())
        {
            Injector injector = Guice.createInjector(new BeanModule());
            injector.getInstance(MasterController.class).masterRead(args[0]);
        }
        else
        {
            infoBox("Four Files R.xls, B.xls, H.xls and BC.xls are required to run this program." +
                    " This program will now shutdown", "Required Files " + "To Run Progam Not Found ");
        }
    }

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

    private static boolean validateFiles()
    {
        final File curDir = new File(".");
        final Set<String> fileList = getAllFiles(curDir);
        for (final String file : ALLOWED_FILES)
        {
            if (!fileList.contains(file))
            {
                return false;
            }
        }
        return true;
    }

    private static Set<String> getAllFiles(File curDir)
    {

        final Set<String> fileSet = Sets.newHashSet();
        File[] filesList = curDir.listFiles();
        for (File f : filesList)
        {
            if (f.isFile())
            {
                fileSet.add(f.getName());
            }
        }
        return fileSet;
    }

    private static List<String> ALLOWED_FILES = Arrays.asList("R.xls", "B.xls", "H.xls", "BC.xls");
}
