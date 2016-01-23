package me.imsean.antherabot.commands;

import in.kyle.ezskypeezlife.Chat;
import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;
import me.imsean.antherabot.library.command.Command;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by sean on 12/24/15.
 */
public class DefineCommand extends Command {

    public DefineCommand() {
        super("Define a word using google's database", "!define [term]", "define", "google");
    }

    @Override
    public void onCommand(SkypeMessage message, SkypeConversation group, SkypeUser sender, String[] args) {
        if(args.length == 0) {
            group.sendMessage(this.getUsage());
            return;
        }

        if(args.length > 0) {

            StringBuilder term = new StringBuilder();

            for(String arg : args) {
                term.append(arg).append(" ");
            }

            SkypeMessage definition = group.sendMessage("Searching for definition of " + Chat.bold(term.toString().trim()) + "...");

            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.google.com/search?q=define+" + term.toString().trim())
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/45.0.2454.101 Chrome/45.0.2454.101 Safari/537.36")
                        .get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert doc != null;
            Element box = doc.select(".lr_dct_sf_sen.vk_txt").first();
            String rawDef;
            try {
                rawDef = box.select("div[data-dobid=\"dfn\"] span").first().text();
            } catch (NullPointerException e) {
                definition.edit(sender.getUsername() + " - " + "Couldn't define term: " + term.toString());
                return;
            }

            StringBuilder def = new StringBuilder();
            def.append("Definition of ").append(Chat.bold(term.toString().trim())).append(" - ").append(System.lineSeparator()).append(rawDef);
            definition.edit(def.toString());
        }
    }

}
