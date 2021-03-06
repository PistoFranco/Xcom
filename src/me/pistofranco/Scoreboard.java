package me.pistofranco;

import me.pistofranco.api.DefaultFontInfo;
import me.pistofranco.resouces.RoundManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collections;

public class Scoreboard {

    private MainClass plugin;
    private RoundManager RoundManager;

    ScoreboardManager manager;
    org.bukkit.scoreboard.Scoreboard board;
    Teams teams;

    public Scoreboard(MainClass plugin){
        this.plugin = plugin;
        RoundManager = plugin.getRoundManagerClass();
        teams = plugin.getTeamsClass();
    }


    public void sendScoreboard(Player player){
        manager = Bukkit.getScoreboardManager();
        board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("Board","dummy");
        objective.setDisplayName("Scoreboard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Score score1 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GOLD+"Rounds: "));
        score1.setScore(12);
        Score score2 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GOLD+""+RoundManager.getRounds()+""));
        score2.setScore(11);
        Score score3 = objective.getScore("");
        score3.setScore(10);
        Score score4 = objective.getScore(" ");
        score4.setScore(9);
        Score score5 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GREEN+"Now throwing:"));
        score5.setScore(8);
        Score score6 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GREEN+""+" "+serializeTeam(RoundManager.getRealPlayer())));
        score6.setScore(7);
        Score score7 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GREEN+""+RoundManager.getIdTrowing()+" / "+"10"));
        score7.setScore(6);
        Score score8 = objective.getScore("  ");
        score8.setScore(5);
        Score score9 = objective.getScore("   ");
        score9.setScore(4);
        Score score10 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.AQUA+"You are playing on:"));
        score10.setScore(3);
        Score score11 = objective.getScore(DefaultFontInfo.getCenteredMessage(ChatColor.GOLD+"mc.rolegame.net"));
        score11.setScore(2);
        player.setScoreboard(board);
    }

    private String serializeTeam(Player player){
        if(teams.isRed(player.getUniqueId())){
            return ChatColor.RED+player.getName();
        }else return ChatColor.BLUE+player.getName();
    }
}
