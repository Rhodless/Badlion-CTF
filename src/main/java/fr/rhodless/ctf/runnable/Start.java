package fr.rhodless.ctf.runnable;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.libs.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Start extends BukkitRunnable {

    @Override
    public void run() {

        if(Main.getMain().start != 121) {
            Main.getMain().start--;

            if(Main.getMain().start == 90) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e90 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 60) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e60 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 30) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e30 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }
            
            if(Main.getMain().start == 10) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e10 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 5) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e5 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 4) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §e4 §8«", "§7Tenez vous prêt");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 3) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §63 §8«", "§7A vos marques");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 2) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §c2 §8«", "§7Prêt ?");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 1) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §41 §8«", "§7Vous ête sûr ?");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

            if(Main.getMain().start == 0) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    Title.sendTitle(player, 10, 30, 10, "§8» §40 §8«", "§7Partez");
                    player.sendMessage("§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
                Main.getUtils().start();
            }

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(Main.getMain().start >= 0) {
                    Title.sendActionBar(player,  "§8» §7Début de la partie dans §6" + Main.getMain().start + " §7secondes.");
                }
            }

        }

    }
}
