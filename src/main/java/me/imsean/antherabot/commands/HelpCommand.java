package me.imsean.antherabot.commands;

import in.kyle.ezskypeezlife.Chat;
import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;
import me.imsean.antherabot.library.command.Command;
import me.imsean.antherabot.library.command.CommandHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sean on 12/24/15.
 */
public class HelpCommand extends Command {

    private CommandHandler cmdHandler;

    public HelpCommand(CommandHandler cmdHandler) {
        super("Brings up this help page", "!help", "help");

        this.cmdHandler = cmdHandler;
    }

    @Override
    public void onCommand(SkypeMessage message, SkypeConversation group, SkypeUser sender, String[] args) {
        if(args.length == 0) {
            StringBuilder helpMessage = new StringBuilder();

            this.cmdHandler.getCommands().forEach((command) -> helpMessage.append("!").append(command.getNames()[0]).append(" - ").append(command.getDescription()).append(System.lineSeparator()));

            group.sendMessage(helpMessage.toString());
        }

        if(args.length > 0) {
            String askCommand = args[0].replace("!", "");

            this.cmdHandler.getCommands().forEach((cmd) -> {
                for(String command : cmd.getNames()) {
                    if(command.equalsIgnoreCase(askCommand)) {
                        StringBuilder helpMessage = new StringBuilder();

                        helpMessage.append("Analysis of command !").append(askCommand).append(" - ").append(System.lineSeparator());
                        helpMessage.append("Aliases: ").append(StringUtils.join(cmd.getNames(), ", ")).append(System.lineSeparator());

                        helpMessage.append("Usage: ").append(Chat.bold(command)).append(" - ").append(cmd.getUsage());

                        group.sendMessage(helpMessage.toString());
                        break;
                    }
                }
            });
        }
    }

}
