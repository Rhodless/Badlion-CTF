package fr.rhodless.ctf.register;

import fr.rhodless.ctf.Main;
import fr.rhodless.ctf.commands.FlagCommand;
import fr.rhodless.ctf.events.PlayersEvent;
import fr.rhodless.ctf.game.Menus;

public class Register  {

    public Register(Main main) {
        main.getCommand("ctf").setExecutor(new FlagCommand());
        main.getServer().getPluginManager().registerEvents(new PlayersEvent(), main);
        main.getServer().getPluginManager().registerEvents(new Menus(), main);
    }

}
