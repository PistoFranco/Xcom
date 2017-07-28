package me.pistofranco;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandsClass implements CommandExecutor{
    MainClass plugin;

    public CommandsClass(MainClass plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(label.equalsIgnoreCase("mcrole")){
            if(args.length < 1){
                player.sendMessage(ChatColor.AQUA+"/mcrole (stop,start,creation,red,blue)");
            }else {
                if(args[0].equalsIgnoreCase("stop")){
                    plugin.stopCountdown();
                    GameState.setCurrent(GameState.CREATING);
                }
                if(args[0].equalsIgnoreCase("start")){
                    plugin.startCountdown();
                    GameState.setCurrent(GameState.STARTING);
                }
                if(args[0].equalsIgnoreCase("creation")){
                    GameState.setCurrent(GameState.CREATING);
                }
                if(args[0].equalsIgnoreCase("red")){
                    Teams teams = plugin.getTeams();
                    teams.addRed(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN+"You are in the red team");
                }
                if(args[0].equalsIgnoreCase("blue")){
                    Teams teams = plugin.getTeams();
                    teams.addBlue(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN+"You are in the blue team");
                }
            }
        }
        return false;
    }
}
