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

    private BukkitTask orderTask;

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
                orderTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::checkToCompleteOrder, 20, 20);
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
        if (orderTask != null) {
            orderTask.cancel();
        }
    }

    public CustomerState getState() {
        return state;
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

    private void checkToCompleteOrder() {
        if (completeOrder()) {
            orderTask.cancel();
            orderTask = null;
            setState(CustomerState.LEFT_POSITIVE);
        }
    }


}
