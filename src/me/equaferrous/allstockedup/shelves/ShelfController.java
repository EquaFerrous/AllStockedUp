package me.equaferrous.allstockedup.shelves;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShelfController {

    private static ShelfController instance;

    private final HashMap<Location, Shelf> shelfLocations = new HashMap<>();
    private final List<Shelf> allShelves = new ArrayList<>();

    // ----------------------------

    private ShelfController() {
    }

    // -----------------------------

    public static ShelfController getInstance() {
        if (instance == null) {
            instance = new ShelfController();
        }
        return instance;
    }

    // ------------------------------

    public Shelf createShelf(Location location) {
        if (!shelfLocations.containsKey(location)) {
            Shelf newShelf = new Shelf(location);
            shelfLocations.put(location, newShelf);
            allShelves.add(newShelf);
            return newShelf;
        }
        return null;
    }

    public void removeShelf(Shelf shelf) {
        if (!allShelves.contains(shelf)) {
            return;
        }

        allShelves.remove(shelf);
        shelfLocations.remove(shelf.getLocation());
        shelf.delete();
    }

    public void deleteAllShelves() {
        List<Shelf> shelfList = new ArrayList<>(allShelves);
        for (Shelf shelf : shelfList) {
            removeShelf(shelf);
        }
    }

    public Shelf getShelf(Location location) {
        return shelfLocations.getOrDefault(location, null);
    }

    public int getItemAmounts(Material material) {
        int amount = 0;
        for (Shelf shelf : allShelves) {
            amount += shelf.getItemAmount(material);
        }
        return amount;
    }

    public boolean removeItems(Material material, int amount) {
        if (getItemAmounts(material) < amount) {
            return false;
        }

        int removeAmount = amount;
        for (Shelf shelf : allShelves) {
            int shelfAmount = shelf.getItemAmount(material);
            if (shelfAmount < removeAmount) {
                shelf.removeItems(material, shelfAmount);
                removeAmount -= shelfAmount;
            }
            else {
                shelf.removeItems(material, removeAmount);
                break;
            }
        }
        return true;
    }


}
