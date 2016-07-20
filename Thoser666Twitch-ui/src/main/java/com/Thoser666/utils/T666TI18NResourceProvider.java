package com.Thoser666.utils;

import com.jain.addon.resource.AbstractI18NResourceProvider;

import java.io.File;
import java.net.URL;
import java.util.Locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.jain.addon.resource.I18NProvider;

/**
 * <code>T666TI18NResourceProvider<code> is the  i18N provider.
 * Is a fork from DefaultI18NResourceProvider
 * @author Steffen Brumm
 * @since 22.6.2016
 * @version 1.0.0
 */

/**
 * Created by bek05842 on 22.06.2016.
 */
@SuppressWarnings("serial")
public class T666TI18NResourceProvider extends AbstractI18NResourceProvider
{
    private Map<String, Properties> propsForLocale;
    private static I18NProvider instance;

    /**
     * Get the instance of the I18N provider from the application
     * @return {@link I18NProvider}
     */
    public static I18NProvider instance () {
        if (instance == null)
            synchronized (T666TI18NResourceProvider.class) {
                if (instance == null) {
                    instance = new T666TI18NResourceProvider();
                }
            }
        return instance;
    }

    private T666TI18NResourceProvider() {
        propsForLocale = new HashMap<String, Properties> ();
        getProperties(null);
    }

    private Properties getProperties(String locale) {
        Properties properties = propsForLocale.get(locale);
        if(properties == null || properties.isEmpty()) {
            String file = System.getProperty("user.dir") + System.getProperty("file.separator") + "messages" + (locale == null ? BLANK : ("_" + locale)) + ".properties";
            properties = new Properties();
            try {
                InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(file);
                if(stream != null) {
                    properties.load(stream);
                    propsForLocale.put(locale, properties);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if(properties == null || properties.isEmpty()) {
                properties = propsForLocale.get(null);
            }
        }
        return properties;
    }

    @Override
    public String getProperty(Locale locale, String key, String defaultValue) {
        Properties properties = getProperties(locale == null ? null : locale.getLanguage());
        if(properties != null) {
            return properties.getProperty(key, defaultValue);
        }
        return defaultValue;
    }
}
