package me.equaferrous.allstockedup.events.customevents;

import me.equaferrous.allstockedup.customers.MovableVillager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomerFinishedMovingEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final MovableVillager villager;

    // -------------------------------

    public CustomerFinishedMovingEvent(MovableVillager villager) {
        this.villager = villager;
    }

    // --------------------------------

    public MovableVillager getVillager() {
        return villager;
    }

    // --------------------------------

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
