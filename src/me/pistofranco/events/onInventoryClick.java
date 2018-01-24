package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import me.pistofranco.resouces.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class onInventoryClick implements Listener {

    MainClass plugin;
    Teams teams;

    public onInventoryClick(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeamsClass();
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (GameState.getCurrent() == GameState.STARTING || GameState.getCurrent() == GameState.WAITING) {
            if (event.getCursor() == Items.redTeam()) {
                teams.addRed(player.getUniqueId());
                player.sendMessage("§6Succesfully added to §1Red §6team");
                return;
            }
            if(event.getCursor() == Items.blueTeam()){
                teams.addBlue(player.getUniqueId());
                player.sendMessage("§6Succesfully added to §6Blue §6team");
                return;
            }
        }
    }
}
