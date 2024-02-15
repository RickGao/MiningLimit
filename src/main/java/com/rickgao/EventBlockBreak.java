//By RickGao
package com.rickgao;


import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import static com.rickgao.BlockChecker.isIronVein;
import static com.rickgao.BlockChecker.onSurface;
import static com.rickgao.PlayerData.*;
import static org.bukkit.Bukkit.getServer;


public class EventBlockBreak implements Listener {
    FileConfiguration config = MiningLimit.getPlugin(MiningLimit.class).getConfig();
    @EventHandler
    public void breakBlock(BlockBreakEvent eventBlockBreak) {
        if(eventBlockBreak.isCancelled()) {
            return;
        }
        Player player = eventBlockBreak.getPlayer();
        if (player.hasPermission("mining.bypass")) {
            return;
        }
        //World
        if (eventBlockBreak.getBlock().getWorld().getEnvironment() == World.Environment.NORMAL) {
            Location blockPos = eventBlockBreak.getBlock().getLocation();
            if (blockPos.getBlockY() > config.getInt("world_height")) {
                return;
            }
            if(hasCoreProtectRecord(eventBlockBreak.getBlock())) {
                return;
            }
            Material blockType = eventBlockBreak.getBlock().getType();
            String playerName = player.getName();
            int point = getWorld(playerName);
            if (blockType == Material.STONE || blockType == Material.GRANITE || blockType == Material.DIORITE || blockType == Material.ANDESITE || blockType == Material.DEEPSLATE || blockType == Material.TUFF) {
                if (point < config.getInt("world_max")) {
                    setWorld(playerName, point + 1);
                }
            }
            else if (blockType == Material.IRON_ORE) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("iron") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                }
                else {
                    setWorld(playerName, point - config.getInt("iron"));
                }
            }
            else if (blockType == Material.DEEPSLATE_IRON_ORE || blockType == Material.RAW_IRON_BLOCK) {
                if(onSurface(blockPos)) {
                    return;
                }
                if(isIronVein(blockPos)) {
                    if (point - config.getInt("iron_vein") < 0) {
                        eventBlockBreak.setCancelled(true);
                        sendWarning(player);
                    }
                    else {
                        setWorld(playerName, point - config.getInt("iron_vein"));
                    }
                }
                else {
                    if (point - config.getInt("iron") < 0) {
                        eventBlockBreak.setCancelled(true);
                        sendWarning(player);
                    }
                    else {
                        setWorld(playerName, point - config.getInt("iron"));
                    }
                }
            }
            else if (blockType == Material.COPPER_ORE || blockType == Material.DEEPSLATE_COPPER_ORE) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("copper") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                } else {
                    setWorld(playerName, point - config.getInt("copper"));
                }
            }
            else if (blockType == Material.GOLD_ORE || blockType == Material.DEEPSLATE_GOLD_ORE) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("gold") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                } else {
                    setWorld(playerName, point - config.getInt("gold"));
                }
            }
            else if (blockType == Material.DIAMOND_ORE || blockType == Material.DEEPSLATE_DIAMOND_ORE) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("diamond") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                } else {
                    setWorld(playerName, point - config.getInt("diamond"));
                }
            }
        }
        //Nether
        else if (eventBlockBreak.getBlock().getWorld().getEnvironment() == World.Environment.NETHER) {
            Location blockPos = eventBlockBreak.getBlock().getLocation();
            if (blockPos.getBlockY() > config.getInt("nether_height")) {
                return;
            }
            if(hasCoreProtectRecord(eventBlockBreak.getBlock())) {
                return;
            }
            Material block = eventBlockBreak.getBlock().getType();
            String playerName = player.getName();
            int point = getNether(playerName);
            if (block == Material.NETHERRACK || block == Material.BLACKSTONE || block == Material.BASALT) {
                if(point < config.getInt("nether_max")) {
                    setNether(playerName, point + 1);
                }
            }
            else if (block == Material.NETHER_GOLD_ORE) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("nether_gold") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                } else {
                    setNether(playerName, point - config.getInt("nether_gold"));
                }
            }
            else if (block == Material.ANCIENT_DEBRIS) {
                if(onSurface(blockPos)) {
                    return;
                }
                if (point - config.getInt("ancient_debris") < 0) {
                    eventBlockBreak.setCancelled(true);
                    sendWarning(player);
                } else {
                    setNether(playerName, point - config.getInt("ancient_debris"));
                }
            }
        }
    }
    private void sendWarning(Player player) {
        player.sendMessage((ChatColor.translateAlternateColorCodes('&', config.getString("warning"))));
    }

    private boolean hasCoreProtectRecord(Block block) {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return false;
        }
        // Check that the API is enabled
        CoreProtectAPI coreProtect = ((CoreProtect) plugin).getAPI();
        if (!coreProtect.isEnabled()) {
            return false;
        }
        return !coreProtect.blockLookup(block, 31536000).isEmpty();
    }
}
