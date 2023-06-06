package com.mdogdevelopment.banshook.bungee;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class reloadCommand extends Command {
    public reloadCommand() {
        super(
                "bhook"
        );
    }
    @Override
    public void execute (CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(new ComponentBuilder("Usage: /bhook reload").color(ChatColor.RED).create());
            return;
        }

        if (Objects.equals(args[0], "reload")) {
            if (!commandSender.hasPermission("bhook.reload")) {
                commandSender.sendMessage(new ComponentBuilder("Invalid Permissions").color(ChatColor.RED).create());
                return;
            }
            try {
                File path = Banshook.path;
                Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(path, "config.yml"));
                Banshook.configuration = configuration;
                commandSender.sendMessage(new ComponentBuilder("Config Reloaded...").color(ChatColor.GREEN).create());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
