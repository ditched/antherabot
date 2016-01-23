package me.imsean.antherabot.commands;

import in.kyle.ezskypeezlife.api.obj.SkypeConversation;
import in.kyle.ezskypeezlife.api.obj.SkypeMessage;
import in.kyle.ezskypeezlife.api.obj.SkypeUser;
import me.imsean.antherabot.Main;
import me.imsean.antherabot.library.command.Command;
import me.imsean.antherabot.models.Listing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by sean on 12/24/15.
 */
public class ListingCommand extends Command {

    private Listing listingModel;
    private Main bot;

    public ListingCommand(Main bot) {
        super("Displays a list of all current server listings Anthera is on",
                "!listing (add|remove|edit)", "listing", "listings");

        this.bot = bot;
        this.listingModel = new Listing(this.bot);
    }

    @Override
    public void onCommand(SkypeMessage message, SkypeConversation group, SkypeUser sender, String[] args) {
        if(args.length == 0) {
            SkypeMessage listingsMessage = group.sendMessage("Retrieving listings...");

            StringBuilder listings = new StringBuilder();
            listings.append("Showing all minecraft server listings for Anthera (format is id. listing) - ").append(System.lineSeparator());
            this.listingModel.getAll().forEach((id, listing) -> listings.append(id).append(". ").append(listing).append(System.lineSeparator()));
            listingsMessage.edit(listings.toString());
        }
        if(args.length > 0) {
            if(args[0].equalsIgnoreCase("add")) {
                String html = "";
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                for(String arg : newArgs) {
                    html += arg + " ";
                }
                Document doc = Jsoup.parse(html);
                Element anchor = doc.select("a").first();
                String listing;
                try {
                    listing = anchor.attr("href");
                } catch (NullPointerException e) {
                    group.sendMessage(sender.getUsername() + " - Invalid URL");
                    return;
                }
                try {
                    new URL(listing);
                    this.listingModel.add(listing);
                    group.sendMessage(sender.getUsername() + " - Listing successfully added!");
                } catch (MalformedURLException e) {
                    group.sendMessage("Invalid URL");
                }
            }
            if(args[0].equalsIgnoreCase("remove")) {
                int listingID;
                try {
                    listingID = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    group.sendMessage("Listing ID must be a valid integer");
                    return;
                }
                if(!this.listingModel.exists(listingID)) {
                    group.sendMessage(sender.getUsername() + " - That listing does not exist!");
                    return;
                }
                this.listingModel.remove(listingID);
                group.sendMessage(sender.getUsername() + " - Listing successfully removed");
            }
            if(args[0].equalsIgnoreCase("edit")) {
                if(args.length < 2) {
                    group.sendMessage(this.getUsage());
                    return;
                }
                String html = "";
                String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
                for(String arg : newArgs) {
                    html += arg + " ";
                }
                Document doc = Jsoup.parse(html);
                Element anchor = doc.select("a").first();

                String listing;
                int listingID = 0;
                try {
                    listingID = Integer.parseInt(args[1]);
                    if(!this.listingModel.exists(listingID)) {
                        group.sendMessage(sender.getUsername() + " - That listing does not exist!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    group.sendMessage(sender.getUsername() + " - ID must be a valid integer");
                    return;
                }
                try {
                    listing = anchor.attr("href");
                } catch (NullPointerException e) {
                    group.sendMessage(sender.getUsername() + " - Invalid URL");
                    return;
                }
                try {
                    new URL(listing);
                    this.listingModel.edit(listingID, listing);
                    group.sendMessage(sender.getUsername() + " - " + "Listing successfully edited!");
                } catch (MalformedURLException e) {
                    group.sendMessage("Invalid URL");
                }
            }
        }
    }

}
