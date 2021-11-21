package fr.rhodless.ctf.runnable;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.game.FlagStats;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FlagLoadingTask extends BukkitRunnable {

    private boolean win = false;

    @Override
    public void run() {

        if(win) return;

        if(Main.getMain().blueStatus != 0) {
            Main.getMain().blueStatus--;
        }

        if(Main.getMain().time == 15*60) {
            for(Player pls : Bukkit.getOnlinePlayers()) {
                pls.setGameMode(GameMode.CREATIVE);
                pls.getInventory().clear();
                if(Main.getMain().blue > Main.getMain().red) {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Main.prefix + "§fLa team §9Bleu §fremporte cette partie.");
                    Bukkit.broadcastMessage(" ");
                    win = true;
                } else if(Main.getMain().blue < Main.getMain().red) {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Main.prefix + "§fLa team §cRouge §fremporte cette partie.");
                    Bukkit.broadcastMessage(" ");
                    win = true;
                } else {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(Main.prefix + "§fPersonne ne remporte cette partie, §6égalité parfaite§f.");
                    Bukkit.broadcastMessage(" ");
                    win = true;
                }
                this.cancel();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Main.getMain().getServer().reload();
                    }
                }.runTaskLater(Main.getMain(), 30*20);
            }
        }

        if(Main.getMain().redStatus != 0) {
            Main.getMain().redStatus--;
        }

        if(Main.getMain().flagState.get(Teams.RED) == FlagStats.RESPAWNING && Main.getMain().redStatus == 0) {
            Main.getFlag().setFlag(Teams.RED, Main.getMain().getRedFlag());
            Main.getMain().flagLocation.put(Teams.RED, Main.getMain().getRedFlag());
            Main.getMain().flagState.put(Teams.RED, FlagStats.AT_HOME);
            Bukkit.broadcastMessage(Main.prefix + "§fLe drapeau §cRouge§f a été posé");
        }

        if(Main.getMain().flagState.get(Teams.RED) == FlagStats.IS_DISAPPEARING && Main.getMain().redStatus == 0) {
            for(Entity entity : Bukkit.getWorld("world").getNearbyEntities(Main.getMain().flagLocation.get(Teams.RED), 5, 5, 5)) {
                if(entity instanceof Item || entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
            Main.getMain().flagLocation.put(Teams.RED, Main.getMain().getRedFlag());
            Main.getMain().flagState.put(Teams.RED, FlagStats.RESPAWNING);
            Bukkit.broadcastMessage(Main.prefix + "§cLe drapeau des rouges à disparu, il respawnera dans 10 secondes.");
            Main.getMain().redStatus = 10;
        }

        if(Main.getMain().flagState.get(Teams.BLUE) == FlagStats.IS_DISAPPEARING && Main.getMain().blueStatus == 0) {
            for(Entity entity : Bukkit.getWorld("world").getNearbyEntities(Main.getMain().flagLocation.get(Teams.BLUE), 5, 5, 5)) {
                if(entity instanceof Item || entity instanceof ArmorStand) {
                    entity.remove();
                }
            }
            Main.getMain().flagLocation.put(Teams.BLUE, Main.getMain().getBlueFlag());
            Main.getMain().flagState.put(Teams.BLUE, FlagStats.RESPAWNING);
            Bukkit.broadcastMessage(Main.prefix + "§9Le drapeau des bleus à disparu, il respawnera dans 10 secondes.");
            Main.getMain().blueStatus = 10;
        }

        if(Main.getMain().flagState.get(Teams.BLUE) == FlagStats.RESPAWNING && Main.getMain().blueStatus == 0) {
            Main.getFlag().setFlag(Teams.BLUE, Main.getMain().getBlueFlag());
            Main.getMain().flagLocation.put(Teams.BLUE, Main.getMain().getBlueFlag());
            Main.getMain().flagState.put(Teams.BLUE, FlagStats.AT_HOME);
            Bukkit.broadcastMessage(Main.prefix + "§fLe drapeau §9Bleu§f a été posé");
        }

        Main.getMain().time++;

    }
}
