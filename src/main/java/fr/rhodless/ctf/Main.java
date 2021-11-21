package fr.rhodless.ctf;

import fr.rhodless.ctf.game.Flag;
import fr.rhodless.ctf.game.FlagStats;
import fr.rhodless.ctf.game.Status;
import fr.rhodless.ctf.game.Utils;
import fr.rhodless.ctf.register.Register;
import fr.rhodless.ctf.runnable.Start;
import fr.rhodless.ctf.samagames.ScoreboardManager;
import fr.rhodless.ctf.teams.Teams;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {

    private static Flag flag;
    private static Main main;
    private static ScoreboardManager scoreboardManager;
    private static Utils utils;
    public int time = 0;
    private Status status;
    private Location spawn;
    private Location redFlag;
    private Location blueFlag;
    private Location redSpawn;
    private Location blueSpawn;


    public static String prefix;
    public List<UUID> players;

    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public HashMap<Teams, FlagStats> flagState = new HashMap<>();
    public HashMap<Teams, Location> flagLocation = new HashMap<>();
    public HashMap<Teams, UUID> holding = new HashMap<>();
    public int redStatus = 10;
    public int blueStatus = 10;
    public int red = 0;
    public int blue = 0;
    public int start = 121;

    @Override
    public void onEnable() {

        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);

        saveConfig();
        this.getConfig().set("spawn", new Location(Bukkit.getWorld("world"), 0, 100, 0));
        saveConfig();

        new Start().runTaskTimer(this, 20, 20);

        flag = new Flag();
        utils = new Utils();
        new Register(this);
        status = Status.LOBBY;

        main = this;
        prefix = "§6§LCTF §8» §f";
        players = new ArrayList<>();
        spawn = (Location) this.getConfig().get("spawn");
        redFlag = (Location) this.getConfig().get("red-flag");
        blueFlag = (Location) this.getConfig().get("blue-flag");
        redSpawn = (Location) this.getConfig().get("red-spawn");
        blueSpawn = (Location) this.getConfig().get("blue-spawn");
        Teams.createTeams();
        getUtils().deleteBanners();
        scoreboardManager = new ScoreboardManager();

        for(Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getPluginManager().callEvent(new PlayerJoinEvent(player, ""));
        }

        super.onEnable();
    }

    public static ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getRedFlag() {
        return redFlag;
    }

    public Location getBlueFlag() {
        return blueFlag;
    }

    public Location getRedSpawn() {
        return redSpawn;
    }

    public Location getBlueSpawn() {
        return blueSpawn;
    }

    public static Utils getUtils() {
        return utils;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static Main getMain() {
        return main;
    }

    @Override
    public void onDisable() {

        for(Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getPluginManager().callEvent(new PlayerQuitEvent(player, ""));
            player.performCommand("play lobby");
        }

        super.onDisable();

    }

    public static Flag getFlag() {
        return flag;
    }
}
