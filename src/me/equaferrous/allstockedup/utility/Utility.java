package me.equaferrous.allstockedup.utility;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Utility {

    public static Location getBlockLocation(Location location) {
        return new Location(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static Vector getBlockLocation(Vector vector) {
        return new Vector(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public static Location getBlockCentre(Location location) {
        return getBlockLocation(location).add(0.5, 0, 0.5);
    }

    public static Vector getBlockCentre(Vector vector) {
        return getBlockLocation(vector).add(new Vector(0.5,0,0.5));
    }

    public static double truncate(double num, int decimalPlaces) {
        int mult = 10 * decimalPlaces;
        return Math.floor(num * mult) / mult;
    }
}
