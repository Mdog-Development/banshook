package com.mdogdevelopment.banshook.spigot;

import com.google.common.collect.Lists;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class tabComplete implements TabCompleter {
    @SuppressWarnings("NullableProblems")
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if (sender == null) {
            return null;
        }

        if (alias == null) {
            return null;
        }

        if (args == null) {
            return null;
        }

        if (command.getName().equalsIgnoreCase("bhook")) {
            ArrayList<String> completions = Lists.newArrayList();
            List<String> tabList = Lists.newArrayList();
            if (sender.hasPermission("bhook.reload")) {
                completions.add("reload");
            }

            if (args.length == 1){
                Iterator var7 = completions.iterator();

                while (var7.hasNext()) {
                    String completion = (String)var7.next();
                    if (completion.toLowerCase().startsWith(args[0].toLowerCase())) {
                        tabList.add(completion);
                    }
                }

                return tabList;
            }
        }
        return null;
    }
}
