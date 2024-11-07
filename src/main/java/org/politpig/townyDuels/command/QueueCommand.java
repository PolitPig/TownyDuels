package org.politpig.townyDuels.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.politpig.townyDuels.data.ArenaData;
import org.politpig.townyDuels.manager.ArenaManager;
import org.politpig.townyDuels.manager.DuelManager;
import org.politpig.townyDuels.manager.KitManager;
import org.politpig.townyDuels.manager.QueueManager;

import java.util.*;

public class QueueCommand implements CommandExecutor, TabCompleter {

    private final ArenaManager arenaManager;
    private final KitManager kitManager;
    private final QueueManager queueManager;
    private final DuelManager duelManager;

    public QueueCommand(ArenaManager arenaManager, KitManager kitManager, QueueManager queueManager, DuelManager duelManager) {
        this.queueManager = queueManager;
        this.arenaManager = arenaManager;
        this.kitManager = kitManager;
        this.duelManager = duelManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда может быть выполнена только игроком.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("Использование: /queue {название кита}");
            return true;
        }


        if (args[0].equalsIgnoreCase("leave")) {
            queueManager.leavecommand(player);
            return true;
        }

        String kitName = args[0];

        if (!kitManager.getKits().containsKey(kitName)) {
            player.sendMessage("Кит с таким именем не существует.");
            return true;
        }

        for (String kit : queueManager.queue.keySet()) {
            if (queueManager.queue.get(kit) == player) {
                player.sendMessage("Вы уже находитесь в очереди с китом " + kit + ".");
                return true;
            }
        }

        if (queueManager.queue.containsKey(kitName)) {
            Player opponent = queueManager.queue.get(kitName);
            queueManager. queue.remove(kitName);

            duelManager.addPlayersToDuel(player, opponent);

            ArenaData selectedArena = queueManager.getRandomAvailableArena();
            if (selectedArena != null) {
                queueManager.teleportPlayersToArena(player, opponent, selectedArena);
                queueManager.giveKitToPlayer(player, kitName);
                queueManager.giveKitToPlayer(opponent, kitName);

                player.sendMessage("Вы были телепортированы на арену.");
                opponent.sendMessage("Вы были телепортированы на арену.");
            } else {
                player.sendMessage("Нет доступных арен.");
                opponent.sendMessage("Нет доступных арен.");
            }
        } else {
            queueManager.queue.put(kitName, player);
            player.sendMessage("Вы добавлены в очередь с китом " + kitName + ".");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            List<String> suggestions = new ArrayList<>(kitManager.getKits().keySet());
            suggestions.add("leave");
            return suggestions;
        }
        return Collections.emptyList();
    }
}