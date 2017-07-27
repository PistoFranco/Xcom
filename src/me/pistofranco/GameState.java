package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Created by Jordi M on 24/07/2017.
 */
public enum GameState {
    STARTING, IN_GAME, WAITING, RESTARTING,CHOOSING,CREATING;
    private static GameState current;


    public static GameState getCurrent() {
        return current;
    }

    public static void setCurrent(GameState given) {
        current = given;
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"New game state: "+ChatColor.AQUA + given.toString());
    }
}
