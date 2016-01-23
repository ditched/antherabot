package me.imsean.antherabot;

import in.kyle.ezskypeezlife.EzSkype;
import in.kyle.ezskypeezlife.events.EventManager;
import in.kyle.ezskypeezlife.events.SkypeEvent;
import me.imsean.antherabot.listeners.CommandListener;
import me.imsean.antherabot.util.Configuration;
import me.imsean.antherabot.util.MySQLConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sean on 12/24/15.
 */
public class Main {

    private EzSkype skype;
    private EventManager skypeEventManager;
    private Configuration config;
    private MySQLConnection conn;

    private boolean isRunning = false;
    private List<SkypeEvent> listeners = new ArrayList<>();

    public Main() {
        this.config = new Configuration();

        this.login();
        this.start();
    }

    public static void main(String[] args) {
        new Main();
    }

    public void login() {
        this.skype = new EzSkype(config.getProperties().getProperty("skype_username"),
                config.getProperties().getProperty("skype_password"));
        try {
            this.skype.login();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Anthera Bot Started.");
    }

    public void start() {
        this.isRunning = true;
        this.skypeEventManager = this.skype.getEventManager();
        this.conn = new MySQLConnection(
                this.config.getProperties().getProperty("database_host"),
                this.config.getProperties().getProperty("database_name"),
                this.config.getProperties().getProperty("database_user"),
                this.config.getProperties().getProperty("database_pass")
        );

        this.listeners.add(new CommandListener(this));
        this.registerListeners();
    }

    public void stop() {
        this.isRunning = false;
        this.skype.logout();

        this.skype = null;
        this.config = null;
        this.skypeEventManager = null;
    }

    public void registerListeners() {
        listeners.forEach(this.skypeEventManager::registerEvents);
    }

    public EzSkype getSkype() {
        return this.skype;
    }

    public MySQLConnection getConnection() { return this.conn; }



}
