package org.politpig.townyDuels.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.politpig.townyDuels.manager.KitManager;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CreateKitCommand implements CommandExecutor, TabCompleter {
    private final KitManager kitManager;

    public CreateKitCommand(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эта команда может быть выполнена только игроком.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Использование: /createkit save|remove {название кита}");
            return true;
        }

        String action = args[0];
        String kitName = args[1];

        switch (action.toLowerCase()) {
            case "save":
                ItemStack[] inventoryItems = player.getInventory().getContents();
                ItemStack[] armorItems = player.getInventory().getArmorContents();
                ItemStack offHandItem = player.getInventory().getItemInOffHand();

                if (inventoryItems == null || inventoryItems.length == 0) {
                    player.sendMessage("Ваш инвентарь пуст. Невозможно сохранить пустой кит.");
                } else {
                    kitManager.saveKit(kitName, inventoryItems, armorItems, offHandItem);
                    player.sendMessage("Кит '" + kitName + "' сохранён.");
                }
                break;
            case "remove":
                kitManager.removeKit(kitName);
                player.sendMessage("Кит '" + kitName + "' удалён.");
                break;
            default:
                player.sendMessage("Неверное использование команды. Используйте: save или remove.");
        }

        return true;
    }



    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            return List.of("save", "remove").stream()
                    .filter(subcommand -> subcommand.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                return kitManager.getKits().keySet().stream()
                        .filter(kitName -> kitName.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        }

        if (args.length < 2) {
            return Collections.singletonList("Укажите название кита.");
        }

        return Collections.emptyList();
    }
}
