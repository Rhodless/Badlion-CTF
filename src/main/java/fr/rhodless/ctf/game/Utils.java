package fr.rhodless.ctf.game;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.libs.ItemBuilder;
import fr.rhodless.ctf.runnable.FlagLoadingTask;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@SuppressWarnings("all")
public class Utils {

    public static void respawn(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        for(Teams team : Teams.values()) {
            if(team.getMembers().contains(player.getUniqueId())) {
                if(team == Teams.RED) {
                    player.teleport(Main.getMain().getRedSpawn());

                    player.getInventory().addItem(new ItemStack[]{
                            new ItemStack(Material.STONE_SWORD),
                            new ItemStack(Material.BOW),
                            new ItemStack(Material.ARROW, 8),
                            new ItemStack(Material.COOKED_BEEF, 64),
                            new ItemStack(Material.GOLDEN_APPLE)
                    });

                    player.getInventory().setArmorContents(new ItemStack[] {
                            new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.RED).toItemStack(),
                            new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.RED).toItemStack(),
                            new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.RED).toItemStack(),
                            new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.RED).toItemStack(),
                    });

                } else if(team == Teams.BLUE) {
                    player.teleport(Main.getMain().getBlueSpawn());

                    player.getInventory().addItem(new ItemStack[]{
                            new ItemStack(Material.STONE_SWORD),
                            new ItemStack(Material.BOW),
                            new ItemStack(Material.ARROW, 8),
                            new ItemStack(Material.COOKED_BEEF, 64),
                            new ItemStack(Material.GOLDEN_APPLE)
                    });

                    player.getInventory().setArmorContents(new ItemStack[] {
                            new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.BLUE).toItemStack(),
                            new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.BLUE).toItemStack(),
                            new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.BLUE).toItemStack(),
                            new ItemBuilder(Material.LEATHER_HELMET).setLeatherArmorColor(Color.BLUE).toItemStack(),
                    });

                }
            }
        }
    }

    public Utils deleteBanners() {

        for(World world : Bukkit.getWorlds()) {
            world.getEntities().stream().filter(e -> e instanceof Item).map(e -> (Item)e).forEach(Entity::remove);
        }

        return this;
    }

    public Utils start() {
        Main.getMain().setStatus(Status.PLAYING);

        List<UUID> list = new ArrayList<>();
        for(UUID p : Main.getMain().players) {
            Main.getUtils().preparePlayer(Bukkit.getPlayer(p));
            if(!Teams.RED.getMembers().contains(p) && !Teams.BLUE.getMembers().contains(p)) {
                list.add(p);
            }
        }

        Collections.shuffle(list);

        for(Teams team : Teams.values()) {
            Main.getMain().flagState.put(team, FlagStats.RESPAWNING);
            if(team.getMembers().size() != 5) {
                if(list.size() != 0)  {
                    team.addMember(Bukkit.getPlayer(list.get(0)));
                    list.remove(0);
                }

            }

            team.getMembers().forEach(m -> {
                if(team == Teams.RED) {
                    Bukkit.getPlayer(m).teleport(Main.getMain().getRedSpawn());
                    Utils.respawn(Bukkit.getPlayer(m));
                } else if(team == Teams.BLUE) {
                    Bukkit.getPlayer(m).teleport(Main.getMain().getBlueSpawn());
                    Utils.respawn(Bukkit.getPlayer(m));
                }
            });
        }
        new FlagLoadingTask().runTaskTimer(Main.getMain(), 20, 20);


        return this;
    }

    public Utils preparePlayer(Player player) {

        player.setMaxHealth(20);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.CREATIVE);

        return this;
    }


    public Utils giveItems(Player player, Teams team) {

        player.getInventory().setItem(4, new ItemBuilder(Material.NAME_TAG).setName("§6§LChoisir sa team").toItemStack());
        player.getInventory().setItem(8, new ItemBuilder(Material.BED).setName("§6§LRetour au Lobby").toItemStack());

        return this;

    }

}
