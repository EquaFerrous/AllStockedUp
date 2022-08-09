package me.equaferrous.allstockedup.customers;

import me.equaferrous.allstockedup.Main;
import me.equaferrous.allstockedup.shelves.ShelfController;
import me.equaferrous.allstockedup.utility.MessageSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashMap;


public class Customer extends MovableVillager{

    private CustomerState state;
    private final HashMap<Material, Integer> order = new HashMap<>();

    // -------------------------------------

    public Customer(Location spawnPoint) {
        super(spawnPoint);
        order.put(Material.IRON_INGOT, 5);
    }

    // -------------------------------------

    public void setState(CustomerState newState) {
        if (newState.equals(state)) {
            return;
        }
        state = newState;

        switch (state) {
            case ENTER -> {
                moveTo(CustomerController.DOOR_POS);
            }
            case ORDER -> {
            }
            case LEFT_POSITIVE -> {
                MessageSystem.opBroadcast("Customer complete.");
                CustomerController.getInstance().removeCustomer(this);
            }
            case lEFT_NEGATIVE -> {
                MessageSystem.opBroadcast("Customer failed.");
                CustomerController.getInstance().removeCustomer(this);
            }
        }
    }

    public void delete() {
        super.delete();
    }

    public CustomerState getState() {
        return state;
    }

    public void checkToCompleteOrder() {
        if (completeOrder()) {
            setState(CustomerState.LEFT_POSITIVE);
        }
    }

    // -------------------------------------

    private boolean completeOrder() {
        HashMap<Material, Integer> orderDict = new HashMap<>(order);
        boolean orderComplete = true;
        for (Material material : orderDict.keySet()) {
            if (ShelfController.getInstance().removeItems(material, order.get(material))) {
                order.remove(material);
            }
            else {
                orderComplete = false;
            }
        }
        return  orderComplete;
    }
}
