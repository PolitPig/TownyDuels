package org.politpig.townyDuels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.politpig.townyDuels.command.ArenaCreateCommand;
import org.politpig.townyDuels.command.CreateKitCommand;
import org.politpig.townyDuels.command.QueueCommand;
import org.politpig.townyDuels.listener.Listener;
import org.politpig.townyDuels.manager.ArenaManager;
import org.politpig.townyDuels.manager.DuelManager;
import org.politpig.townyDuels.manager.KitManager;
import org.politpig.townyDuels.manager.QueueManager;

public final class TownyDuels extends JavaPlugin {
    private ArenaManager arenaManager;
    private KitManager kitManager;

    private DuelManager duelManager;
    private QueueManager queuemanager;
    private Obj obj;
    @Override
    public void onEnable() {
        Bukkit.getScheduler().runTaskLater(this, this::initializePlugin, 20L);
    }
    @Override
    public void onDisable() {

    }
    private void initializePlugin() {
        saveDefaultConfig();
        obj = new Obj(this);
        kitManager = new KitManager(this);
        arenaManager = new ArenaManager(this);
        duelManager = new DuelManager(duelManager, obj);
        queuemanager = new QueueManager(arenaManager, kitManager, duelManager);

        getCommand("queuea").setExecutor(new QueueCommand(arenaManager, kitManager, queuemanager, duelManager));
        getCommand("createkit").setExecutor(new CreateKitCommand(kitManager));
        getCommand("arenacreate").setExecutor(new ArenaCreateCommand(arenaManager));

        getServer().getPluginManager().registerEvents(new Listener(queuemanager, obj, arenaManager, duelManager), this);
    }
    /*Допишу плагин ливну со сквада
    Спасибо нагито что втянул меня в програмирование*/
}
