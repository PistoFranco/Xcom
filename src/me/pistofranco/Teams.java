package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jordi M on 24/07/2017.
 */
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
            this.red.add(red);
            Bukkit.broadcastMessage(ChatColor.RED + "Size: " + this.red.size());
        }
    }

    public void addBlue(UUID blue) {
        if (this.blue.size() == 4) {
            Player player = Bukkit.getPlayer(blue);
            player.sendMessage(ChatColor.RED + "This team is full");
        } else {
            this.blue.add(blue);
        }
    }

    public void removeRed(UUID red) {
        this.red.remove(red);
    }
    public void removeBlue(UUID blue) {
        this.blue.remove(blue);
    }

    public boolean isBlue(UUID player) {
        for (UUID pl : blue) {
            if (pl == player) {
                return true;
            }
        }
        return false;
    }
    public boolean isRed(UUID player) {
        for (UUID pl : red) {
            if (pl == player) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayerRed(int number) {
        UUID uuid;
        Player pl;
        try {
            if (red.get(number) != null) {
                uuid = red.get(number);
                pl = Bukkit.getPlayer(uuid);
                return pl;
            }
            return null;
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    public Player getPlayerBlue(int number) {
        UUID uuid;
        Player pl;
        try {
            if (blue.get(number) != null) {
                uuid = blue.get(number);
                pl = Bukkit.getPlayer(uuid);
                return pl;
            }
            return null;
        }catch (IndexOutOfBoundsException e){
            return null;
        }
    }
    public UUID getIdBlue(int number) {
        UUID pl;
        try {
            Bukkit.broadcastMessage("Blue team size:" + blue.size());
            if (blue.get(number) != null) {
                pl = blue.get(number);
                return pl;
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            Bukkit.broadcastMessage("Trying to get next player");
            pl = null;
        } catch (StackOverflowError error){
            GameState.setCurrent(GameState.WAITING);
            plugin.startCountdown();
            red.clear();
            blue.clear();
        }
        return null;
    }
    public UUID getIdRed(int number) {
        UUID pl;
        try {
            Bukkit.broadcastMessage("Red team size:" + red.size());
            if (red.get(number) != null) {
                pl = red.get(number);
                return pl;
            }
            return null;
        } catch (IndexOutOfBoundsException e) {
            Bukkit.broadcastMessage("Trying to get next player");
            pl = null;
        } catch (StackOverflowError error){
            //TODO: Start finding another game
        }
        return null;
    }

}
