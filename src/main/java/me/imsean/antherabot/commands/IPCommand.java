package me.imsean.antherabot.commands;

import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;
import me.imsean.antherabot.Main;
import me.imsean.antherabot.library.command.Command;

/**
 * Created by sean on 12/27/15.
 */
public class IPCommand extends Command {

    private Main bot;

    // BRB F00D

    public IPCommand(Main bot) {
        super("Display the server IP", "!server", "server", "serverstatus");

        this.bot = bot;
    }

    public void onCommand(SkypeMessage message, SkypeConversation group, SkypeUser sender, String[] args) {
//        if(args.length == 0) {
//
//        }
//        if(args.length > 0) {
//            if(args[0].equalsIgnoreCase(""))
//        }
    }

}
