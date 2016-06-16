package com.Thoser666.utils;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
    public void testforEncrypt() throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        String erg = crypt.encrypt("testosteron");
        assertTrue(erg.equals("E1uVlQYBUrB7k/2D+GHrYA=="));
    }

    // not necessary right now
//    /**
//     * Test for decrypt data
//     */
//    @Test
//    public void testforDecrypt()
//    {
//        String erg = null;
//        try
//        {
//            erg = crypt.decrypt("jcZyVG7CAfD+FQ9gAkhbfwzRD+tpKxeq8GRh+ub/uuc=");
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e)
//        {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e)
//        {
//            e.printStackTrace();
//        } catch (InvalidKeyException e)
//        {
//            e.printStackTrace();
//        } catch (BadPaddingException e)
//        {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e)
//        {
//            e.printStackTrace();
//        }
//        assertTrue(erg.equals("Das ist der Text"));
//    }
}
