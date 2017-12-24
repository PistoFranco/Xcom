package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.resouces.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class onJoin implements Listener {



    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (player.isOp()) {
                return;
            } else {
                player.kickPlayer(ChatColor.RED + "This server is in game, you cannot access!");
            }
        }
        if (GameState.getCurrent() == GameState.STARTING || GameState.getCurrent() == GameState.WAITING) {
            player.getInventory().clear();
            player.getInventory().setItem(1, new ItemStack(Material.BARRIER));
            player.getInventory().setItem(6, new ItemStack(Material.BARRIER));
            player.getInventory().setItem(7, new ItemStack(Material.BARRIER));
            player.getInventory().setItem(8, new ItemStack(Items.banner()));
        }
        player.teleport(player.getWorld().getSpawnLocation());
    }

}
