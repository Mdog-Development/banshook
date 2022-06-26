package com.mdogdevelopment.banshook.spigot;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Banshook extends JavaPlugin {
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.saveDefaultConfig();
        } else {
            config = this.getConfig();
        }
        getCommand("banshook-reload").setExecutor(new reloadCommand());

        new punishEvents().registerEvents();
        new unpunishEvents().registerEvents();

        Bukkit.getLogger().info("Banshook is enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Banshook is disabled!");
    }
}
