package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class MainClass extends JavaPlugin {
    private Teams teams;
    private ListenersClass listeners;
    private int taskID;
    private int taskID2;

    public void onEnable() {
        teams = new Teams(this);
        listeners = new ListenersClass(this);
        Bukkit.getPluginManager().registerEvents(listeners,this);
        Bukkit.getPluginManager().registerEvents(new ChooserPhase(),this);
        getCommand("mcrole").setExecutor(new CommandsClass(this));
        startCountdown();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"Xcom is enabled");
        GameState.setCurrent(GameState.STARTING);
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED+"Xcom is disabled");
    }

    public void startCountdown(){
        taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new Countdown(this),20L,20L);
    }
    public void startCountdownChoosing(){
        taskID2 = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,new CountdownChoose(this),20L,20L);
    }
    public void stopCountdownChoosing(){
        Bukkit.getServer().getScheduler().cancelTask(taskID2);
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
