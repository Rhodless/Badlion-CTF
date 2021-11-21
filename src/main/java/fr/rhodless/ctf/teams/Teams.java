package fr.rhodless.ctf.teams;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum Teams {

    BLUE("§9Bleu", "§9"),
    RED("§cRouge", "§c");

    private final String name;
    private final String prefix;
    private final List<UUID> members;

    Teams(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
        this.members = new ArrayList<>();
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void addMember(Player player) {
        members.add(player.getUniqueId());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(getPrefix()).addEntry(player.getName());
    }

    public void removeMember(Player player) {
        members.remove(player.getUniqueId());
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam(getPrefix()).removeEntry(player.getName());
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isInTeam(Player player) {
        return getMembers().contains(player.getUniqueId());
    }

    public static void createTeams() {
        for (Teams team : Teams.values()) {
            Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

            if(scoreboard.getTeam(team.getPrefix()) == null) {
                org.bukkit.scoreboard.Team t = scoreboard.registerNewTeam(team.getPrefix());
                t.setPrefix(team.getPrefix());
            }
        }
    }

}
