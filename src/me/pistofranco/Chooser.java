package me.pistofranco;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Chooser implements Listener {
    String SPECIALITY = ChatColor.YELLOW + "Speciality selected: " + ChatColor.AQUA;
    String HABILITIES = ChatColor.GRAY + "Hability selected: " + ChatColor.AQUA;
    @EventHandler
    public void KitSelector(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity e = event.getRightClicked();
        if (GameState.getCurrent() == GameState.CHOOSING) {
            if (e instanceof ItemFrame) {
                if (player.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
                    player.sendMessage(ChatColor.RED + "You can't put nothing in that slot!");
                    event.setCancelled(true);
                    return;
                    //SPECIALIST
                } else {
                    switch (((ItemFrame) e).getItem().getType()) {
                        case DIAMOND_CHESTPLATE: {
                            event.setCancelled(true);
                            player.sendTitle("", SPECIALITY + "Juggernaut");
                            player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_CHESTPLATE));
                        }
                        case BOW: {
                            event.setCancelled(true);
                            player.sendTitle("", SPECIALITY + "Archer");
                            player.getInventory().setItem(0, new ItemStack(Material.BOW));
                        }
                        case TNT: {
                            event.setCancelled(true);
                            player.sendTitle("", SPECIALITY + "Bomber");
                            player.getInventory().setItem(0, new ItemStack(Material.TNT));
                        }
                        case SHIELD: {
                            event.setCancelled(true);
                            player.sendTitle("", HABILITIES + "Shield");
                            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.SHIELD));
                        }
                    }
                }
            }
        } else {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You can't choose, we aren't in choosing phase!");
        }
    }
}