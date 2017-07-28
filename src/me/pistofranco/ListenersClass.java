package me.pistofranco;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.UUID;

/**
 * Created by Jordi M on 24/07/2017.
 */
public class ListenersClass implements Listener{

    private MainClass plugin;
    Teams teams;


    private UUID player;
    private int movements;
    private int idTrowing = -1; //ACTUAL ID TROWING

    public ListenersClass(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeams();
    }

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
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (event.getPlayer().getUniqueId() == getPlayer()) {
                if (movements == 0) {
                    Bukkit.broadcastMessage("You ran out of movements");
                    Location from = event.getFrom();
                    if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                        event.getPlayer().teleport(from);
                    }
                    newRound();
                }
                if (event.getTo().getBlockX() != event.getFrom().getBlockX()) {
                    movements--;
                    Bukkit.broadcastMessage("Movements left: " + movements);
                }
                if (event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                    movements--;
                    Bukkit.broadcastMessage("Movements left: " + movements);
                }
            } else {
                event.getPlayer().sendTitle("", ChatColor.RED + "Isn't your round!");
                Location from = event.getFrom();
                if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                    event.getPlayer().teleport(from);
                }
            }
        }
    }
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(GameState.getCurrent() == GameState.STARTING){
            player.getInventory().setItem(1,new ItemStack(Material.BARRIER));
            player.getInventory().setItem(4,new ItemStack(Material.BARRIER));
        }
        if(player.isOp()){
            return;
        }
        if(GameState.getCurrent() == GameState.IN_GAME){
            player.kickPlayer(ChatColor.RED+"This server is in game, you cannot acces!");
            //TODO: Permitir el acceso a jugadores OP.
        }
        if(GameState.getCurrent() == GameState.STARTING){
            player.getInventory().setItem(1,new ItemStack(Material.BARRIER));
            player.getInventory().setItem(6,new ItemStack(Material.BARRIER));
            player.getInventory().setItem(7,new ItemStack(Material.BARRIER));
            player.getInventory().setItem(8,new ItemStack(Material.BARRIER));
        }
    }

    private void newPlayerTrows(UUID players) {
        if (players == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Cannot find the player: " + idTrowing);
            nextPlayerTrows();
        } else {
            setPlayer(players);
            setMovements(20);
        }
    }

    private int nextPlayerTrows() {
        if (idTrowing == 9) {
            idTrowing = 0;
        } else idTrowing++;

        switch (idTrowing) {
            case 0: {
                newPlayerTrows(teams.getIdRed(0));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 1: {
                newPlayerTrows(teams.getIdBlue(0));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 2: {
                newPlayerTrows(teams.getIdRed(1));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 3: {
                newPlayerTrows(teams.getIdBlue(1));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 4: {
                newPlayerTrows(teams.getIdRed(2));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 5: {
                newPlayerTrows(teams.getIdBlue(2));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 6: {
                newPlayerTrows(teams.getIdRed(3));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }

            case 7: {
                newPlayerTrows(teams.getIdBlue(3));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 8: {
                newPlayerTrows(teams.getIdRed(4));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }
            case 9: {
                newPlayerTrows(teams.getIdBlue(4));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Localized new player: " + idTrowing);
                return idTrowing;
            }

        }
        return 0;
    }

    public void startGame(){
        nextPlayerTrows();
        Player pl = Bukkit.getPlayer(getPlayer());
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            plyer.sendTitle("",ChatColor.GREEN+"Player "+ChatColor.AQUA+pl.getName()+ChatColor.GREEN+ " is trowing.");
        }
    }
    private void newRound() {
        nextPlayerTrows();
        Player player = Bukkit.getPlayer(getPlayer());
        String title;
            if (teams.isRed(getPlayer())) {
                title = ChatColor.GREEN + "Player " + ChatColor.RED + player.getName() + ChatColor.GREEN + " is trowing.";
            }else {
            title = ChatColor.GREEN + "Player " + ChatColor.BLUE + player.getName() + ChatColor.GREEN + " is trowing.";
            }
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            plyer.sendTitle("",title);
        }
    }

}
