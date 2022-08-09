package me.equaferrous.allstockedup.events;

import me.equaferrous.allstockedup.customers.Customer;
import me.equaferrous.allstockedup.customers.CustomerController;
import me.equaferrous.allstockedup.customers.CustomerState;
import me.equaferrous.allstockedup.customers.MovableVillager;
import me.equaferrous.allstockedup.events.customevents.CustomerFinishedMovingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomerStartsOrderEvent implements Listener {

    @EventHandler
    public void onVillagerStopMoving(CustomerFinishedMovingEvent event) {
        MovableVillager villager = event.getVillager();
        for (Customer customer : CustomerController.getInstance().getCustomerList()) {
            if (customer.equals(villager) && customer.getState() == CustomerState.ENTER) {
                customer.setState(CustomerState.ORDER);
            }
        }
    }
}
