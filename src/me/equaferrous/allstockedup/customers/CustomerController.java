package me.equaferrous.allstockedup.customers;

import me.equaferrous.allstockedup.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerController {

    private static CustomerController instance;
    public static final Vector[] SPAWN_POINTS = new Vector[] {new Vector(-7,1,11), new Vector(11,1,11)};
    public static final Vector DOOR_POS = new Vector(2,1,11);
    public static final Vector COUNTER_POS = new Vector(2,1,5);

    private List<Customer> customerList = new ArrayList<>();

    // ----------------------------

    public CustomerController() {
    }

    // -----------------------------

    public static CustomerController getInstance() {
        if (instance == null) {
            instance = new CustomerController();
        }
        return instance;
    }

    // ------------------------------

    public Customer createCustomer() {
        Location spawnPoint = SPAWN_POINTS[new Random().nextInt(SPAWN_POINTS.length)].toLocation(Bukkit.getWorlds().get(0));
        Customer newCustomer = new Customer(spawnPoint);
        customerList.add(newCustomer);

        return newCustomer;
    }

    public void removeCustomer(Customer customer) {
        if (customerList.contains(customer)) {
            customer.delete();
            customerList.remove(customer);
        }
    }
}
