package me.pistofranco.resouces;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class  Messages {

    public static String usedHability(String habilityName,String playerName,boolean isRed){
        if(isRed){
            String value = ChatColor.RED + "" + ChatColor.BOLD + playerName+ChatColor.YELLOW+ChatColor.BOLD + " use: "+ChatColor.AQUA+""+ChatColor.BOLD+habilityName;
            return value;
        }else {
            String value = ChatColor.BLUE + "" + ChatColor.BOLD + playerName+ ChatColor.YELLOW+""+ChatColor.BOLD+" use: "+ChatColor.AQUA+""+ChatColor.BOLD+habilityName;
         return value;
        }
    }
    public static List<String> makeLoreRadius(String type, int damage,int radius){
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+type); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Damage: "+ChatColor.RED+"+"+damage);
        lore.add(ChatColor.YELLOW+"Radius:"+ChatColor.RED+" "+radius+" blocks");
        lore.add("  ");
        lore.add(ChatColor.YELLOW+"Description:");
        lore.add("   ");
        lore.add("");
        return lore;
    }
}
