package me.pistofranco.Habilities;

import org.bukkit.entity.Player;

public class Speciality {

    private Player player;

    private int defense;
    private int damage;

    private boolean invulnerable;

    private void Specialitiy(Player player){
        this.player = player;
    }

    public enum hability{
        SHIELD, ;
    }

    //TODO Informatic (Drone)
}
