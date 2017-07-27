package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class ListenersClass implements Listener{

    private MainClass plugin;



    private UUID player;
    private int movements;
    private int idTrowing = -1; //ACTUAL ID TROWING

    public ListenersClass(MainClass plugin) {
        this.plugin = plugin;
    }
    ListenersClass listener = plugin.getListener();
    Teams teams = plugin.getTeams();

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public void setMovements(int movements) {
        this.movements = movements;
    }

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event) {
        if (event.getPlayer().getUniqueId() == getPlayer()) {
            if (movements == 0) {
                Bukkit.broadcastMessage("You ran out of movements");
                Location from = event.getFrom();
                if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                    event.getPlayer().teleport(from);
                }
                endRound();
            }
            if (event.getTo().getBlockX() != event.getFrom().getBlockX()) {
                movements--;
                Bukkit.broadcastMessage("Movements left: " + movements);
            }
            if (event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                movements--;
                Bukkit.broadcastMessage("Movements left: " + movements);
            }
        }else{
            event.getPlayer().sendTitle("",ChatColor.RED+"Isn't your round!");
            Location from = event.getFrom();
            if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                event.getPlayer().teleport(from);
            }
        }
    }
    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event){
        if(GameState.getCurrent() == GameState.IN_GAME){
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER,ChatColor.RED+"This server is in game, you cannot acces!");
            //TODO: Permitir el acceso a jugadores OP.
        }
    }

    private void newPlayerTrows(UUID players) {
        if (players == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cannot find the player: " + idTrowing);
            nextPlayerTrows();
        } else {
            listener.setPlayer(players);
            listener.setMovements(20);
        }
    }

    private void nextPlayerTrows() {
        if (idTrowing == 9) {
            idTrowing = 0;
        } else idTrowing++;

        switch (idTrowing) {
            case 0: {
                newPlayerTrows(teams.getIdRed(0));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 1: {
                newPlayerTrows(teams.getIdBlue(0));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 2: {
                newPlayerTrows(teams.getIdRed(1));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 3: {
                newPlayerTrows(teams.getIdBlue(1));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 4: {
                newPlayerTrows(teams.getIdRed(2));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 5: {
                newPlayerTrows(teams.getIdBlue(2));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 6: {
                newPlayerTrows(teams.getIdRed(3));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }

            case 7: {
                newPlayerTrows(teams.getIdBlue(3));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 8: {
                newPlayerTrows(teams.getIdRed(4));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }
            case 9: {
                newPlayerTrows(teams.getIdBlue(4));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                break;
            }

        }
    }

    public void startGame(){
        nextPlayerTrows();
        Player pl = Bukkit.getPlayer(listener.getPlayer());
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            plyer.sendTitle("",ChatColor.GREEN+"Player "+ChatColor.AQUA+pl.getName()+ChatColor.GREEN+ " is trowing.");
        }
    }
    private void finishGame(){

    }
    private void newRound() {

    }
    public void endRound(){
        nextPlayerTrows();
    }
}
