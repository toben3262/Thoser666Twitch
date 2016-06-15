package com.Thoser666.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by bek05842 on 15.06.2016.
 */
public class CryptTest
{
    private Crypt crypt;

    @Before
    public void setUp()
    {
        crypt = new Crypt();
    }

    /**
     * Test for Encrypt data
     */
    @Test
    public void testforEncrypt()
    {
        String erg = crypt.encrypt("testosteron");
        assertTrue(erg.equals("E1uVlQYBUrB7k/2D+GHrYA=="));
    }

    /**
     * Test for decrypt data
     */
    @Test
    public void testforDecrypt()
    {
        String erg = crypt.decrypt("jcZyVG7CAfD+FQ9gAkhbfwzRD+tpKxeq8GRh+ub/uuc=");
        assertTrue(erg.equals("Das ist der Text"));
    }
}
