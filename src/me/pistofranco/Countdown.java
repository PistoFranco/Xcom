package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class Countdown extends BukkitRunnable {


    int time = 10;
    private MainClass plugin;
    public Teams teams = plugin.getTeams();

    public Countdown(MainClass plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() >= 1) {
            if (time % 10 == 0) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " +ChatColor.AQUA+ time);
            }
            if (time < 10 && time > 0) {
                Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " +ChatColor.AQUA+ time);
            }
            if (time == 0) {
                plugin.stopCountdown();
                for (Player playr:Bukkit.getOnlinePlayers()) {
                    teams.addRed(playr.getUniqueId());
                }
                GameState.setCurrent(GameState.IN_GAME);
                plugin.getListener().startGame();
                return;

            }
            time--;
        } else {
            plugin.stopCountdown();
            plugin.startCountdown();
        }
    }
}
