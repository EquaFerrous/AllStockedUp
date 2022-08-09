package me.equaferrous.allstockedup.customers;

import me.equaferrous.allstockedup.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitTask;
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
    private final List<Customer> customerPositions = new ArrayList<>();
    private final List<Customer> customerQueue = new ArrayList<>();
    private final List<Customer> customersOrdering = new ArrayList<>();

    private final BukkitTask checkCustomerOrdersTask;

    // ----------------------------

    public CustomerController() {
        for (Vector ignored : ORDER_POSITIONS) {
            customerPositions.add(null);
        }

        checkCustomerOrdersTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::checkCustomerOrdersTask, 20, 20);
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

        for (int index = 0; index < customerPositions.size(); index++) {
            if (customerPositions.get(index) == null) {
                newCustomer.setState(CustomerState.ENTER);
                newCustomer.moveTo(ORDER_POSITIONS[index]);
                customerPositions.set(index, newCustomer);
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
            customersOrdering.remove(customer);

            if (customerPositions.contains(customer)) {
                customerPositions.set(customerPositions.indexOf(customer), null);
            }

            if (customerQueue.size() > 0) {
                moveCustomerQueue();
            }
        }
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void startCustomerOrdering(Customer customer) {
        customer.setState(CustomerState.ORDER);
        customersOrdering.add(customer);
    }

    // ------------------------------

    private void moveCustomerQueue() {
        Customer customer = customerQueue.get(0);

        for (int index = 0; index < customerPositions.size(); index++) {
            if (customerPositions.get(index) == null) {
                customer.setState(CustomerState.ENTER);
                customer.moveTo(ORDER_POSITIONS[index]);
                customerPositions.set(index, customer);
                customerQueue.remove(customer);
            }
        }
    }

    private void checkCustomerOrdersTask() {
        List<Customer> tempList = new ArrayList<>(customersOrdering);
        for (Customer customer : tempList) {
            customer.checkToCompleteOrder();
        }
    }

}
