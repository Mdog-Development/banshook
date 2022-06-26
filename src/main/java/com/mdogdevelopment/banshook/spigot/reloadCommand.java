package com.mdogdevelopment.banshook.spigot;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("Reloading Banshook...");
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Banshook");
        Banshook.config = plugin.getConfig();
        sender.sendMessage("Banshook reloaded!");
        return true;
    }
}
