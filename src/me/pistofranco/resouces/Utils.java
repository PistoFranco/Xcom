package me.pistofranco.resouces;

import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import me.pistofranco.api.AABB;
import me.pistofranco.api.Ray;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Utils {

    private MainClass plugin;
    private Teams teams;

    public Utils(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
    }

    public void tpRedPlayers(){
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
    public void tpBluePlayers(){
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

    void teleportPlayers() {
        int iblue = 0,ired = 0;
        for (UUID uuid:teams.getBluePlayers()) {
            Player blue = Bukkit.getPlayer(uuid);
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.blue."+iblue+".world"));
            double x = plugin.getConfig().getDouble("spawn.blue."+iblue+".x");
            double y = plugin.getConfig().getDouble("spawn.blue."+iblue+".y");
            double z = plugin.getConfig().getDouble("spawn.blue."+iblue+".z");
            Location location = new Location(world,x,y,z);
            blue.teleport(location);
            blue.getLocation().setPitch(0f);
            blue.getLocation().setYaw(0f);
            iblue++;
        }
        for (UUID uuid:teams.getRedPlayers()) {
            Player red = Bukkit.getPlayer(uuid);
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.red."+ired+".world"));
            double x = plugin.getConfig().getDouble("spawn.red."+ired+".x");
            double y = plugin.getConfig().getDouble("spawn.red."+ired+".y");
            double z = plugin.getConfig().getDouble("spawn.red."+ired+".z");
            Location location = new Location(world,x,y,z);
            red.teleport(location);
            red.getLocation().setPitch(-90f);
            red.getLocation().setYaw(0f);
            ired++;
        }
    }

    public Player getTargetPlayer(Player player, int max) {
        List<Player> possible = player.getNearbyEntities(max, max, max).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).filter(player::hasLineOfSight).collect(Collectors.toList());
        Ray ray = Ray.from(player);
        double d = -1;
        Player closest = null;
        for (Player player1 : possible) {
            double dis = AABB.from(player1).collidesD(ray, 0, max);
            if (dis != -1) {
                if (dis < d || d == -1) {
                    d = dis;
                    closest = player1;
                }
            }
        }
        return closest;
    }
}
