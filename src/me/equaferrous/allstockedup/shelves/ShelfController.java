package me.equaferrous.allstockedup.shelves;

import org.bukkit.Location;

import java.util.HashMap;

public class ShelfController {

    private static ShelfController instance;

    private final HashMap<Location, Shelf> shelfLocations = new HashMap<>();

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
            return newShelf;
        }
        return null;
    }

    public Shelf getShelf(Location location) {
        return shelfLocations.getOrDefault(location, null);
    }


}
