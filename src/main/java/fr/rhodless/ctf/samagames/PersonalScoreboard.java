package fr.rhodless.ctf.samagames;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.game.Status;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class PersonalScoreboard {
    private final Player p;
    private final UUID uuid;
    private final ObjectiveSign e;
 
    PersonalScoreboard(Player player){
        this.p = player;
        uuid = player.getUniqueId();
        e = new ObjectiveSign("sidebar", ChatColor.translateAlternateColorCodes('&', "&6&lGloriaMC &7┃ &fCTF"));
 
        reloadData();
        e.addReceiver(player);
    }
 
    public void reloadData(){}
 
    public void setLines(String ip){

        if(Main.getMain().getStatus() == Status.LOBBY) {
            e.add(0, "§7§m-------------------§1");
            e.add(1, "§8» §fJoueurs: §6" + Main.getMain().players.size() + "/10");
            e.add(2, "");
            e.add(3, "§7play.gloriamc.eu");
            e.add(4, "§7§m-------------------§2");
        } else {
            e.add(0, "§7§m-------------------§3");
            e.add(1, "§8» §fScores: §9" + Main.getMain().blue + " §8- §c" + Main.getMain().red);
            e.add(2, "§8» §fTemps: §6" + (Main.getMain().time <= 0 ? "0min 00s " : Main.getMain().time / 60 + "min " + new SimpleDateFormat("ss").format(Main.getMain().time * 1000)) + "s");
            e.add(3, "§1");
            e.add(4, "§8» §fBleu: §6" + Main.getMain().blueStatus + "§9 " + Main.getMain().flagState.get(Teams.BLUE).getName());
            e.add(5, "§8» §fRed: §6" + Main.getMain().redStatus + "§c " + Main.getMain().flagState.get(Teams.RED).getName());
            e.add(6, "");
            e.add(7, "§7play.gloriamc.eu");
            e.add(8, "§7§m-------------------§4");
        }

        e.updateLines();
    }
 
    public void onLogout(){
        e.removeReceiver(Bukkit.getServer().getOfflinePlayer(uuid));
    }
}