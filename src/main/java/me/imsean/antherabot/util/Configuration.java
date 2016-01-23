package me.imsean.antherabot.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by sean on 12/24/15.
 */
public class Configuration {

    private Properties properties;
    private InputStream inputStream;
    private ClassLoader classLoader;

    public Configuration() {
        this.properties = new Properties();
        this.classLoader = getClass().getClassLoader();
        this.loadProperties();
    }

    private void loadProperties() {
        File file = new File("bot.properties");
        if(!file.exists()) {
            System.out.println("Bot configuration not present, using packed one.");
            this.inputStream = this.classLoader.getResourceAsStream("bot.properties");
            try {
                this.properties.load(this.inputStream);
            } catch (IOException e) {
                System.out.println("Failed to load packed properties");
            }
            return;
        }

        try {
            this.inputStream = new FileInputStream("bot.properties");
            this.properties.load(this.inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find bot properties");
        } catch (IOException e) {
            System.out.println("Couldn't read bot properties");
        }
    }

    public Properties getProperties() {
        return this.properties;
    }


}
