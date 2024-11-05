package org.politpig.townyDuels;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Obj {
    private final JavaPlugin plugin;

    private FileConfiguration config;

    public Obj(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }


    public String getWinnerMessage() {
        return config.getString("duel.messages.duelEndWinner");
    }

    public String getLoserMessage() {
        return config.getString("duel.messages.duelEndLoser");
    }

    public int getTeleportDelay() {
        return config.getInt("duel.teleportDelay", 5);
    }


    public Location getSpawnLocation() {
        String[] spawnCoords = config.getString("duel.spawnLocation").split(",");
        return new Location(
                Bukkit.getWorld(spawnCoords[0]),
                Double.parseDouble(spawnCoords[1]),
                Double.parseDouble(spawnCoords[2]),
                Double.parseDouble(spawnCoords[3])
        );
    }
}
