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
                player.sendMessage(ChatColor.AQUA+"/mcrole (stop,start,creation)");
            }else {
                if(args[0].equalsIgnoreCase("stop")){
                    plugin.stopCountdown();
                }
                if(args[0].equalsIgnoreCase("start")){
                    plugin.startCountdown();
                }
                if(args[0].equalsIgnoreCase("creation")){
                    GameState.setCurrent(GameState.CREATING);
                }
            }
        }
        return false;
    }
}
