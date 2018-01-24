package me.pistofranco.resouces;


import me.pistofranco.MainClass;
import me.pistofranco.Scoreboard;
import me.pistofranco.Teams;
import me.pistofranco.api.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class RoundManager {

    private MainClass plugin;
    private Teams teams;

    private int idTrowing = -1;
    private int rounds = 0;
    private UUID player;
    private int movements;

    public RoundManager(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
    }

    private void newPlayerTrows(UUID players) {
        if (players == null) {
            nextPlayerTrows();
        } else {
            setPlayer(players);
            setMovements(20);
            Scoreboard scoreboard = new Scoreboard(plugin);
            for (Player player : Bukkit.getOnlinePlayers()){
                scoreboard.sendScoreboard(player);
            }
        }
    }
    private void setRounds(){
        rounds++;
        Bukkit.broadcastMessage(ChatColor.GOLD+"Round: "+ChatColor.AQUA+getRounds()+ChatColor.GOLD+" has started!");
    }
    private void nextPlayerTrows() {
        if (idTrowing == 9) {
            setRounds();
            idTrowing = 0;
        } else idTrowing++;

        switch (idTrowing) {
            case 0: {
                newPlayerTrows(teams.getIdRed(0));
                return;
            }
            case 1: {
                newPlayerTrows(teams.getIdBlue(0));
                return;
            }
            case 2: {
                newPlayerTrows(teams.getIdRed(1));
                return;
            }
            case 3: {
                newPlayerTrows(teams.getIdBlue(1));
                return;
            }
            case 4: {
                newPlayerTrows(teams.getIdRed(2));
                return;
            }
            case 5: {
                newPlayerTrows(teams.getIdBlue(2));
                return;
            }
            case 6: {
                newPlayerTrows(teams.getIdRed(3));
                return;
            }

            case 7: {
                newPlayerTrows(teams.getIdBlue(3));
                return;
            }
            case 8: {
                newPlayerTrows(teams.getIdRed(4));
                return;
            }
            case 9: {
                newPlayerTrows(teams.getIdBlue(4));
            }
        }
    }

    public void startGame() {
        for (Player inside: Bukkit.getOnlinePlayers()) {
            inside.playSound(inside.getLocation(), Sound.ENTITY_WITHER_DEATH,1f,1f);
            inside.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,30,10));
            inside.sendTitle("",ChatColor.GOLD+"Starting the game!",5,100,5);
            Utils utils = new Utils(plugin);
            utils.teleportPlayers();

        }
        newRound();
    }

    public void newRound() {
        nextPlayerTrows();
        Player player = Bukkit.getPlayer(getPlayer());
        String title;
        if (teams.isRed(getPlayer())) {
            title = ChatColor.GREEN + "Player " + ChatColor.RED + player.getName() + ChatColor.GREEN + " is trowing.";
        } else {
            title = ChatColor.GREEN + "Player " + ChatColor.BLUE + player.getName() + ChatColor.GREEN + " is trowing.";
        }
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            plyer.sendTitle("", title);
        }
    }

    public UUID getPlayer(){
        return player;
    }

    public Player getRealPlayer(){
        return Bukkit.getPlayer(player);
    }

    private void setPlayer(UUID player) {
        this.player = player;
    }

    public void setMovements(int movements) {
        this.movements = movements;
        ActionBar message2 = new ActionBar(ChatColor.RED + "Movements left: " + movements);
        message2.sendToPlayer(Bukkit.getPlayer(player));
        if (movements == 0) {
            nextPlayerTrows();
        }
    }

    public int getMovements() {
        return movements;
    }

    public int getRounds() {
        return rounds;
    }

    public int getIdTrowing() {
        return idTrowing+1;
    }

    public void endGame(){
        //TODO: All the unserialization.
    }
}
