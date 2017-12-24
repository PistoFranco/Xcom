package me.pistofranco;

import com.sun.javafx.collections.MappingChange;
import me.pistofranco.Habilities.Hability;
import me.pistofranco.Habilities.HabilityManager;
import me.pistofranco.events.*;
import me.pistofranco.resouces.Items;
import me.pistofranco.resouces.RoundManager;
import me.pistofranco.resouces.Utils;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class MainClass extends JavaPlugin {

    private Teams teams;
    public FileConfiguration config;
    public RoundManager roundManager;
    Items items;
    private Map<Player,HabilityManager> habilityManager = new HashMap<>();

    int timeFirstCountdown = 10,timeSecondCountdown = 20;

    public void onEnable() {
        try {
            config = getConfig();
            createConfigurationFile();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        items = new Items(this);
        teams = new Teams(this);
        getCommand("mcrole").setExecutor(new CommandsClass(this));
        roundManager = new RoundManager(this);
        registerEvents();
        startCountdown();
        GameState.setCurrent(GameState.STARTING);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Xcom is enabled");
        World world = Bukkit.getWorld("world");
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRuleValue("doMobSpawn", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setTime(10000);
        new BukkitRunnable() {
            @Override
            public void run() {
                world.setWeatherDuration(0);
            }
        }.runTaskTimer(this, 100L, 100L);
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Xcom is disabled");
    }

    public void startCountdown() {
        Utils utils = new Utils(this);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GameState.getCurrent() == GameState.STARTING) {
                    if (Bukkit.getOnlinePlayers().size() >= 1) {
                        if (timeFirstCountdown % 10 == 0) {
                            Bukkit.broadcastMessage(ChatColor.GOLD + "Choosing phase starts in: " + ChatColor.AQUA + timeFirstCountdown);
                        }
                        if (timeFirstCountdown < 10 && timeFirstCountdown > 0) {
                            Bukkit.broadcastMessage(ChatColor.GOLD + "Choosing phase starts in: " + ChatColor.AQUA + timeFirstCountdown);
                        }
                        if (timeFirstCountdown == 0) {
                            cancel();
                            GameState.setCurrent(GameState.CHOOSING);
                            utils.tpBluePlayers();
                            utils.tpRedPlayers();
                            startSecondCountdwn();
                            for(Player player : Bukkit.getOnlinePlayers()){
                                addHabilityManger(player);
                            }
                            return;
                        }
                        timeFirstCountdown--;
                    }else {
                        cancel();
                        startCountdown();
                    }
                }
            }
        }.runTaskTimer(this,20L,20L);
    }

    public void startSecondCountdwn() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GameState.getCurrent() == GameState.CHOOSING) {
                    if (Bukkit.getOnlinePlayers().size() >= 1) {
                        if (timeSecondCountdown % 10 == 0) {
                            Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " + ChatColor.AQUA + timeSecondCountdown);
                            for (Player inside : Bukkit.getOnlinePlayers()) {
                                inside.playSound(inside.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 1f);
                            }
                        }
                        if (timeSecondCountdown < 10 && timeSecondCountdown > 0) {
                            Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " + ChatColor.AQUA + timeSecondCountdown);
                            for (Player inside : Bukkit.getOnlinePlayers()) {
                                inside.playSound(inside.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, 1f, 1f);
                            }
                        }
                        if (timeSecondCountdown == 0) {
                            cancel();
                            GameState.setCurrent(GameState.IN_GAME);
                            roundManager.startGame();
                            return;

                        }
                        timeSecondCountdown--;
                    } else {
                        cancel();
                        startSecondCountdwn();
                    }
                }
            }
        }.runTaskTimer(this,20L,20L);
    }


    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new ChooserPhase(this), this);
        pm.registerEvents(new onWalk(this),this);
        pm.registerEvents(new onDeath(this),this);
        pm.registerEvents(new onInteract(this),this);
        pm.registerEvents(new onJoin(),this);
        pm.registerEvents(new onShoot(this),this);
        pm.registerEvents(new onQuit(this),this);
        pm.registerEvents(new onDamagePlayer(this),this);
    }


    private void createConfigurationFile() {
        if(!config.contains("settings.movements")) {
            config.addDefault("settings.movements", 20);
        }
        if(!config.contains("settings.movements.bow")) {
            config.addDefault("settings.movements.bow", 5);
        }
        if(!config.contains("settings.movements.sword_stone")) {
            config.addDefault("settings.movements.sword_stone", 4);
        }
        if(!config.contains("settings.movements.sword_iron")) {
            config.addDefault("settings.movements.sword_iron", 5);
        }
        if(!config.contains("settings.movements.sword_diamond")) {
            config.addDefault("settings.movements.sword_diamond", 7);
        }
        if(!config.contains("settings.movements.starting_movements")){
            config.addDefault("settings.movements.starting_movements",20);
        }
        if(!config.contains("items.wand.seconds")){
            config.addDefault("items.wand.seconds",10);
        }
        if(!config.contains("items.wand.movements")){
            config.addDefault("items.wand.movements",4);
        }
        if(!config.contains("items.marksman_bow.movements")){
            config.addDefault("items.marksman_bow.movements",5);
        }

        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Config.yml loaded correctly");
    }

    //LocalGetters for classes

    public RoundManager getRoundManagerClass() {
        return roundManager;
    }
    public Teams getTeamsClass() {
        return teams;
    }

    //HABILITY MANAGER

    public void addHabilityManger(Player player){
        habilityManager.put(player,new HabilityManager(player));
    }
    public void removeHabilityManger(Player player){
        habilityManager.remove(player,habilityManager.get(player));
    }
    public HabilityManager getHabilityManager(Player player){
        for(Map.Entry<Player,HabilityManager> entry : habilityManager.entrySet()){
            if(entry.getKey() == player){
                return entry.getValue();
            }
        }
        return null;
    }
}