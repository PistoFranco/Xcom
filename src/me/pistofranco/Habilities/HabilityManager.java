package me.pistofranco.Habilities;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Map;

public class HabilityManager {

    private Player player;
    private ArrayList<Hability> habilities = new ArrayList<>(4);

    public HabilityManager(Player player) {
        this.player = player;
    }


    public void setHability(Player player,Hability hability) {
        if(habilities.contains(hability)){
            player.sendMessage(ChatColor.RED+"You already got this hability");
            return;
        }
        habilities.add(hability);
    }

    public void removeHability(Hability hability) {
        habilities.remove(hability);
    }

    public boolean hasHability(Hability hability) {
        return habilities.contains(hability);
    }
}

