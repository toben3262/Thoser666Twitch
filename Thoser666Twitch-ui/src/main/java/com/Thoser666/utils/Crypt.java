package com.Thoser666.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by bek05842 on 15.06.2016.
 */
public class Crypt
{
    // Das Passwort bzw der Schluesseltext
    String keyStr = "geheim";
    // byte-Array erzeugen
    byte[] key;
    // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
    MessageDigest sha;
    // der fertige Schluessel
    SecretKeySpec secretKeySpec;



    public Crypt()
    {
        try
        {
            key = (keyStr).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        try
        {
            sha = MessageDigest.getInstance("SHA-256");
            key = sha.digest(key);
            // nur die ersten 128 bit nutzen
            key = Arrays.copyOf(key, 16);
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        // der fertige Schluessel
        secretKeySpec = new SecretKeySpec(key, "AES");
    }

    /**
     * encrypt text
     * @param text
     * @return
     */
    public String encrypt(String text)
    {
        // Verschluesseln
        Cipher cipher = null;
        try
        {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        try
        {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        byte[] encrypted = new byte[0];
        try
        {
            encrypted = cipher.doFinal(text.getBytes());
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        }

        // bytes zu Base64-String konvertieren (dient der Lesbarkeit)
        BASE64Encoder myEncoder = new BASE64Encoder();
        String geheim = myEncoder.encode(encrypted);

        // Ergebnis
        return geheim;
    }

    /**
     * decrypt text
     */

    public String decrypt(String text)
    {
        // der verschl. Text
        String geheim = text;
        String erg = null;

// BASE64 String zu Byte-Array konvertieren
        BASE64Decoder myDecoder2 = new BASE64Decoder();
        byte[] crypted2 = new byte[256];
        try
        {
            crypted2 = myDecoder2.decodeBuffer(geheim);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

// Entschluesseln
        Cipher cipher2 = null;
        try
        {
            cipher2 = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        try
        {
            cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec);
        } catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }

        try
        {
            byte[] cipherData2 = cipher2.doFinal(crypted2);

            erg = new String(cipherData2);
        } catch (BadPaddingException e)
        {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }

// Klartext
        return erg;
    }
}
