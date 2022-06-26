package com.mdogdevelopment.banshook.bungee;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class reloadCommand extends Command {
    public reloadCommand() {
        super(
                "banshook-reload",
                "bhook.reload",
                "breload"
        );
    }
    @Override
    public void execute (CommandSender commandSender, String[] strings ) {
        try {
            File path = Banshook.path;
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(path, "config.yml"));
            Banshook.configuration = configuration;
            commandSender.sendMessage("Reloaded config!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
