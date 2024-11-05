package org.politpig.townyDuels.manager;

import org.bukkit.entity.Player;
import org.politpig.townyDuels.data.ArenaData;
import org.politpig.townyDuels.data.KitData;

import java.util.*;

public class QueueManager {

    private final Map<String, Player> queue = new HashMap<>();
    private final ArenaManager arenaManager;
    private final KitManager kitManager;
    public QueueManager(ArenaManager arenaManager, KitManager kitManager) {
        this.arenaManager = arenaManager;
        this.kitManager = kitManager;
    }

    public void teleportPlayersToArena(Player player1, Player player2, ArenaData data) {
        player1.teleport(data.getPos1());
        player2.teleport(data.getPos2());
    }

    public void giveKitToPlayer(Player player, String kitName) {
        KitData kit = kitManager.getKits().get(kitName);

        player.getInventory().clear();

        player.getInventory().setContents(kit.getInventoryItems());

        player.getInventory().setArmorContents(kit.getArmorItems());

        player.getInventory().setItemInOffHand(kit.getOffHandItem());
    }

    public boolean isPlayerInQueue(Player player) {
        return queue.containsValue(player);
    }
    public ArenaData getRandomAvailableArena() {
        if (arenaManager.getArenas().isEmpty()) {
            return null;
        }

        List<ArenaData> availableArenas = arenaManager.getArenas().values().stream()
                .filter(ArenaData::hasSpawns)
                .toList();

        if (availableArenas.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return availableArenas.get(random.nextInt(availableArenas.size()));
    }
        public void leaveQueue(Player player) {
        queue.remove(player.getName());
    }
}
