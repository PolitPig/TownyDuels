package org.politpig.townyDuels.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.politpig.townyDuels.data.KitData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {
    private Map<String, KitData> kits = new HashMap<>();
    private File kitFile;
    private FileConfiguration kitConfig;

    public KitManager(JavaPlugin plugin) {
        kitFile = new File(plugin.getDataFolder(), "kits.yml");
        if (!kitFile.exists()) {
            try {
                kitFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        kitConfig = YamlConfiguration.loadConfiguration(kitFile);
        loadKits();
    }

    public Map<String, KitData> getKits() {
        return kits;
    }

    public void saveKit(String name, ItemStack[] inventoryItems, ItemStack[] armorItems, ItemStack offHandItem) {
        KitData kit = new KitData(name, inventoryItems, armorItems, offHandItem);
        kits.put(name, kit);
        saveKitToFile(kit);
    }

    public void removeKit(String name) {
        kits.remove(name);
        kitConfig.set("kits." + name, null);
        try {
            kitConfig.save(kitFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveKitToFile(KitData kit) {
        String path = "kits." + kit.getName();
        kitConfig.set(path + ".inventory", kit.getInventoryItems());
        kitConfig.set(path + ".armor", kit.getArmorItems());
        kitConfig.set(path + ".offhand", kit.getOffHandItem());
        try {
            kitConfig.save(kitFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadKits() {
        if (kitConfig.contains("kits")) {
            for (String key : kitConfig.getConfigurationSection("kits").getKeys(false)) {
                List<ItemStack> inventoryItems = (List<ItemStack>) kitConfig.getList("kits." + key + ".inventory");
                List<ItemStack> armorItems = (List<ItemStack>) kitConfig.getList("kits." + key + ".armor");
                ItemStack offHandItem = kitConfig.getItemStack("kits." + key + ".offhand");

                KitData kit = new KitData(key, inventoryItems.toArray(new ItemStack[0]), armorItems.toArray(new ItemStack[0]), offHandItem);
                kits.put(key, kit);
            }
        }
    }
}
