package me.imsean.antherabot.library.command;

import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;

/**
 * Created by sean on 12/24/15.
 */
public abstract class Command {

    private final String desc;
    private final String usage;
    private final String[] names;

    public Command(String desc, String usage, String... names) {
        this.desc = desc;
        this.usage = usage;
        this.names = names;
    }

    public String getDescription() {
        return this.desc;
    }

    public String getUsage() {
        return this.usage;
    }

    public String[] getNames() {
        return this.names;
    }

    public abstract void onCommand(SkypeMessage message, SkypeConversation group, SkypeUser user, String[] args);

}
