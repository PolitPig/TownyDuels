package org.politpig.townyDuels.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.politpig.townyDuels.Obj;

import java.util.HashMap;
import java.util.Map;

public class DuelManager {

    private final Obj obj;

    public DuelManager(DuelManager duelManager ,Obj obj) {
        this.obj = obj;
    }
    private final Map<Player, Player> playersInDuel = new HashMap<>();

    public void removePlayerFromDuel(Player player) {
        Player opponent = playersInDuel.remove(player);
        if (opponent != null) {
            playersInDuel.remove(opponent);
        }
    }
    public void addPlayersToDuel(Player player1, Player player2) {
        playersInDuel.put(player1, player2);
        playersInDuel.put(player2, player1);
    }
    public Player getOpponent(Player player) {
        return playersInDuel.get(player);
    }
    public boolean isPlayerInDuel(Player player) {
        return playersInDuel.containsKey(player);
    }
    public void endDuel(Player winner, Player loser) {
        winner.sendMessage(ChatColor.translateAlternateColorCodes('&', obj.getWinnerMessage()));
        loser.sendMessage(ChatColor.translateAlternateColorCodes('&', obj.getLoserMessage()));

        loser.setGameMode(GameMode.SPECTATOR);
        loser.setAllowFlight(true);

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("TownyDuels"), () -> {
            winner.teleport(obj.getSpawnLocation());
            loser.teleport(obj.getSpawnLocation());

            loser.setGameMode(GameMode.SURVIVAL);
            loser.setAllowFlight(false);

            removePlayerFromDuel(winner);
            removePlayerFromDuel(loser);
        }, obj.getTeleportDelay() * 20L);
    }
}
