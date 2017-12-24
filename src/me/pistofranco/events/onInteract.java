package me.pistofranco.events;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.effect.ShieldEffect;
import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import me.pistofranco.Teams;
import me.pistofranco.api.ActionBar;
import me.pistofranco.resouces.Messages;
import me.pistofranco.resouces.RoundManager;
import me.pistofranco.resouces.Utils;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.UUID;

public class onInteract implements Listener {

    MainClass plugin;
    RoundManager roundManager;
    Utils utils;
    Teams teams;

    public onInteract(MainClass plugin) {
        this.plugin = plugin;
        roundManager = plugin.getRoundManagerClass();
        utils = new Utils(plugin);
        teams = plugin.getTeamsClass();
    }

    private ActionBar cantTrow = new ActionBar(ChatColor.RED + "You can't trow now!");
    private ActionBar noMovements = new ActionBar(ChatColor.RED + "You don't have sufficient movements!");

    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        final Player player1 = event.getPlayer();
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (player1.getUniqueId() == roundManager.getPlayer()) {
                switch (event.getPlayer().getInventory().getItemInMainHand().getType()) {
                    case BLAZE_ROD: {
                        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            if (roundManager.getMovements() >= plugin.getConfig().getInt("items.wand.movements")) {
                                roundManager.setMovements(roundManager.getMovements()- 5);
                                Player target = utils.getTargetPlayer(player1, 50);
                                if (target != null) {
                                    if (teams.isRed(player1.getUniqueId())) {
                                        for (UUID uuid : teams.getRedPlayers()) {
                                            Player redTeam = Bukkit.getPlayer(uuid);
                                            redTeam.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + player1.getName() + ChatColor.GREEN + " has spotted an enemy!");
                                            redTeam.playSound(redTeam.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                                            target.setGlowing(true);
                                            BukkitTask task = new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    target.setGlowing(false);
                                                }
                                            }.runTaskLater(plugin, plugin.getConfig().getInt("items.wand.seconds") * 20L);
                                        }
                                    } else {
                                        for (UUID uuid : teams.getBluePlayers()) {
                                            Player blueTeam = Bukkit.getPlayer(uuid);
                                            blueTeam.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + player1.getName() + ChatColor.GREEN + " has spotted an enemy!");
                                            blueTeam.playSound(blueTeam.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                                            target.setGlowing(true);
                                        }
                                    }
                                }
                            } else noMovements.sendToPlayer(player1);
                        }
                    }
                    case BARRIER: {
                        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                            event.setCancelled(true);
                        }
                        return;
                    }
                    case TNT: {
                        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                            event.setCancelled(true);
                            final Location location = new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ());
                            final ArmorStand armorStand = player1.getLocation().getWorld().spawn(location, ArmorStand.class);
                            armorStand.setVisible(false);
                            BukkitTask task = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (teams.isRed(player1.getUniqueId())) {
                                        Collection<Entity> inside = location.getWorld().getNearbyEntities(location, 5, 5, 5);
                                        if (inside.size() >= 1) {
                                            for (Entity plInside : inside) {
                                                if (plInside instanceof Player) {
                                                    if (!teams.isRed(plInside.getUniqueId())) {
                                                        ((Player) plInside).damage(6);
                                                        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 100, 100);
                                                        location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f);
                                                        armorStand.remove();
                                                        cancel();
                                                    } else return;
                                                } else return;
                                            }

                                        }
                                    }
                                    if (!teams.isRed(player1.getUniqueId())) {
                                        Collection<Entity> inside = location.getWorld().getNearbyEntities(location, 5, 5, 5);
                                        if (inside.size() >= 1) {
                                            for (Entity plInside : inside) {
                                                if (plInside instanceof Player) {
                                                    if (teams.isRed(plInside.getUniqueId())) {
                                                        ((Player) plInside).damage(6);
                                                        location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 100, 100);
                                                        armorStand.remove();
                                                        cancel();
                                                    } else return;
                                                } else return;
                                            }

                                        }
                                    }
                                }
                            }.runTaskTimer(plugin, 10L, 10L);
                        }
                    }
                    case SHIELD: {
                        if (roundManager.getMovements() >= 10) {
                            roundManager.setMovements(0);
                            EffectManager em = new EffectManager(plugin);
                            ShieldEffect effect = new ShieldEffect(em);
                            effect.setLocation(new Location(player1.getWorld(), player1.getLocation().getX(), player1.getLocation().getY(), player1.getLocation().getZ()));
                            effect.radius = 2;
                            effect.iterations = 15 * 3;
                            effect.start();
                            for (Player allPl : Bukkit.getOnlinePlayers()) {
                                allPl.sendMessage(Messages.usedHability("Shield of Protection!", player1.getName(), teams.isRed(player1.getUniqueId())));
                                allPl.playSound(allPl.getLocation(), Sound.BLOCK_ANVIL_USE, 1f, 1f);
                            }
                        } else noMovements.sendToPlayer(player1);
                    }
                }
            } else {
                event.setCancelled(true);
            }
        } else {
            cantTrow.sendToPlayer(player1);
        }
    }
}