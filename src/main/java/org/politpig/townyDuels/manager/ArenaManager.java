package org.politpig.townyDuels.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.politpig.townyDuels.data.ArenaData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    private final File arenasFile;
    private FileConfiguration arenasConfig;
    private final Map<String, ArenaData> arenas = new HashMap<>();

    public ArenaManager(JavaPlugin plugin) {
        arenasFile = new File(plugin.getDataFolder(), "arenas.yml");
        if (!arenasFile.exists()) {
            try {
                arenasFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        arenasConfig = YamlConfiguration.loadConfiguration(arenasFile);
        loadArenas();
    }

    public void createArena(String name, Location pos1, Location pos2, Location corner1, Location corner2) {
        ArenaData arena = new ArenaData(name, pos1 != null ? pos1.getWorld().getName() : null, pos1, pos2, corner1, corner2);
        arenas.put(name, arena);
        saveArena(arena);
    }

    public void setSpawnPoint1(String arenaName, Location location) {
        ArenaData arena = arenas.get(arenaName);
        if (arena != null) {
            arena.setPos1(location);
            saveArena(arena);
        }
    }

    public void setSpawnPoint2(String arenaName, Location location) {
        ArenaData arena = arenas.get(arenaName);
        if (arena != null) {
            arena.setPos2(location);
            saveArena(arena);
        }
    }

    public void removeArena(String name) {
        arenas.remove(name);
        arenasConfig.set("arenas." + name, null);
        saveArenasConfig();
    }

    public ArenaData getArena(String name) {
        return arenas.get(name);
    }

    public Map<String, ArenaData> getArenas() {
        return arenas;
    }

    private void saveArena(ArenaData arena) {
        String path = "arenas." + arena.getName();
        arenasConfig.set(path + ".world", arena.getWorld());
        arenasConfig.set(path + ".pos1", serializeLocation(arena.getPos1()));
        arenasConfig.set(path + ".pos2", serializeLocation(arena.getPos2()));
        arenasConfig.set(path + ".corner1", serializeLocation(arena.getCorner1()));
        arenasConfig.set(path + ".corner2", serializeLocation(arena.getCorner2()));
        saveArenasConfig();
    }

    private void loadArenas() {
        if (arenasConfig.contains("arenas")) {
            for (String name : arenasConfig.getConfigurationSection("arenas").getKeys(false)) {
                String world = arenasConfig.getString("arenas." + name + ".world");
                Location pos1 = deserializeLocation(arenasConfig.getString("arenas." + name + ".pos1"));
                Location pos2 = deserializeLocation(arenasConfig.getString("arenas." + name + ".pos2"));
                Location corner1 = deserializeLocation(arenasConfig.getString("arenas." + name + ".corner1"));
                Location corner2 = deserializeLocation(arenasConfig.getString("arenas." + name + ".corner2"));

                arenas.put(name, new ArenaData(name, world, pos1, pos2, corner1, corner2));
            }
        }
    }

    private void saveArenasConfig() {
        try {
            arenasConfig.save(arenasFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String serializeLocation(Location location) {
        if (location == null) return null;
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ();
    }

    private Location deserializeLocation(String locationString) {
        if (locationString == null) return null;
        String[] parts = locationString.split(",");
        return new Location(Bukkit.getWorld(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
    }
}
