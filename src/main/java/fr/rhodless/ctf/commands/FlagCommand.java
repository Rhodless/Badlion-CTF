package fr.rhodless.ctf.commands;

import fr.rhodless.ctf.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlagCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.DARK_RED + "This command can only be used by a player");
            return true;
        }

        if(args.length == 0) {
            return true;
        }

        Player player = (Player) commandSender;

        if(args[0].equalsIgnoreCase("setspawn")) {
            Main.getMain().getConfig().set("spawn", player.getLocation());
            player.sendMessage(Main.prefix + "§6Spawn updated");
            Main.getMain().saveConfig();
        }

        if(args[0].equalsIgnoreCase("setblueflag")) {
            Main.getMain().getConfig().set("blue-flag", player.getLocation());
            player.sendMessage(Main.prefix + "§6Blue flag updated");
            Main.getMain().saveConfig();
        }

        if(args[0].equalsIgnoreCase("setredflag")) {
            Main.getMain().getConfig().set("red-flag", player.getLocation());
            player.sendMessage(Main.prefix + "§6Red flag updated");
            Main.getMain().saveConfig();
        }

        if(args[0].equalsIgnoreCase("setredspawn")) {
            Main.getMain().getConfig().set("red-spawn", player.getLocation());
            player.sendMessage(Main.prefix + "§6Red flag updated");
            Main.getMain().saveConfig();
        }

        if(args[0].equalsIgnoreCase("setbluespawn")) {
            Main.getMain().getConfig().set("blue-spawn", player.getLocation());
            player.sendMessage(Main.prefix + "§6Blue spawn updated");
            Main.getMain().saveConfig();
        }

        if(args[0].equalsIgnoreCase("start")) {
            Main.getMain().start = 10;
        }

        return false;
    }
}
