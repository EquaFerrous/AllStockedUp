package me.equaferrous.allstockedup.shelves;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

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

    public int getItemAmount(Material material) {
        HashMap<Integer, ? extends ItemStack> itemStacks = inventory.all(material);
        int storedAmount = 0;
        for (ItemStack itemStack : itemStacks.values()) {
            storedAmount += itemStack.getAmount();
        }
        return storedAmount;
    }

    public boolean removeItems(Material material, int amount) {
        if (getItemAmount(material) < amount) {
            return false;
        }

        HashMap<Integer, ? extends ItemStack> itemStacks = inventory.all(material);
        int removeAmount = amount;
        for (Map.Entry<Integer, ? extends ItemStack> invSpace : itemStacks.entrySet()) {
            ItemStack stack = invSpace.getValue();
            if (stack.getAmount() < removeAmount) {
                inventory.clear(invSpace.getKey());
            }
            else {
                stack.setAmount(stack.getAmount() - removeAmount);
                break;
            }
            removeAmount -= stack.getAmount();
        }
        return true;
    }

    public Location getLocation() {
        return location;
    }

    public void delete() {
        location.getBlock().setType(Material.AIR);
    }
}
