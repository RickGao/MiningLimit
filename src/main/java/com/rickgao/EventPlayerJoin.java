//By RickGao
package com.rickgao;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import static com.rickgao.PlayerData.*;


public class EventPlayerJoin implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent eventPlayerJoin) {
        String playerName = eventPlayerJoin.getPlayer().getName();
        FileConfiguration config = MiningLimit.getPlugin(MiningLimit.class).getConfig();
        if(!containPlayer(playerName)) {
            setWorld(playerName, config.getInt("world_max"));
            setNether(playerName, config.getInt("nether_max"));
        }
    }
}
