package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import me.pistofranco.api.ActionBar;
import me.pistofranco.resouces.RoundManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public class onWalk implements Listener {

    MainClass plugin;
    Teams teams;
    RoundManager roundManager;
    private UUID player;



    public onWalk(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
        roundManager = plugin.getRoundManagerClass();
    }


    private ActionBar message = new ActionBar(ChatColor.RED + "You cant move!");

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event) {
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (teams.hasTeam(event.getPlayer().getUniqueId())) {
                if (event.getPlayer().getUniqueId() == roundManager.getPlayer()) {
                    if (roundManager.getMovements() == 0) {
                        message.sendToPlayer(event.getPlayer());
                        if (event.getPlayer().isFlying()) {
                            return;
                        } else {
                            Location from = event.getFrom();
                            if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                                event.getPlayer().teleport(from);
                            }
                        }
                        roundManager.newRound();
                    }
                    if (event.getTo().getBlockX() != event.getFrom().getBlockX()) {
                        roundManager.setMovements(roundManager.getMovements()-1);
                        ActionBar message2 = new ActionBar(ChatColor.RED + "Movements left: " + roundManager.getMovements());
                        message2.sendToPlayer(event.getPlayer());
                    }
                    if (event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                        roundManager.setMovements(roundManager.getMovements()-1);
                        ActionBar message3 = new ActionBar(ChatColor.RED + "Movements left: " + roundManager.getMovements());
                        message3.sendToPlayer(event.getPlayer());
                    }
                } else {
                    message.sendToPlayer(event.getPlayer());
                    Location from = event.getFrom();
                    if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                        event.getPlayer().teleport(from);
                    }
                }
            }
        }
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player){
        this.player = player;
    }
}
