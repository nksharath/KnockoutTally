package com.spiegel.main;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.spiegel.guice.BeanModule;
import com.spiegel.logic.MasterController;

public class MainRunner
{
    public static void main(String[] args)
    {
        Injector injector = Guice.createInjector(new BeanModule());
        injector.getInstance(MasterController.class).masterRead(args[0]);
    }
}
