package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.api.ActionBar;
import me.pistofranco.resouces.RoundManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class onShoot implements Listener {

    private MainClass plugin;
    private RoundManager roundManager;

    public onShoot(MainClass plugin) {
        this.plugin = plugin;
        roundManager = plugin.getRoundManagerClass();
    }

    private ActionBar noMovements = new ActionBar(ChatColor.RED + "You don't have sufficient movements!");


    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent event) {
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (roundManager.getMovements() >= 5) {
                    if (player.getUniqueId() == roundManager.getPlayer()) {
                        roundManager.setMovements(roundManager.getMovements()-5);
                        //TODO: get the hitted player and apply glowing effect!.
                    } else {
                        event.setCancelled(true);
                        noMovements.sendToPlayer(player);
                    }
                } else {
                    event.setCancelled(true);
                    noMovements.sendToPlayer(player);
                }
            }
        }
    }
}
