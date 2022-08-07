package me.equaferrous.allstockedup.events;

import me.equaferrous.allstockedup.shelves.Shelf;
import me.equaferrous.allstockedup.shelves.ShelfController;
import me.equaferrous.allstockedup.utility.MessageSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class OpenShelfEvent implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (event.getClickedBlock().getType().equals(Shelf.BLOCK_TYPE)) {
            Shelf shelf = ShelfController.getInstance().getShelf(event.getClickedBlock().getLocation());
            if (shelf != null) {
                shelf.openInventory(event.getPlayer());
            }
        }
    }
}
