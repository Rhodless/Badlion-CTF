package fr.rhodless.ctf.events;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.game.FlagStats;
import fr.rhodless.ctf.game.Menus;
import fr.rhodless.ctf.game.Status;
import fr.rhodless.ctf.game.Utils;
import fr.rhodless.ctf.libs.ItemBuilder;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

@SuppressWarnings({"unused"})
public class PlayersEvent implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setKeepInventory(true);

        if(event.getEntity().getKiller() != null) {
            event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
        }

        Player player = event.getEntity();

        if(player.getLocation().getY() <= 50) {
            player.teleport(Main.getMain().getSpawn());
        }

        player.setGameMode(GameMode.SPECTATOR);

        LeatherArmorMeta l = (LeatherArmorMeta) player.getInventory().getHelmet().getItemMeta();
        if(Main.getMain().holding.get(Teams.BLUE) == player.getUniqueId()) {
            Main.getMain().holding.put(Teams.BLUE, null);
            Main.getMain().flagState.put(Teams.RED, FlagStats.IS_DISAPPEARING);
            Bukkit.broadcastMessage(Main.prefix + "§9Le drapeau des bleus respawnera dans 10 secondes.");
            Bukkit.broadcastMessage(Main.prefix + "§c" + player.getName() + " §fa drop le drapeau.");
            Main.getFlag().setFlag(Teams.RED, new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()-2, player.getLocation().getZ()));
            Main.getMain().redStatus = 10;
            Main.getMain().flagLocation.put(Teams.RED, player.getLocation());
        } else if(Main.getMain().holding.get(Teams.RED) == player.getUniqueId()) {
            Main.getMain().holding.put(Teams.RED, null);
            Main.getMain().flagState.put(Teams.BLUE, FlagStats.IS_DISAPPEARING);
            Bukkit.broadcastMessage(Main.prefix + "§cLe drapeau des rouges respawnera dans 10 secondes.");
            Bukkit.broadcastMessage(Main.prefix + "§9" + player.getName() + " §fa drop le drapeau.");
            Main.getFlag().setFlag(Teams.BLUE, new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY()-2, player.getLocation().getZ()));
            Main.getMain().blueStatus = 10;
            Main.getMain().flagLocation.put(Teams.BLUE, player.getLocation());
        }


            Location location = player.getLocation();
        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
                player.teleport(location);
                player.sendMessage(Main.prefix + "§fVous allez respawn dans §65 secondes§f.");
            }
        }.runTaskLater(Main.getMain(), 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                Utils.respawn(player);
            }
        }.runTaskLater(Main.getMain(), 5*20);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(Main.getMain().holding.get(Teams.RED) == event.getEntity().getUniqueId() || Main.getMain().holding.get(Teams.BLUE) == event.getEntity().getUniqueId()) {
            if(Main.getMain().flagLocation.get(Teams.RED).distance(event.getEntity().getLocation()) <= 5 || Main.getMain().flagLocation.get(Teams.BLUE).distance(event.getEntity().getLocation()) <= 5) {
                event.setDamage(0.0001);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if(player.getGameMode() != GameMode.SPECTATOR && Main.getMain().flagState.get(Teams.RED) != FlagStats.RESPAWNING && player.getLocation().distance(Main.getMain().flagLocation.get(Teams.RED)) <= 2 && Main.getMain().holding.get(Teams.BLUE) == null) {
            if(Teams.BLUE.isInTeam(player)) {
                Bukkit.broadcastMessage(Main.prefix + "§9" + player.getName() + " §fa capturé le drapeau des §cRouge§f.");
                Main.getMain().flagState.put(Teams.RED, FlagStats.AT_HOME);
                Main.getMain().redStatus = 0;
                Main.getMain().holding.put(Teams.BLUE, player.getUniqueId());
                for(Entity entity : player.getNearbyEntities(4, 4, 4)) {
                    if(entity instanceof Item || entity instanceof ArmorStand) {
                        entity.remove();
                    }
                }
                player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.ORANGE).toItemStack());
            }
        }

        if(Main.getMain().flagState.get(Teams.RED) != FlagStats.RESPAWNING && player.getLocation().distance(Main.getMain().getBlueFlag()) <= 2 && Main.getMain().holding.get(Teams.BLUE) != null) {
            if(Teams.BLUE.isInTeam(player) && Main.getMain().holding.get(Teams.BLUE) == player.getUniqueId()) {
                Bukkit.broadcastMessage(Main.prefix + "§9" + player.getName() + " §fa marqué un point pour les §9Bleus§f.");
                Main.getMain().holding.put(Teams.BLUE, null);
                Main.getMain().blue++;
                player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLUE).toItemStack());
                Main.getMain().flagLocation.put(Teams.RED, Main.getMain().getRedFlag());
                Main.getMain().flagState.put(Teams.RED, FlagStats.RESPAWNING);
                Bukkit.broadcastMessage(Main.prefix + "§9Le drapeau des bleus respawnera dans 10 secondes.");
                Main.getMain().redStatus = 10;
            }
        }

        if(player.getGameMode() != GameMode.SPECTATOR && Main.getMain().flagState.get(Teams.BLUE) != FlagStats.RESPAWNING && player.getLocation().distance(Main.getMain().flagLocation.get(Teams.BLUE)) <= 2 && Main.getMain().holding.get(Teams.RED) == null) {
            if(Teams.RED.isInTeam(player)) {
                Bukkit.broadcastMessage(Main.prefix + "§c" + player.getName() + " §fa capturé le drapeau des §9Bleus§f.");
                Main.getMain().flagState.put(Teams.BLUE, FlagStats.AT_HOME);
                Main.getMain().blueStatus = 0;
                Main.getMain().holding.put(Teams.RED, player.getUniqueId());
                for(Entity entity : player.getNearbyEntities(4, 4, 4)) {
                    if(entity instanceof Item || entity instanceof ArmorStand) {
                        entity.remove();
                    }
                }
                player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.ORANGE).toItemStack());
            }
        }

        if(Main.getMain().flagState.get(Teams.BLUE) != FlagStats.RESPAWNING && player.getLocation().distance(Main.getMain().getRedFlag()) <= 2 && Main.getMain().holding.get(Teams.RED) != null) {
            if(Teams.RED.isInTeam(player) && Main.getMain().holding.get(Teams.RED) == player.getUniqueId()) {
                Bukkit.broadcastMessage(Main.prefix + "§c" + player.getName() + " §fa marqué un point pour les §cRouges§f.");
                Main.getMain().holding.put(Teams.RED, null);
                Main.getMain().red++;
                player.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.RED).toItemStack());
                Main.getMain().flagLocation.put(Teams.BLUE, Main.getMain().getRedFlag());
                Main.getMain().flagState.put(Teams.BLUE, FlagStats.RESPAWNING);
                Bukkit.broadcastMessage(Main.prefix + "§cLe drapeau des rouges respawnera dans 10 secondes.");
                Main.getMain().blueStatus = 10;
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if(event.getItem() == null) return;

        event.setCancelled(player.getGameMode() == GameMode.CREATIVE);

        event.setCancelled(Main.getMain().getStatus() == Status.LOBBY);

        switch(event.getItem().getType()) {
            case BED:
                player.performCommand("play lobby");
                break;
            case NAME_TAG:
                Menus.chooseTeam(player);
                break;
        }
    }

    @EventHandler
    public void onDespawn(ItemDespawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();

        if(Main.getMain().getStatus().equals(Status.LOBBY)) {
            Main.getMain().players.add(player.getUniqueId());
            Bukkit.broadcastMessage(Main.prefix + "§6" + player.getName() + " §fa rejoint la game §8(§6" + Main.getMain().players.size() + "§8/§610§8)");
            Main.getUtils().preparePlayer(player).giveItems(player, null);
            Bukkit.getScheduler().runTaskLater(Main.getMain(),() -> player.teleport(Main.getMain().getSpawn()), 5);
            for(Team team : Main.getMain().getServer().getScoreboardManager().getMainScoreboard().getTeams()) {
                team.removeEntry(player.getName());
            }
        }
        Main.getScoreboardManager().onLogin(player);

        if(Main.getMain().getStatus() == Status.LOBBY) {
            if(Bukkit.getOnlinePlayers().size() == 4) {
                Main.getMain().start = 120;
            }

            if(Bukkit.getOnlinePlayers().size() == 6) {
                if(Main.getMain().start > 60) {
                    Main.getMain().start = 60;
                }
            }

            if(Bukkit.getOnlinePlayers().size() == 8) {
                if(Main.getMain().start > 20) {
                    Main.getMain().start = 20;
                }
            }
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Main.getScoreboardManager().onLogout(event.getPlayer());
        event.setQuitMessage(null);
        if(Main.getMain().getStatus().equals(Status.LOBBY)) {
            Main.getMain().players.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(event.getEntity() instanceof Bat);
    }

    @EventHandler
    public void onEntityDamage(EntityInteractEvent event) {
        event.setCancelled(event.getEntity() instanceof Bat);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(EntityDamageEvent event) {
        event.setCancelled(Main.getMain().getStatus() == Status.LOBBY);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(Main.getMain().getStatus() == Status.LOBBY);
        event.setCancelled(event.getWhoClicked().getGameMode() == GameMode.CREATIVE);
    }

}
