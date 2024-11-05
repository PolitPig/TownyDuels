package org.politpig.townyDuels.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.politpig.townyDuels.Obj;
import org.politpig.townyDuels.manager.ArenaManager;
import org.politpig.townyDuels.manager.DuelManager;
import org.politpig.townyDuels.manager.QueueManager;

public class Listener implements org.bukkit.event.Listener {

    private final QueueManager queueManager;
    private final Obj obj;

    private final DuelManager duelManager;
    private final ArenaManager arenaManager;

    public Listener(QueueManager queueManager, Obj obj, ArenaManager arenaManager, DuelManager duelManager) {
        this.duelManager = duelManager;
        this.queueManager = queueManager;
        this.obj = obj;
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player loser = event.getEntity();
        Player winner = duelManager.getOpponent(loser);
        duelManager.endDuel(winner, loser);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player loser = event.getPlayer();
        Player winner = duelManager.getOpponent(loser);
        if (duelManager.isPlayerInDuel(loser)) {
            duelManager.endDuel(loser, winner);
        }
        if (queueManager.isPlayerInQueue(loser)) {
            Player player = event.getPlayer();
            queueManager.leaveQueue(player);
        }
    }
}
