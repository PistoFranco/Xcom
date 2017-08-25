package me.pistofranco;

import me.pistofranco.resouces.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ChooserPhase implements Listener {
    String SPECIALITY = ChatColor.YELLOW + "Speciality selected: " + ChatColor.AQUA;
    String HABILITIES = ChatColor.GRAY + "Hability selected: " + ChatColor.AQUA;

    @EventHandler
    public void KitSelector(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity e = event.getRightClicked();
        if(GameState.getCurrent() == GameState.CREATING){
            return;
        }else if (GameState.getCurrent() == GameState.CHOOSING) {
            if (e instanceof ItemFrame) {
                if (player.getInventory().getItemInMainHand().getType() == Material.BARRIER) {
                    player.sendMessage(ChatColor.RED + "You can't put nothing in that slot!");
                    event.setCancelled(true);
                    return;
                    //SPECIALIST
                } else {
                    event.setCancelled(true);
                    switch (((ItemFrame) e).getItem().getType()) {
                        case DIAMOND_CHESTPLATE: {
                                player.sendTitle("", SPECIALITY + "Juggernaut");
                                player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_CHESTPLATE));
                                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
                                return;
                        }
                        case BOW: {
                            player.sendTitle("", SPECIALITY + "Archer");
                            player.getInventory().setItem(0, Items.sniperBow());
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
                            return;
                        }
                        case TNT: {
                            player.sendTitle("", SPECIALITY + "Bomber");
                            player.getInventory().setItem(0, Items.tntBomb());
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
                            return;
                        }
                        case SHIELD: {
                            player.sendTitle("", HABILITIES + "Shield");
                            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), Items.shield());
                            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH,1f,1f);
                            return;
                        }
                        case DIAMOND_HELMET:{
                            player.sendTitle("",SPECIALITY+ "Detector");
                            player.getInventory().setItem(player.getInventory().getHeldItemSlot(),Items.helmet());
                            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1f,1f);
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
