package fr.rhodless.ctf.game;

public enum FlagStats {

    IS_DISAPPEARING("⚐"),
    AT_HOME("⚑"),
    RESPAWNING("×");

    private String name;

    FlagStats(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
