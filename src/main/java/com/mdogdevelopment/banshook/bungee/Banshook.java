package com.mdogdevelopment.banshook.bungee;

import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;

public final class Banshook extends Plugin {
    public static Configuration configuration;
    public static File path;

    @Override
    public void onEnable() {
        this.loadConfig();
        punishEvents pe = new punishEvents();
        pe.registerEvents();
        unpunishEvents upe = new unpunishEvents();
        upe.registerEvents();
        getProxy().getPluginManager().registerCommand(this, new reloadCommand());
        getLogger().info("Banshook loaded");
    }

    public void loadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                try (InputStream is = getResourceAsStream("config.yml");
                     OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
        }

        try {
            Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
            this.configuration = configuration;
            this.path = getDataFolder();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        getLogger().info("Banshook has stopped");
        getLogger().info("Thanks for using Banshook!");
    }
}
