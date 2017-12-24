package me.pistofranco.events;

import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import me.pistofranco.resouces.RoundManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class onDeath implements Listener {

    MainClass plugin;
    Teams teams;

    public onDeath(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
    }

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        RoundManager roundManager = plugin.getRoundManagerClass();
        if (teams.isRed(killed.getUniqueId())) {
            teams.removeRed(killed.getUniqueId());
            if (teams.getRedPlayers().size() == 0) {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.RED + "Red players eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 1f);
                    roundManager.endGame();
                }
            } else {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.RED + killed.getName() + " has been eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1f, 1f);
                    roundManager.endGame();
                }
            }
        } else {
            teams.removeBlue(killed.getUniqueId());
            if (teams.getBluePlayers().size() == 0) {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.BLUE + "Blue players eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 1f);
                }
            } else {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.BLUE + killed.getName() + " has been eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1f, 1f);
                }
            }
        }
    }

}
