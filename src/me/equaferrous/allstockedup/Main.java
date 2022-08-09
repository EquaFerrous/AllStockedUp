package me.equaferrous.allstockedup;

import me.equaferrous.allstockedup.customers.CustomerController;
import me.equaferrous.allstockedup.events.CustomerStartsOrderEvent;
import me.equaferrous.allstockedup.events.OpenShelfEvent;
import me.equaferrous.allstockedup.shelves.ShelfController;
import me.equaferrous.allstockedup.utility.MessageSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new OpenShelfEvent(), this);
        getServer().getPluginManager().registerEvents(new CustomerStartsOrderEvent(), this);

        MessageSystem.opBroadcast("Plugin enabled.");
        ShelfController.getInstance().createShelf(new Location(Bukkit.getWorlds().get(0), 5,2,4));
        ShelfController.getInstance().createShelf(new Location(Bukkit.getWorlds().get(0), 3,2,4));

        Bukkit.getScheduler().runTaskTimer(this, this::test, 200, 200);
    }

    @Override
    public void onDisable() {
        removeEntities();
        ShelfController.getInstance().deleteAllShelves();

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

    private void test() {
        MessageSystem.opBroadcast("Spawn");
        CustomerController.getInstance().createCustomer();
    }
}
