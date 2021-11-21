package fr.rhodless.ctf.game;

import fr.rhodless.ctf.libs.ItemBuilder;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;

public class Flag {

    public void setFlag(Teams team, Location location) {

        double y = location.getY();
        double x = location.getX();

        spawnItem(location, (y + 0.4), Material.WOOD, 0, team);
        spawnItem(location, (y + 0.8), Material.WOOD, 0, team);
        spawnItem(location, (y + 1.2), Material.WOOD, 0, team);
        spawnItem(location, (y + 1.6), Material.WOOD, 0, team);
        spawnItem(location, (y + 2.0), Material.WOOD, 0, team);
        spawnItem(location, (y + 2.4), Material.WOOD, 0, team);
        spawnItem(location, (y + 2.8), Material.WOOD, 0, team);
        spawnItem(location, (y + 3.2), Material.WOOD, 0, team);
        spawnItem(location, (y + 3.6), Material.WOOD, 0, team);
        spawnItem(location, (y + 4.0), Material.WOOD, 0, team);

        byte dye;

        if(team == Teams.BLUE) {
            dye = 11;
        } else {
            dye = 14;
        }

        spawnItem(location, (x + 0.4), (y + 4), Material.WOOL, dye, team);
        spawnItem(location, (x + 0.8), (y + 4), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.2), (y + 4), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.6), (y + 4), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.0), (y + 4), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.4), (y + 4), Material.WOOL, dye, team);

        spawnItem(location, (x + 0.4), (y + 3.6), Material.WOOL, dye, team);
        spawnItem(location, (x + 0.8), (y + 3.6), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.2), (y + 3.6), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.6), (y + 3.6), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.0), (y + 3.6), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.4), (y + 3.6), Material.WOOL, dye, team);

        spawnItem(location, (x + 0.4), (y + 3.2), Material.WOOL, dye, team);
        spawnItem(location, (x + 0.8), (y + 3.2), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.2), (y + 3.2), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.6), (y + 3.2), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.0), (y + 3.2), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.4), (y + 3.2), Material.WOOL, dye, team);

        spawnItem(location, (x + 0.4), (y + 2.8), Material.WOOL, dye, team);
        spawnItem(location, (x + 0.8), (y + 2.8), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.2), (y + 2.8), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.6), (y + 2.8), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.0), (y + 2.8), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.4), (y + 2.8), Material.WOOL, dye, team);

        spawnItem(location, (x + 0.4), (y + 2.4), Material.WOOL, dye, team);
        spawnItem(location, (x + 0.8), (y + 2.4), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.2), (y + 2.4), Material.WOOL, dye, team);
        spawnItem(location, (x + 1.6), (y + 2.4), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.0), (y + 2.4), Material.WOOL, dye, team);
        spawnItem(location, (x + 2.4), (y + 2.4), Material.WOOL, dye, team);

    }


    void spawnItem(Location location, double y, Material mat, int data, Teams team) {
        double x = location.getX();
        double z = location.getZ();
        World world = location.getWorld();
        Item item = Bukkit.getWorld("world").dropItem(new Location(world, x, y, z), new ItemBuilder(mat, 1, (byte) data).setName("" + (Math.random() * 134324)).toItemStack());
        ArmorStand armorStand = (ArmorStand) spawnArmorStand(new Location(world, x, y, z));
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setPassenger(item);
    }

    void spawnItem(Location location, double x, double y, Material mat, int data, Teams team) {
        spawnItem(new Location(location.getWorld(), x, y, location.getZ()), y, mat, data, team);
    }


    Entity spawnArmorStand(Location location) {
        return location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
    }


}
