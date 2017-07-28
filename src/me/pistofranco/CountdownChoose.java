package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class CountdownChoose extends BukkitRunnable{
    int time = 20;
    private MainClass plugin;
    Teams teams;

    public CountdownChoose(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeams();
    }

    @Override
    public void run() {
        if (GameState.getCurrent() == GameState.CHOOSING) {
            if (Bukkit.getOnlinePlayers().size() >= 1) {
                if (time % 10 == 0) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " + ChatColor.AQUA + time);
                }
                if (time < 10 && time > 0) {
                    Bukkit.broadcastMessage(ChatColor.GOLD + "The game starts in: " + ChatColor.AQUA + time);
                }
                if (time == 0) {
                    plugin.stopCountdownChoosing();
                    GameState.setCurrent(GameState.IN_GAME);
                    plugin.getListener().startGame();
                    return;

                }
                time--;
            } else {
                plugin.stopCountdownChoosing();
                plugin.startCountdownChoosing();
            }
        }
    }
}
