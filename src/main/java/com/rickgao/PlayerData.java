//By RickGao
package com.rickgao;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;


public class PlayerData {
    private static File file;
    private static FileConfiguration playerData;
    public static void loadData() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MiningLimit").getDataFolder(), "playerdata.yml");
        if(!file.exists()) {
            try {
                file.createNewFile();
            }catch (IOException e) {
                Bukkit.getConsoleSender().sendMessage("[MiningLimit] File error");
            }
        }
        playerData = YamlConfiguration.loadConfiguration(file);
    }
    public static Integer getWorld(String player) {
        return playerData.getInt(player + ".world");
    }
    public static Integer getNether(String player) {
        return playerData.getInt(player + ".nether");
    }
    public static void setWorld(String player, Integer value) {
        playerData.set(player + ".world", value);
    }
    public static void setNether(String player, Integer value) {
        playerData.set(player + ".nether", value);
    }
    public static boolean containPlayer(String player) {
        return playerData.contains(player);
    }
    public static void saveData() {
        try{
            playerData.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("[MiningLimit] Cannot save");
        }
    }

//    public static FileConfiguration getData() {
//        return playerData;
//    }
//    public static void reloadData() {
//        playerData = YamlConfiguration.loadConfiguration(file);
//    }
}
