package com.mdogdevelopment.banshook.spigot;

import com.mdogdevelopment.banshook.DiscordWebhook;
import litebans.api.Database;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class punishEvents extends Events.Listener {
    public String getName(String uuid) {
        String query = "SELECT name FROM {history} WHERE uuid=? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement st = Database.get().prepareStatement(query)) {
            st.setString(1, uuid);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                } else {
                    return uuid;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    public void registerEvents() {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                FileConfiguration configuration = Banshook.config;

                String webhookUrl = configuration.getString("webhook-url");
                Boolean banEnabled = configuration.getBoolean("announcements.ban");
                Boolean warnEnabled = configuration.getBoolean("announcements.warn");
                Boolean kickEnabled = configuration.getBoolean("announcements.kick");
                Boolean muteEnabled = configuration.getBoolean("announcements.mute");

                if (entry.getType().equals("ban")) {
                    if (!banEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getReason();
                    String executor = entry.getExecutorName();
                    Long startDate = entry.getDateStart();
                    Boolean isPermanent = entry.isPermanent();
                    String duration = entry.getDurationString();
                    Boolean isIP = entry.isIpban();

                    String name = getName(uuid);

                    Date date = new Date(startDate);

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    if (isPermanent){
                        duration = "Perm";
                    }
                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Banned")
                                .setColor(Color.RED)
                                .addField("Player", "`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
                                .addField("Duration", duration, true)
                                .addField("IP Ban", isIP.toString(), true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (entry.getType().equals("kick")) {
                    if (!kickEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getReason();
                    String executor = entry.getExecutorName();
                    Long startDate = entry.getDateStart();

                    String name = getName(uuid);

                    Date date = new Date(startDate);

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Kicked")
                                .setColor(Color.ORANGE)
                                .addField("Player", "`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (entry.getType().equals("warn")) {
                    if (!warnEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getReason();
                    String executor = entry.getExecutorName();
                    Long startDate = entry.getDateStart();

                    String name = getName(uuid);

                    Date date = new Date(startDate);

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Warned")
                                .setColor(Color.YELLOW)
                                .addField("Player","`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (entry.getType().equals("mute")) {
                    if (!muteEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getReason();
                    String executor = entry.getExecutorName();
                    Long startDate = entry.getDateStart();
                    Boolean isPermanent = entry.isPermanent();
                    String duration = entry.getDurationString();

                    String name = getName(uuid);

                    Date date = new Date(startDate);

                    if (isPermanent){
                        duration = "Perm";
                    }

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Muted")
                                .setColor(Color.PINK)
                                .addField("Player", "`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
                                .addField("Duration", duration, true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
