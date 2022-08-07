package me.equaferrous.allstockedup;

import me.equaferrous.allstockedup.customers.CustomerController;
import me.equaferrous.allstockedup.utility.MessageSystem;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        MessageSystem.opBroadcast("Plugin enabled.");
        CustomerController.getInstance().createCustomer();
    }

    @Override
    public void onDisable() {
        removeEntities();

        MessageSystem.opBroadcast("Plugin disabled.");
    }

    // -------------------------------------------

    public static Plugin getPlugin() {
        return plugin;
    }

    // -------------------------------------------

    private void removeEntities() {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getScoreboardTags().contains(getPlugin().getName())) {
                    entity.remove();
                }
            }
        }
    }
}
