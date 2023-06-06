package com.mdogdevelopment.banshook.spigot;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class commandManager implements CommandExecutor {
    final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Banshook");
    FileConfiguration config;

    public commandManager() {
        assert this.plugin != null;

        this.config =this.plugin.getConfig();
    }

    @SuppressWarnings("NullableProblems")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender == null) {
            return false;
        }
        if (command == null) {
            return false;
        }
        if (label == null) {
            return false;
        }

        if (args.length == 0){
            String usage = "&4Usage: /bhook <&6reload&4>";
            usage = ChatColor.translateAlternateColorCodes('&', usage);
            sender.sendMessage(usage);
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("bhook.reload")) {
                sender.sendMessage("You do not have permission");
            } else {
                sender.sendMessage("Reloading config...");

                assert this.plugin != null;
                this.plugin.reloadConfig();
                config = this.plugin.getConfig();
                Banshook.config = config;

                sender.sendMessage("Config reloaded!");
            }
            return true;

        } else {
            return false;
        }
    }
}
