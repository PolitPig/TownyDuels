package org.politpig.townyDuels.data;

import org.bukkit.inventory.ItemStack;

public class KitData {
    private String name;
    private ItemStack[] inventoryItems;
    private ItemStack[] armorItems;
    private ItemStack offHandItem;

    public KitData(String name, ItemStack[] inventoryItems, ItemStack[] armorItems, ItemStack offHandItem) {
        this.name = name;
        this.inventoryItems = inventoryItems;
        this.armorItems = armorItems;
        this.offHandItem = offHandItem;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getInventoryItems() {
        return inventoryItems;
    }

    public ItemStack[] getArmorItems() {
        return armorItems;
    }

    public ItemStack getOffHandItem() {
        return offHandItem;
    }
}
