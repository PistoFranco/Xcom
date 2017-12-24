package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Teams {
    MainClass plugin;

    public Teams(MainClass plugin) {
        this.plugin = plugin;
    }

    private List<UUID> red = new ArrayList<>(4);
    private List<UUID> blue = new ArrayList<>(4);


    public void addRed(UUID red) {
        if (this.red.size() == 4) {
            Player player = Bukkit.getPlayer(red);
            player.sendMessage(ChatColor.RED + "This team is full");
        } else {
            if (blue.contains(red)) {
                Player player = Bukkit.getPlayer(red);
                this.blue.remove(red);
                this.red.add(red);
                player.sendMessage(this.blue.size()+"");
            }else this.red.add(red);
        }
    }

    public void addBlue(UUID blue) {
        if (this.blue.size() == 4) {
            Player player = Bukkit.getPlayer(blue);
            player.sendMessage(ChatColor.RED + "This team is full");
        } else {
            if(red.contains(blue)){
                Player player = Bukkit.getPlayer(blue);
                this.red.remove(blue);
                this.blue.add(blue);
                player.sendMessage(this.blue.size()+"");
            }else this.blue.add(blue);
        }
    }

    public void removeRed(UUID red) {
        this.red.remove(red);
    }
    public void removeBlue(UUID blue) {
        this.blue.remove(blue);
    }

    public boolean isRed(UUID player) {
        return red.contains(player);
    }
    public boolean isBlue(UUID player) {
        return blue.contains(player);
    }
    public List<UUID> getRedPlayers(){
        return red;
    }

    public List<UUID> getBluePlayers(){
        return blue;
    }


    public UUID getIdBlue(int number) {
        UUID pl;
        try {
            if (blue.get(number) != null) {
                pl = blue.get(number);
                return pl;
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            pl = null;
        }
        return null;
    }

    public UUID getIdRed(int number) {
        UUID pl;
        try {
            if (red.get(number) != null) {
                pl = red.get(number);
                return pl;
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            pl = null;
        }
        return null;
    }

    public boolean hasTeam(UUID player) {
        if (isRed(player))
            return true;
        if(isBlue(player))
            return true;
        return false;
    }
}