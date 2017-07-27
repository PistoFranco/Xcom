package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class MainClass extends JavaPlugin {
    private Teams teams;
    private ListenersClass listeners;
    private int taskID;

    public void onEnable() {
        listeners = new ListenersClass(this);
        teams = new Teams();
        Bukkit.getPluginManager().registerEvents(listeners,this);
        startCountdown();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Xcom is enabled");
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Xcom is disabled");
    }

    public void startCountdown(){
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new Countdown(this),20L,20L);
    }

    public void stopCountdown(){
        Bukkit.getServer().getScheduler().cancelTask(taskID);
    }

    public ListenersClass getListener() {
        return listeners;
    }
    public Teams getTeams(){
        return teams;
    }
}
