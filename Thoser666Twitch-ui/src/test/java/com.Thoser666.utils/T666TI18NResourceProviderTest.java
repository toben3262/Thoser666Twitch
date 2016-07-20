package com.Thoser666.utils;

import com.jain.addon.resource.I18NProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;

import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * Created by bek05842 on 22.06.2016.
 */
public class T666TI18NResourceProviderTest
{
    @Test
    public void instance() throws Exception
    {
        I18NProvider prov =  T666TI18NResourceProvider.instance();
        assertTrue(!(prov == null));
    }

    @Test
    public void getProperty() throws Exception
    {
        I18NProvider prov =  T666TI18NResourceProvider.instance();
        String tt = prov.getText (Locale.forLanguageTag("de"), "loginscreen.username", "message_de.properties");
System.out.println("tt= " + tt);
        assertTrue(!(tt == null));
    }

    @Test
    public void getProperty1() throws Exception
    {

    }

    @Test
    public void getText() throws Exception
    {

    }

    @Test
    public void getTitle() throws Exception
    {

    }

    @Test
    public void getMessage() throws Exception
    {

    }

    @Test
    public void getProperty2() throws Exception
    {

    }

    @Test
    public void getText1() throws Exception
    {

    }

    @Before
    public void setUp() throws Exception
    {

    }

    @After
    public void tearDown() throws Exception
    {

    }

}