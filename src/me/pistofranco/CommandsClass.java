package me.pistofranco;

import me.pistofranco.api.DefaultFontInfo;
import me.pistofranco.resouces.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class CommandsClass implements CommandExecutor{
    MainClass plugin;
    private HashMap<String, String> COMMANDS;

    public CommandsClass(MainClass plugin) {
        this.plugin = plugin;
        COMMANDS = new HashMap<>();
        COMMANDS.put("stop", "Stop all the countdowns");
        COMMANDS.put("start","Start the countdowns");
        COMMANDS.put("creation","Enter creation mode & kick all the non OP players");
        COMMANDS.put("red","Join RED team");
        COMMANDS.put("blue","Join BLUE team");
        COMMANDS.put("spawn","Sets the spawn to your position");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player)sender;
        if(label.equalsIgnoreCase("mcrole")){
            if(args.length < 1){
                DefaultFontInfo.sendCenteredMessage(player,"§6----"+"§aMinecraft Tactics"+"§6----");
                for(String cmds : COMMANDS.keySet()) {
                    DefaultFontInfo.sendCenteredMessage(player,"§2"+cmds+" §7"+COMMANDS.get(cmds));
                }
                DefaultFontInfo.sendCenteredMessage(player,"§6----"+"§aMinecraft Tactics"+"§6----");
            }else {
                if(args[0].equalsIgnoreCase("stop")){
                    plugin.timeFirstCountdown = 0;
                    plugin.timeSecondCountdown = 0;
                    GameState.setCurrent(GameState.CREATING);
                }
                if(args[0].equalsIgnoreCase("start")){
                    plugin.startCountdown();
                    GameState.setCurrent(GameState.STARTING);
                }
                if(args[0].equalsIgnoreCase("spawn")){
                    if(args.length == 3){
                        int target = Integer.parseInt(args[2]);
                        if(args[1].equalsIgnoreCase("blue")){
                            if(target <=4 && target >= 0){
                                plugin.getConfig().set("spawn.blue."+target+".world",player.getLocation().getWorld().getName());
                                plugin.getConfig().set("spawn.blue."+target+".x",player.getLocation().getX());
                                plugin.getConfig().set("spawn.blue."+target+".y",player.getLocation().getY());
                                plugin.getConfig().set("spawn.blue."+target+".z",player.getLocation().getZ());
                                plugin.saveConfig();
                                player.sendMessage(ChatColor.GREEN+"Spawn created successfully!");
                            }else if(target == 5){
                                plugin.getConfig().set("spawn.blue.choose.world",player.getLocation().getWorld().getName());
                                plugin.getConfig().set("spawn.blue.choose.x",player.getLocation().getX());
                                plugin.getConfig().set("spawn.blue.choose.y",player.getLocation().getY());
                                plugin.getConfig().set("spawn.blue.choose.z",player.getLocation().getZ());
                                plugin.saveConfig();
                                player.sendMessage(ChatColor.GREEN+"Choosing spawn blue, created successfully!");
                            }else player.sendMessage("usage: /mcrole spawn <team> <number 0 to 4>");
                        }else if (args[1].equalsIgnoreCase("red")){
                            if(target <=4 && target >= 0){
                                plugin.getConfig().set("spawn.red."+target+".world",player.getLocation().getWorld().getName());
                                plugin.getConfig().set("spawn.red."+target+".x",player.getLocation().getX());
                                plugin.getConfig().set("spawn.red."+target+".y",player.getLocation().getY());
                                plugin.getConfig().set("spawn.red."+target+".z",player.getLocation().getZ());
                                plugin.saveConfig();
                                player.sendMessage(ChatColor.GREEN+"Spawn created successfully!");
                            }else player.sendMessage("usage: /mcrole spawn <team> <number 0 to 4>");
                            if(target == 5){
                                plugin.getConfig().set("spawn.red.choose.world",player.getLocation().getWorld().getName());
                                plugin.getConfig().set("spawn.red.choose.x",player.getLocation().getX());
                                plugin.getConfig().set("spawn.red.choose.y",player.getLocation().getY());
                                plugin.getConfig().set("spawn.red.choose.z",player.getLocation().getZ());
                                plugin.saveConfig();
                                player.sendMessage(ChatColor.GREEN+"Choosing spawn red, created successfully!");
                            }
                        }else player.sendMessage("usage: /mcrole spawn <team> <number 0 to 4>");
                    }else {
                        player.sendMessage("usage: /mcrole spawn <team> <number 0 to 4>");
                    }
                }
                if(args[0].equalsIgnoreCase("creation")){
                    GameState.setCurrent(GameState.CREATING);
                    for (Player pl: Bukkit.getOnlinePlayers()) {
                        if(!(pl.isOp())){
                            pl.kickPlayer(ChatColor.RED+"This server is on creation mode");
                        }else {
                            pl.sendTitle("",ChatColor.GREEN+"Creation mode activated");
                            pl.sendMessage(ChatColor.RED+"Kicking all non op players");
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("red")){
                    Teams teams = plugin.getTeamsClass();
                    teams.addRed(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN+"You are in the red team");
                }
                if(args[0].equalsIgnoreCase("blue")){
                    Teams teams = plugin.getTeamsClass();
                    teams.addBlue(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN+"You are in the blue team");
                }
                if(args[0].equalsIgnoreCase("help")){
                    player.sendMessage("§6----"+"§aMinecraft Tactics"+"§6----");
                    for(String cmds : COMMANDS.keySet()) {
                        player.sendMessage("§2"+cmds+" §7"+COMMANDS.get(cmds));
                    }
                    player.sendMessage("§6----"+"§aMinecraft Tactics"+"§6----");
                }
            }
        }
        return false;
    }
}
