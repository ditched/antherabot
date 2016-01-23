package me.imsean.antherabot.library.command;

import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;
import me.imsean.antherabot.Main;
import me.imsean.antherabot.commands.DefineCommand;
import me.imsean.antherabot.commands.HelpCommand;
import me.imsean.antherabot.commands.ListingCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sean on 12/24/15.
 */
public class CommandHandler {

    public static String prefix = "!";

    private List<Command> commands;
    private Main bot;

    public CommandHandler(Main bot) {
        this.commands = new ArrayList<>();
        this.bot = bot;

        this.loadCommands(
                new HelpCommand(this),
                new DefineCommand(),
                new ListingCommand(this.bot)
        );
    }

    private void loadCommands(Command... commands) {
        Collections.addAll(this.commands, commands);
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public void handleCommand(SkypeConversation group, SkypeUser user, SkypeMessage message) {
        String input = message.getMessage();

        if (!input.startsWith(prefix)) return;

        String noPrefix = input.substring(prefix.length());
        String[] args = noPrefix.split(" ");

        for (Command c : commands) {
            for (String name : c.getNames()) {
                if (name.equalsIgnoreCase(args[0])) {
                    List<String> newArgs = new ArrayList<String>();
                    for (String s : args) if (!args[0].equalsIgnoreCase(s)) newArgs.add(s);
                    String[] arrayArgs = newArgs.toArray(new String[newArgs.size()]);

                    c.onCommand(message, group, user, arrayArgs);
                    break;
                }
            }
        }
    }


}
