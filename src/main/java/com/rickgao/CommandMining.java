//By RickGao
package com.rickgao;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.rickgao.PlayerData.*;


public class CommandMining implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String lable, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player.isOp()) {
                if(args.length == 1) {
                    if (containPlayer(args[0])) {
                        player.sendMessage(ChatColor.GOLD + args[0] + ChatColor.YELLOW + "  World: " + getWorld(args[0]) + "  Nether: " + getNether(args[0]));
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Player not found");
                    }
                }
                else if(args.length ==3) {
                    if (containPlayer(args[0])) {
                        setWorld(args[0], Integer.valueOf(args[1]));
                        setNether(args[0], Integer.valueOf(args[2]));
                        player.sendMessage(ChatColor.YELLOW + "Player data set");
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Player not found");
                    }
                }
            }
            else {
                player.sendMessage(ChatColor.RED + "您没有权限 You do not have permission");
            }
        }
        else {
            if(args.length == 1) {
                if (containPlayer(args[0])) {
                    Bukkit.getConsoleSender().sendMessage("[MiningLimit] " + args[0] + "  World: " + getWorld(args[0]) + "  Nether: " + getNether(args[0]));
                }
                else {
                    Bukkit.getConsoleSender().sendMessage("[MiningLimit] Player not found");
                }
            }
            else if(args.length == 3) {
                if (containPlayer(args[0])) {
                    setWorld(args[0], Integer.valueOf(args[1]));
                    PlayerData.setNether(args[0], Integer.valueOf(args[2]));
                    Bukkit.getConsoleSender().sendMessage("[MiningLimit] Player data set");
                }
                else {
                    Bukkit.getConsoleSender().sendMessage("[MiningLimit] Player not found");
                }
            }
            else {
                Bukkit.getConsoleSender().sendMessage("[MiningLimit] Wrong command");
            }
        }
        return false;
    }
}
