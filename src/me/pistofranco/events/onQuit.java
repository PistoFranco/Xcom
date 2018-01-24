package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onQuit implements Listener {

    MainClass plugin;
    Teams teams;

    public onQuit(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
    }

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (teams.isRed(player.getUniqueId())) {
                teams.removeRed(player.getUniqueId());
                //plugin.removeHabilityManger(player);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendTitle("", ChatColor.RED + player.getName() + ChatColor.AQUA + " has disconnected!");
                }
            } else {
                teams.removeBlue(player.getUniqueId());
                //plugin.removeHabilityManger(player);
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendTitle("", ChatColor.BLUE + player.getName() + ChatColor.AQUA + " has disconnected!");
                }
            }
        }
    }
}
