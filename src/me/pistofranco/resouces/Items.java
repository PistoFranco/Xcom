package me.pistofranco.resouces;

import me.pistofranco.MainClass;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Items {
    private static MainClass plugin;

    public Items(MainClass plugin){
        this.plugin = plugin;
    }
    public static ItemStack banner(){
        ItemStack banner = new ItemStack(Material.BANNER);
        ItemMeta meta = banner.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Choose team");
        banner.setItemMeta(meta);
        return banner;
    }
    public static ItemStack redTeam(){
        ItemStack wool = new ItemStack(Material.WOOL,(short)14);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"Red team");
        wool.setItemMeta(meta);
        return wool;
    }
    public static ItemStack blueTeam(){
        ItemStack wool = new ItemStack(Material.WOOL,(short)11);
        ItemMeta meta = wool.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE+"Blue team");
        wool.setItemMeta(meta);
        return wool;
    }
    public static ItemStack sniperBow(){
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Marksman bow");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+"Distance gun"); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Damage: "+ChatColor.RED+"+5");
        lore.add(ChatColor.YELLOW+"Movements: "+ChatColor.AQUA+plugin.getConfig().getInt("items.marksman_bow.movements"));
        lore.add(ChatColor.YELLOW+"Effect duration: "+ChatColor.AQUA+"1 round");
        lore.add("  ");
        lore.add(ChatColor.AQUA+"Description:");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"When you hit an enemy,");
        lore.add(ChatColor.YELLOW+"you can see him trough");
        lore.add(ChatColor.YELLOW+"the walls for 1 round");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack shield(){
        ItemStack item = new ItemStack(Material.SHIELD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Shield of protection");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+"Protection"); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Damage: "+ChatColor.RED+"0");
        lore.add(ChatColor.YELLOW+"Movements: "+ChatColor.AQUA+"10 + End of round");
        lore.add(ChatColor.YELLOW+"Effect duration: "+ChatColor.AQUA+"1 round");
        lore.add("  ");
        lore.add(ChatColor.AQUA+"Description:");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"When you activate it,");
        lore.add(ChatColor.YELLOW+"you will be invulnerable");
        lore.add(ChatColor.YELLOW+"for 1 round");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack wandOfComunication(){
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Wand of communication");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+"Informative"); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Damage: "+ChatColor.RED+"0");
        lore.add(ChatColor.YELLOW+"Movements: "+ChatColor.AQUA+plugin.getConfig().getInt("items.wand.movements"));
        lore.add("  ");
        lore.add(ChatColor.AQUA+"Description:");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"If you see a player, you");
        lore.add(ChatColor.YELLOW+"can inform to your allies");
        lore.add(ChatColor.YELLOW+"by looking and clicking.");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"Selected player will glow for");
        lore.add(ChatColor.RED+""+plugin.getConfig().getInt("items.wand.seconds")+ChatColor.YELLOW+" seconds");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack tntBomb(){
        ItemStack item = new ItemStack(Material.TNT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Bomb");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+"Invisible"); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Damage: "+ChatColor.RED+"+6");
        lore.add(ChatColor.YELLOW+"Movements:"+ChatColor.AQUA+"12");
        lore.add(ChatColor.YELLOW+"Radius: "+ChatColor.RED+"5 blocks");
        lore.add("  ");
        lore.add(ChatColor.AQUA+"Description:");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"Where you click, it will");
        lore.add(ChatColor.YELLOW+"summon an invisible bomb");
        lore.add(ChatColor.YELLOW+"and explode when an enemy");
        lore.add(ChatColor.YELLOW+"is under 5 blocks");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack helmet(){
        ItemStack item = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD+"Helmet of reveal");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW+"Type: "+ChatColor.AQUA+"Protection"); //Invisible or Visible;
        lore.add(ChatColor.YELLOW+"Function: "+ChatColor.AQUA+"See invisible bombs");
        lore.add(ChatColor.YELLOW+"Movements: "+ChatColor.AQUA+10);
        lore.add(ChatColor.YELLOW+"Effect duration: "+ChatColor.AQUA+"1 round");
        lore.add("  ");
        lore.add(ChatColor.AQUA+"Description:");
        lore.add("   ");
        lore.add(ChatColor.YELLOW+"Use this helmet to reveal");
        lore.add(ChatColor.YELLOW+"invisible bombs on the ground");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
