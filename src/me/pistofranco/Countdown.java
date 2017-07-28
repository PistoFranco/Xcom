package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class Countdown extends BukkitRunnable {


    int time = 10;
    private MainClass plugin;
    Teams teams;

    public Countdown(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeams();
    }

    @Override
    public void run() {
        if (GameState.getCurrent() == GameState.STARTING) {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                if (time % 10 == 0) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "Choosing phase starts in: " + ChatColor.AQUA + time);
                }
                if (time < 10 && time > 0) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "Choosing phase starts in: " + ChatColor.AQUA + time);
                }
                if (time == 0) {
                    plugin.stopCountdown();
                    plugin.startCountdownChoosing();
                    GameState.setCurrent(GameState.CHOOSING);
                    return;
                }
                time--;
            } else {
                plugin.stopCountdown();
                plugin.startCountdown();
            }
        }
    }
}