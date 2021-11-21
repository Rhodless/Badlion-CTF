package fr.rhodless.ctf.game;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.libs.ItemBuilder;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menus implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getInventory().getName().equalsIgnoreCase("§8Team")) {
            switch(event.getSlot()) {
                case 0:
                    Teams.RED.removeMember(player);
                    Teams.BLUE.removeMember(player);
                    chooseTeam(player);
                    break;
                case 3:
                    if(Teams.BLUE.getMembers().size() != 5) {
                        Teams.RED.removeMember(player);
                        Teams.BLUE.removeMember(player);
                        Teams.BLUE.addMember(player);
                        chooseTeam(player);
                        break;
                    }
                case 5:
                    if(Teams.RED.getMembers().size() != 5) {
                        Teams.RED.removeMember(player);
                        Teams.BLUE.removeMember(player);
                        Teams.RED.addMember(player);
                        chooseTeam(player);
                        break;
                    }
            }
        }
    }

    public static void chooseTeam(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§8Team");

        List<String> randomLore = new ArrayList<>();
        for(UUID p : Main.getMain().players) {
            if(!Teams.RED.getMembers().contains(p) && !Teams.BLUE.getMembers().contains(p)) {
                randomLore.add(" §8» §7" + Bukkit.getPlayer(p).getName());
            }
        }

        List<String> blueLore = new ArrayList<>();
        for(UUID id : Teams.BLUE.getMembers()) {
            blueLore.add(" §8» §9" + Bukkit.getPlayer(id).getName());
        }

        List<String> redLore = new ArrayList<>();
        for(UUID id : Teams.RED.getMembers()) {
            redLore.add(" §8» §c" + Bukkit.getPlayer(id).getName());
        }

        inventory.setItem(0, new ItemBuilder(Material.LEATHER_HELMET).setName("§f§lRandom Team").setLore(randomLore).toItemStack());
        inventory.setItem(3, new ItemBuilder(Material.LEATHER_HELMET).addLeatherMeta(Color.BLUE)
                .setName("§9§LBleu §8(§6" + Teams.BLUE.getMembers().size() + "§8/§65§8)")
                .setLore(blueLore)
                .toItemStack());

        inventory.setItem(5, new ItemBuilder(Material.LEATHER_HELMET).addLeatherMeta(Color.RED)
                .setName("§c§LRouge §8(§6" + Teams.RED.getMembers().size() + "§8/§65§8)")
                .setLore(redLore)
                .toItemStack());

        player.openInventory(inventory);
    }
}
