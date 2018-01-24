package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.resouces.Items;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class onJoin implements Listener {

    private MainClass plugin;
    public onJoin(MainClass plugin){
        this.plugin = plugin;
    }


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
            player.getInventory().setItem(8, new ItemStack(Items.redTeam()));
            player.getInventory().setItem(7,new ItemStack(Items.blueTeam()));
        }
        player.teleport(player.getWorld().getSpawnLocation());
        BukkitTask task = new BukkitRunnable(){
            @Override
            public void run() {
                player.openInventory(MainClass.myInventory);
            }
        }.runTaskLater(plugin,20L);

    }

}
