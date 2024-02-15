//By RickGao
package com.rickgao;

import org.bukkit.Location;
import org.bukkit.Material;


public class BlockChecker {
    private static final int[] moveX = {1,-1, 0, 0, 0, 0};
    private static final int[] moveY = {0, 0, 1,-1, 0, 0};
    private static final int[] moveZ = {0, 0, 0, 0, 1,-1};
    private static final int[] moveD1 = { 1,1,1, 0,0,0,-1,-1,-1};
    private static final int[] moveD2 = {-1,0,1,-1,0,1,-1, 0, 1};
    public static boolean onSurface(Location pos) {
        int X = pos.getBlockX();
        int Y = pos.getBlockY();
        int Z = pos.getBlockZ();
        for (int i = 0 ; i < 6 ; i++) {
            if (moveY[i] != 0) {
                for (int j = 0 ; j < 9 ; j++) {
                    Location nextPos = new Location(pos.getWorld(),X + moveD1[i],Y + 2 * moveY[i],Z + moveD2[i]);
                    if(nextPos.getBlock().getType() != Material.AIR && nextPos.getBlock().getType() != Material.WATER) {
                        continue;
                    }
                    else if (isOpen(nextPos)) {
                        return true;
                    }
                }
            }
            else if (moveX[i] != 0) {
                for (int j = 0 ; j < 9 ; j++) {
                    Location nextPos = new Location(pos.getWorld(),X + 2 * moveX[i],Y + moveD1[i],Z + moveD2[i]);
                    if (nextPos.getBlock().getType() != Material.AIR && nextPos.getBlock().getType() != Material.WATER) {
                        continue;
                    }
                    else if (isOpen(nextPos)) {
                        return true;
                    }
                }
            }
            else if (moveZ[i] != 0) {
                for (int j = 0 ; j < 9 ; j++) {
                    Location nextPos = new Location(pos.getWorld(),X + moveD1[i],Y + 2*moveD2[i],Z + moveZ[i]);
                    if (nextPos.getBlock().getType() != Material.AIR && nextPos.getBlock().getType() != Material.WATER) {
                        continue;
                    }
                    else if(isOpen(nextPos)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private static boolean isOpen(Location pos) {
        int numAir = 0;
        for(int i = 0 ; i < 6 ; i++) {
            Location nextPos = new Location(pos.getWorld(),pos.getBlockX() + moveX[i], pos.getBlockY() + moveY[i], pos.getBlockZ() + moveZ[i]);
            if(nextPos.getBlock().getType() == Material.AIR || nextPos.getBlock().getType() == Material.WATER) {
                numAir += 1;
            }
        }
        if(numAir < 4){
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean isIronVein(Location pos) {
        for(int i = 0 ; i < 6 ; i++) {
            Location nextPos = new Location(pos.getWorld(),pos.getBlockX() + moveX[i], pos.getBlockY() + moveY[i], pos.getBlockZ() + moveZ[i]);
            if(nextPos.getBlock().getType() == Material.TUFF) {
                return true;
            }
        }
        return false;
    }
}
