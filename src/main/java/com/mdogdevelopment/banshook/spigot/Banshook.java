package com.mdogdevelopment.banshook.spigot;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Banshook extends JavaPlugin implements Listener {
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        config = this.getConfig();

        if (Bukkit.getPluginManager().getPlugin("LiteBans") != null){
            Bukkit.getLogger().info("Enabling Banshook!");
            Bukkit.getPluginManager().registerEvents(this, this);
        } else {
            Bukkit.getLogger().info("Litebans not found");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        Objects.requireNonNull(this.getCommand("bhook")).setExecutor(new commandManager());
        Objects.requireNonNull(this.getCommand("bhook")).setTabCompleter(new tabComplete());
        new punishEvents().registerEvents();
        new unpunishEvents().registerEvents();

        Bukkit.getLogger().info("Banshook is enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Banshook is disabled!");
    }
}
