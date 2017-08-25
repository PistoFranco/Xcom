package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

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
                    tpBluePlayers();
                    tpRedPlayers();
                    return;
                }
                time--;
            } else {
                plugin.stopCountdown();
                plugin.startCountdown();
            }
        }
    }

    private void tpRedPlayers(){
        if (!plugin.getConfig().contains("spawn.red.choose.world")) {
            Bukkit.broadcastMessage(ChatColor.RED + "Choosing phase not enabled! [Red]");
        } else {
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.red.choose.world"));
            double x = plugin.getConfig().getDouble("spawn.red.choose.x");
            double y = plugin.getConfig().getDouble("spawn.red.choose.y");
            double z = plugin.getConfig().getDouble("spawn.red.choose.z");
            Location location = new Location(world, x, y, z);
            for (UUID red : teams.getRedPlayers()) {
                Player player = Bukkit.getPlayer(red);
                player.teleport(location);
            }
        }
    }
    private void tpBluePlayers(){
        if (!plugin.getConfig().contains("spawn.blue.choose.x")) {
            Bukkit.broadcastMessage(ChatColor.RED + "Choosing phase not enabled! [Blue]");
        } else {
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.blue.choose.world"));
            double x = plugin.getConfig().getDouble("spawn.blue.choose.x");
            double y = plugin.getConfig().getDouble("spawn.blue.choose.y");
            double z = plugin.getConfig().getDouble("spawn.blue.choose.z");
            Location location = new Location(world, x, y, z);
            for (UUID blue : teams.getBluePlayers()) {
                Player player = Bukkit.getPlayer(blue);
                player.teleport(location);
            }
        }
    }
}