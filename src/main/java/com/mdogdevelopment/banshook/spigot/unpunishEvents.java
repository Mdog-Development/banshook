package com.mdogdevelopment.banshook.spigot;

import com.mdogdevelopment.banshook.DiscordWebhook;
import litebans.api.Database;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class unpunishEvents extends Events.Listener {
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
        Events.get().register(new Events.Listener(){
            @Override
            public void entryRemoved(Entry entry){
                FileConfiguration configuration = Banshook.config;

                String webhookUrl = configuration.getString("webhook-url");
                Boolean unbanEnabled = configuration.getBoolean("announcements.unban");
                Boolean unwarnEnabled = configuration.getBoolean("announcements.unwarn");
                Boolean unmuteEnabled = configuration.getBoolean("announcements.unmute");

                if (entry.getType().equals("ban")){
                    if (!unbanEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getRemovalReason();
                    String executor = entry.getRemovedByName();

                    String name = getName(uuid);

                    LocalDate date = LocalDate.now();

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Unbanned")
                                .setColor(Color.GREEN)
                                .addField("Player", "`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (entry.getType().equals("warn")) {
                    if (!unwarnEnabled) return;
                    String uuid = entry.getUuid();

                    String name = getName(uuid);

                    LocalDate date = LocalDate.now();

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Unwarned")
                                .setColor(Color.GREEN)
                                .addField("Player", "`"+name+"`", true)
                                .setFooter(uuid+" | "+date, null));
                        webhook.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (entry.getType().equals("mute")) {
                    if (!unmuteEnabled) return;
                    String uuid = entry.getUuid();
                    String reason = entry.getRemovalReason();
                    String executor = entry.getRemovedByName();

                    String name = getName(uuid);

                    LocalDate date = LocalDate.now();

                    if (reason.equals("")) {
                        reason = "No reason given";
                    }

                    try {
                        DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
                        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                .setTitle("Member Unmuted")
                                .setColor(Color.GREEN)
                                .addField("Player", "`"+name+"`", true)
                                .addField("Moderator", "`"+executor+"`", true)
                                .addField("Reason", reason, true)
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
