package me.imsean.antherabot.models;

import me.imsean.antherabot.Main;
import me.imsean.antherabot.util.MySQLConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sean on 12/25/15.
 */
public class Listing {

    private Main bot;
    private MySQLConnection conn;

    public Listing(Main bot) {
        this.bot = bot;
        this.conn = this.bot.getConnection();
    }

    public Map getAll() {
        Map<Integer, String> listings = new HashMap<>();
        try {
            ResultSet results = this.conn.query("SELECT * FROM `listings`").getStatement().executeQuery();
            while(results.next()) {
                listings.put(results.getInt("id"), results.getString("listing"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

    public void add(String listing) {
        PreparedStatement stmt = this.conn.query("INSERT INTO `listings` (`listing`) VALUES (?)").getStatement();
        try {
            stmt.setString(1, listing.trim());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        PreparedStatement stmt = this.conn.query("DELETE FROM `listings` WHERE `id`=?").getStatement();
        try {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(int id, String listing) {
        PreparedStatement stmt = this.conn.query("UPDATE `listings` SET `listing`=? WHERE `id`=?").getStatement();
        try {
            stmt.setString(1, listing);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(int id) {
        PreparedStatement stmt = this.conn.query("SELECT * FROM `listings` WHERE `id`=?").getStatement();
        try {
            stmt.setInt(1, id);
            ResultSet results = stmt.executeQuery();
            while(results.next()) {
                if(results.last()) return results.getRow() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
