package me.equaferrous.allstockedup.customers;

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
    private static final Vector[] ORDER_POSITIONS = new Vector[] {
            new Vector(-3,1,5),
            new Vector(-2,1,5),
            new Vector(-1,1,5),
            new Vector(0,1,5),
            new Vector(1,1,5),
            new Vector(2,1,5),
            new Vector(3,1,5),
            new Vector(4,1,5),
            new Vector(5,1,5),
            new Vector(6,1,5),
            new Vector(7,1,5),
            new Vector(8,1,5)
    };

    private final List<Customer> customerList = new ArrayList<>();
    private final List<Customer> customersWaiting = new ArrayList<>();
    private final List<Customer> customerQueue = new ArrayList<>();

    // ----------------------------

    public CustomerController() {
        for (Vector ignored : ORDER_POSITIONS) {
            customersWaiting.add(null);
        }
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

        for (int index = 0; index < customersWaiting.size(); index++) {
            if (customersWaiting.get(index) == null) {
                newCustomer.setState(CustomerState.ENTER);
                newCustomer.moveTo(ORDER_POSITIONS[index]);
                customersWaiting.set(index, newCustomer);
                return newCustomer;
            }
        }

        customerQueue.add(newCustomer);
        return newCustomer;
    }

    public void removeCustomer(Customer customer) {
        if (customerList.contains(customer)) {
            customer.delete();
            customerList.remove(customer);
            customerQueue.remove(customer);

            if (customersWaiting.contains(customer)) {
                customersWaiting.set(customersWaiting.indexOf(customer), null);
            }

            if (customerQueue.size() > 0) {
                moveCustomerQueue();
            }
        }
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    // ------------------------------

    private void moveCustomerQueue() {
        Customer customer = customerQueue.get(0);

        for (int index = 0; index < customersWaiting.size(); index++) {
            if (customersWaiting.get(index) == null) {
                customer.setState(CustomerState.ENTER);
                customer.moveTo(ORDER_POSITIONS[index]);
                customersWaiting.set(index, customer);
                customerQueue.remove(customer);
            }
        }
    }

}
