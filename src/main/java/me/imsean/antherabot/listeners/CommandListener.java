package me.imsean.antherabot.listeners;

import in.kyle.ezskypeezlife.events.SkypeEvent;
import in.kyle.ezskypeezlife.events.conversation.SkypeMessageReceivedEvent;
import me.imsean.antherabot.Main;
import me.imsean.antherabot.library.command.CommandHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sean on 12/24/15.
 */
public class CommandListener implements SkypeEvent {

    private final CommandHandler cmdHandler;
    private final Main bot;

    public CommandListener(Main bot) {
        this.bot = bot;
        this.cmdHandler = new CommandHandler(this.bot);
    }

    public void onCommand(SkypeMessageReceivedEvent e) {
        if(e.getMessage().isEdited()) return;
        if(!e.getMessage().getMessage().startsWith(CommandHandler.prefix)) return;
        if(this.bot.getSkype().getLocalUser().getUsername().equalsIgnoreCase(e.getMessage().getSender().getUsername())) return;

        this.cmdHandler.handleCommand(e.getMessage().getConversation(), e.getMessage().getSender(), e.getMessage());

        System.out.println(("[" + StringUtils.abbreviate(e.getMessage().getConversation().getTopic(), 15) + "]") + e.getMessage().getSender().getDisplayName().orElse(" ") + " (" + e.getMessage().getSender().getUsername() + ") : " + e.getMessage().getMessage());
    }

}
