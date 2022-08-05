package me.equaferrous.stockedup;

import me.equaferrous.stockedup.utility.MessageSystem;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        MessageSystem.opBroadcast("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        MessageSystem.opBroadcast("Plugin disabled.");
    }

    // -------------------------------------------

    public static Plugin getPlugin() {
        return plugin;
    }
}
