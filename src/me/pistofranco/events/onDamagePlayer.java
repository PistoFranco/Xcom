package me.pistofranco.events;

import me.pistofranco.GameState;
import me.pistofranco.MainClass;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class onDamagePlayer implements Listener {

    private MainClass plugin;

    public onDamagePlayer(MainClass plugin) {
        this.plugin = plugin;

    }

    @EventHandler
    public void entityDamageEvent(EntityDamageByEntityEvent event) {
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                Player damaged = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();
            }
            if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
                Player damaged = (Player)event.getEntity();
                Arrow arrow = (Arrow) event.getDamager();
                Player shooter = (Player) arrow.getShooter();
                /*if (hability.hasHability(Hability.MARKSMAN)) {
                    new BukkitRunnable() {
                        int time = 10;
                        @Override
                        public void run() {
                            if(time == 10) {
                                damaged.setGlowing(true);
                            }
                            if(time == 0){
                                damaged.setGlowing(false);
                            }
                            time--;
                        }
                    }.runTaskTimer(plugin,0L,20L);
                }
                */
            }
        }
    }
}
