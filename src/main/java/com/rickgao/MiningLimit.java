//By RickGao
package com.rickgao;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import static com.rickgao.PlayerData.*;


public final class MiningLimit extends JavaPlugin {
    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EventPlayerJoin(),this);
        getServer().getPluginManager().registerEvents(new EventBlockBreak(),this);
        getCommand("mining").setExecutor(new CommandMining());
        getCommand("mining").setTabCompleter(new CommandMiningTab());
        loadData();
        FileConfiguration config = getConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                saveData();
            }
        }.runTaskTimerAsynchronously(this,6000L, 12000L);
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : getServer().getOnlinePlayers()) {
                    String playerName = player.getName();
                    if((getWorld(playerName) + config.getInt("world_add")) <= config.getInt("world_max")) {
                        setWorld(playerName, getWorld(playerName) + config.getInt("world_add"));
                    }
                    if(getNether(playerName) + config.getInt("nether_add") <= config.getInt("nether_max")) {
                        setNether(playerName, getNether(playerName) + config.getInt("nether_add"));
                    }
                }
            }
        }.runTaskTimerAsynchronously(this,0,config.getInt("add_period") * 20L);
        Bukkit.getConsoleSender().sendMessage("[MiningLimit] Enabled   By RickGao");
    }
    @Override
    public void onDisable() {
        saveData();
    }

}
