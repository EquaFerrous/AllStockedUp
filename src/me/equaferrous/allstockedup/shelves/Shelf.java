package me.equaferrous.allstockedup.shelves;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class Shelf {

    public static final Material BLOCK_TYPE = Material.BOOKSHELF;

    private final Inventory inventory;
    private final Location location;

    // ----------------------------------

    public Shelf(Location location) {
        inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Shop Shelf");
        this.location = location;
        this.location.getBlock().setType(BLOCK_TYPE);
    }

    // ----------------------------------

    public void openInventory(HumanEntity entity) {
        entity.openInventory(inventory);
    }
}
