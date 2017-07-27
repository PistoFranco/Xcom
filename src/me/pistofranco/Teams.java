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

    private List<UUID> red = new ArrayList<>(4);
    private List<UUID> blue = new ArrayList<>(4);


    public void addRed(UUID red) {
        if (this.red.size() == 4) {
            Player player = Bukkit.getPlayer(red);
            player.sendMessage(ChatColor.RED+"This team is full");
        } else {
            this.red.add(red);
            Bukkit.broadcastMessage(ChatColor.RED+"Size: "+this.red.size());
        }
    }

    public void addBlue(UUID blue) {
        if (this.blue.size() == 4) {
            Player player = Bukkit.getPlayer(blue);
            player.sendMessage(ChatColor.RED+"This team is full");
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

    public boolean isBlue(UUID player){
        for (UUID pl : blue) {
            if(pl == player){
                return true;
            }
        }
        return false;
    }

    public boolean isRed(UUID player){
        for (UUID pl : red) {
            if(pl == player){
                return true;
            }
        }
        return false;
    }

    public UUID getIdRed(int number){
        Bukkit.broadcastMessage("Red team size:"+red.size());
        UUID pl = red.get(number);
        if(!(pl == null)) {
            if (number > -1 && number < 5) {
                return pl;
            }
        }else Bukkit.broadcastMessage("LOL IS NULL");
        return null;
    }
    public UUID getIdBlue(int number) {
        if (blue.get(number) != null) {
            if (number > -1 && number < 5) {
                return blue.get(number);
            }
        }
        return null;
    }
}
