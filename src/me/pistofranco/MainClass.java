package me.pistofranco;

import me.pistofranco.resouces.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class MainClass extends JavaPlugin {
    private Teams teams;
    private ListenersClass listeners;
    private int taskID;
    private int taskID2;
    public FileConfiguration config;
    Items items;

    public void onEnable() {
        try {
            config = getConfig();
            createConfigurationFile();
        }catch (Exception e1){
            e1.printStackTrace();
        }
        items = new Items(this);
        teams = new Teams(this);
        listeners = new ListenersClass(this);
        Bukkit.getPluginManager().registerEvents(listeners, this);
        Bukkit.getPluginManager().registerEvents(new ChooserPhase(), this);
        getCommand("mcrole").setExecutor(new CommandsClass(this));
        startCountdown();
        GameState.setCurrent(GameState.STARTING);
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Xcom is enabled");
        World world = Bukkit.getWorld("world");
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRuleValue("doMobSpawn","false");
        world.setGameRuleValue("keepInventory","true");
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Xcom is disabled");
    }

    public void startCountdown() {
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Countdown(this), 20L, 20L);
    }

    public void startCountdownChoosing() {
        taskID2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new CountdownChoose(this), 20L, 20L);
    }

    public void stopCountdownChoosing() {
        Bukkit.getServer().getScheduler().cancelTask(taskID2);
    }

    public void stopCountdown() {
        Bukkit.getServer().getScheduler().cancelTask(taskID);
    }

    public ListenersClass getListener() {
        return listeners;
    }

    public Teams getTeams() {
        return teams;
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
}