package me.pistofranco;

import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.ShieldEffect;
import me.pistofranco.api.*;
import me.pistofranco.resouces.Items;
import me.pistofranco.resouces.Messages;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Created by Jordi M on 24/07/2017.
 */
public class ListenersClass implements Listener {

    private MainClass plugin;
    Teams teams;

    int rounds = 0;
    private UUID player;
    private int movements;
    private int idTrowing = -1;

    private ScoreboardWrapper scoreboard;
    Inventory inv;


    public ListenersClass(MainClass plugin) {
        this.plugin = plugin;
        teams = plugin.getTeams();
        scoreboard = new ScoreboardWrapper(ChatColor.GOLD + "XCom");
        inv = Bukkit.createInventory(null,9,"Select Team:");
    }

    private ActionBar message = new ActionBar(ChatColor.RED + "You cant move!");
    private ActionBar noMovements = new ActionBar(ChatColor.RED + "You don't have sufficient movements!");
    private ActionBar cantTrow = new ActionBar(ChatColor.RED + "You can't trow now!");

    @EventHandler
    public void onPlayerWalk(PlayerMoveEvent event) {
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (event.getPlayer().getUniqueId() == getPlayer()) {
                if (movements == 0) {
                    message.sendToPlayer(event.getPlayer());
                    if (event.getPlayer().isFlying()) {
                        return;
                    } else {
                        Location from = event.getFrom();
                        if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                            event.getPlayer().teleport(from);
                        }
                    }
                    newRound();
                }
                if (event.getTo().getBlockX() != event.getFrom().getBlockX()) {
                    movements--;
                    ActionBar message2 = new ActionBar(ChatColor.RED + "Movements left: " + movements);
                    message2.sendToPlayer(event.getPlayer());
                }
                if (event.getTo().getBlockZ() != event.getFrom().getBlockZ()) {
                    movements--;
                    ActionBar message3 = new ActionBar(ChatColor.RED + "Movements left: " + movements);
                    message3.sendToPlayer(event.getPlayer());
                }
            } else {
                message.sendToPlayer(event.getPlayer());
                Location from = event.getFrom();
                if (from.getZ() != event.getTo().getZ() && from.getX() != event.getTo().getX()) {
                    event.getPlayer().teleport(from);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent event) {
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (event.getEntity() instanceof Player) {
                Player playr = (Player) event.getEntity();
                if (movements >= 5) {
                    if (playr.getUniqueId() == player) {
                        setMovements(movements - 5);
                        //TODO: get the hitted player and apply glowing effect!.
                    } else {
                        event.setCancelled(true);
                        noMovements.sendToPlayer(playr);
                    }
                } else {
                    event.setCancelled(true);
                    noMovements.sendToPlayer(playr);
                }
            }
        }
    }
    @EventHandler
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        final Player player1 = event.getPlayer();
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (player1.getUniqueId() == player) {
                switch (event.getPlayer().getItemInHand().getType()) {
                    case BLAZE_ROD: {
                        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                            if (movements >= plugin.getConfig().getInt("items.wand.movements")) {
                                setMovements(movements - plugin.getConfig().getInt("items.wand.movements"));
                                Player target = getTargetPlayer(player1, 50);
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
                }
                switch (event.getPlayer().getItemInHand().getType()) {
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
                        if (movements >= 10) {
                            setMovements(0);
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
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        if (teams.isRed(killed.getUniqueId())) {
            teams.removeRed(killed.getUniqueId());
            if (teams.getRedPlayers().size() == 0) {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.RED + "Red players eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 1f);
                }
            } else {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.RED + killed.getName() + " has been eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1f, 1f);
                }
            }
        } else {
            teams.removeBlue(killed.getUniqueId());
            if (teams.getBluePlayers().size() == 0) {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.BLUE + "Blue players eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_DEATH, 1f, 1f);
                }
            } else {
                for (Player allPl : Bukkit.getOnlinePlayers()) {
                    allPl.sendTitle(ChatColor.BLUE + killed.getName() + " has been eliminated!", ChatColor.RED + "" + teams.getRedPlayers().size() + ChatColor.AQUA + " vs " + ChatColor.BLUE + teams.getBluePlayers().size());
                    allPl.playSound(allPl.getLocation(), Sound.ENTITY_ENDERDRAGON_HURT, 1f, 1f);
                }
            }
        }
    }
    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        for (Player pl : Bukkit.getOnlinePlayers()) {
            pl.setScoreboard(scoreboard.getScoreboard());
        }
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
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if(event.getInventory() == inv){
            inv.setItem(0,Items.blueTeam());
            inv.setItem(9,Items.redTeam());
            if(item == Items.blueTeam()){
                Teams teams = plugin.getTeams();
                teams.addBlue(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN+"You are in the blue team");
                updateScoreboard();
            }
            if(item == Items.redTeam()){
                Teams teams = plugin.getTeams();
                teams.addRed(player.getUniqueId());
                player.sendMessage(ChatColor.GREEN+"You are in the red team");
                updateScoreboard();
            }
        }
    }

    /**public void startCounter() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedTime / 1000;
        long secondsDisplay = elapsedSeconds % 60;
        long elapsedMinutes = elapsedSeconds / 60;
    }*/

    @EventHandler
    public void onQuitEvent(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        updateScoreboard();
        for (Player plyr : Bukkit.getOnlinePlayers()) {
            plyr.setScoreboard(scoreboard.getScoreboard());
        }
        if (GameState.getCurrent() == GameState.IN_GAME) {
            if (teams.isRed(player.getUniqueId())) {
                teams.removeRed(player.getUniqueId());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendTitle("", ChatColor.RED + player.getName() + ChatColor.AQUA + " has disconnected!");
                }
            } else {
                teams.removeBlue(player.getUniqueId());
                for (Player pl : Bukkit.getOnlinePlayers()) {
                    pl.sendTitle("", ChatColor.BLUE + player.getName() + ChatColor.AQUA + " has disconnected!");
                }
            }
        }
    }

    private void newPlayerTrows(UUID players) {
        if (players == null) {
            nextPlayerTrows();
        } else {
            setPlayer(players);
            setMovements(plugin.getConfig().getInt("settings.movements.start_movements"));
        }
    }
    private void setRounds(){
        rounds++;
        Bukkit.broadcastMessage(ChatColor.GOLD+"A new Round has started!");
    }
    private void nextPlayerTrows() {
        if (idTrowing == 9) {
            setRounds();
            idTrowing = 0;
            rounds++;
        } else idTrowing++;

        switch (idTrowing) {
            case 0: {
                newPlayerTrows(teams.getIdRed(0));
                return;
            }
            case 1: {
                newPlayerTrows(teams.getIdBlue(0));
                return;
            }
            case 2: {
                newPlayerTrows(teams.getIdRed(1));
                return;
            }
            case 3: {
                newPlayerTrows(teams.getIdBlue(1));
                return;
            }
            case 4: {
                newPlayerTrows(teams.getIdRed(2));
                return;
            }
            case 5: {
                newPlayerTrows(teams.getIdBlue(2));
                return;
            }
            case 6: {
                newPlayerTrows(teams.getIdRed(3));
                return;
            }

            case 7: {
                newPlayerTrows(teams.getIdBlue(3));
                return;
            }
            case 8: {
                newPlayerTrows(teams.getIdRed(4));
                return;
            }
            case 9: {
                newPlayerTrows(teams.getIdBlue(4));
            }
        }
    }
    public void startGame() {
        teleportPlayers();
        for (Player inside: Bukkit.getOnlinePlayers()) {
            inside.playSound(inside.getLocation(), Sound.ENTITY_WITHER_DEATH,1f,1f);
            inside.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,30,10));
        }
        nextPlayerTrows();
        Player pl = Bukkit.getPlayer(getPlayer());
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            if (teams.isRed(pl.getUniqueId())) {
                plyer.sendTitle("", ChatColor.AQUA + "Player " + ChatColor.RED + pl.getName() + ChatColor.GREEN + " is trowing.");
            } else {
                plyer.sendTitle("", ChatColor.AQUA + "Player " + ChatColor.BLUE + pl.getName() + ChatColor.GREEN + " is trowing.");

            }
            plyer.getInventory().setItem(8, Items.wandOfComunication());
        }
    }
    private void newRound() {
        nextPlayerTrows();
        Player player = Bukkit.getPlayer(getPlayer());
        String title;
        if (teams.isRed(getPlayer())) {
            title = ChatColor.GREEN + "Player " + ChatColor.RED + player.getName() + ChatColor.GREEN + " is trowing.";
        } else {
            title = ChatColor.GREEN + "Player " + ChatColor.BLUE + player.getName() + ChatColor.GREEN + " is trowing.";
        }
        for (Player plyer : Bukkit.getOnlinePlayers()) {
            plyer.sendTitle("", title);
        }
    }
    private UUID getPlayer() {
        return player;
    }
    private void setPlayer(UUID player) {
        this.player = player;
    }
    private void setMovements(int movements) {
        this.movements = movements;
        ActionBar message2 = new ActionBar(ChatColor.RED + "Movements left: " + movements);
        message2.sendToPlayer(Bukkit.getPlayer(player));
        if (movements == 0) {
            nextPlayerTrows();
        }
    }
    private void teleportPlayers() { //TODO: THIS
        int iblue = 0,ired = 0;
        for (UUID uuid:teams.getBluePlayers()) {
            Player blue = Bukkit.getPlayer(uuid);
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.blue."+iblue+".world"));
            double x = plugin.getConfig().getDouble("spawn.blue."+iblue+".x");
            double y = plugin.getConfig().getDouble("spawn.blue."+iblue+".y");
            double z = plugin.getConfig().getDouble("spawn.blue."+iblue+".z");
            Location location = new Location(world,x,y,z);
            blue.teleport(location);
            blue.getLocation().setPitch(0f);
            blue.getLocation().setYaw(0f);
            iblue++;
        }
        for (UUID uuid:teams.getRedPlayers()) {
            Player red = Bukkit.getPlayer(uuid);
            World world = Bukkit.getWorld(plugin.getConfig().getString("spawn.red."+ired+".world"));
            double x = plugin.getConfig().getDouble("spawn.red."+ired+".x");
            double y = plugin.getConfig().getDouble("spawn.red."+ired+".y");
            double z = plugin.getConfig().getDouble("spawn.red."+ired+".z");
            Location location = new Location(world,x,y,z);
            red.teleport(location);
            red.getLocation().setPitch(-90f);
            red.getLocation().setYaw(0f);
            ired++;
        }
    }
    private Player getTargetPlayer(Player player, int max) {
        List<Player> possible = player.getNearbyEntities(max, max, max).stream().filter(entity -> entity instanceof Player).map(entity -> (Player) entity).filter(player::hasLineOfSight).collect(Collectors.toList());
        Ray ray = Ray.from(player);
        double d = -1;
        Player closest = null;
        for (Player player1 : possible) {
            double dis = AABB.from(player1).collidesD(ray, 0, max);
            if (dis != -1) {
                if (dis < d || d == -1) {
                    d = dis;
                    closest = player1;
                }
            }
        }
        return closest;
    }
    private void updateScoreboard() {
        if (GameState.getCurrent() == GameState.WAITING) {
            scoreboard.addLine(ChatColor.GOLD + "" + ChatColor.BOLD + "     Teams:");
            scoreboard.addLine("      " + ChatColor.RED + ChatColor.BOLD + teams.getRedPlayers().size() + ChatColor.AQUA + ChatColor.BOLD + " vs " + ChatColor.BLUE + "" + ChatColor.BLUE + teams.getBluePlayers().size());
            scoreboard.addBlankSpace();
            scoreboard.addBlankSpace();
            scoreboard.addLine(ChatColor.GOLD + "" + ChatColor.GOLD + "mc.strategygames.net");
        }
        if (GameState.getCurrent() == GameState.IN_GAME) {
            scoreboard.addLine(ChatColor.GOLD + "" + ChatColor.BOLD + "     Teams:");
            scoreboard.addLine("      " + ChatColor.RED + ChatColor.BOLD + teams.getRedPlayers().size() + ChatColor.AQUA + ChatColor.BOLD + " vs " + ChatColor.BLUE + "" + ChatColor.BLUE + teams.getBluePlayers().size());
            scoreboard.addBlankSpace();
            scoreboard.addLine(ChatColor.GOLD + "" + ChatColor.GOLD + "mc.strategygames.net");
        }
    }
}