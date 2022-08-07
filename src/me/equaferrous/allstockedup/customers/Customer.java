package me.equaferrous.allstockedup.customers;

import me.equaferrous.allstockedup.Main;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Customer extends MovableVillager{

    private final List<ItemStack> order = new ArrayList<>();

    // -------------------------------------

    public Customer(Location spawnPoint) {
        super(spawnPoint);
    }

    // -------------------------------------
}
