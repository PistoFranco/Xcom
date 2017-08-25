package me.pistofranco;

import org.bukkit.Bukkit;
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
                player.sendMessage(ChatColor.AQUA+"/mcrole (stop,start,creation,red,blue,spawn)");
            }else {
                if(args[0].equalsIgnoreCase("stop")){
                    plugin.stopCountdown();
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
                            }else player.sendMessage("usage: /mcrole spawn <team> <number 0 to 4>");
                            if(target == 5){
                                plugin.getConfig().set("spawn.blue.choose.world",player.getLocation().getWorld().getName());
                                plugin.getConfig().set("spawn.blue.choose.x",player.getLocation().getX());
                                plugin.getConfig().set("spawn.blue.choose.y",player.getLocation().getY());
                                plugin.getConfig().set("spawn.blue.choose.z",player.getLocation().getZ());
                                plugin.saveConfig();
                                player.sendMessage(ChatColor.GREEN+"Choosing spawn blue, created successfully!");
                            }
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
