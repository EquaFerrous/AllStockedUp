package me.equaferrous.allstockedup.customers;

import me.equaferrous.allstockedup.Main;
import me.equaferrous.allstockedup.utility.MessageSystem;
import me.equaferrous.allstockedup.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MovableVillager {

    private final static int MOVE_DELAY = 1;
    private final static double MOVE_SPEED = 2; // Blocks per second
    private final static double MOVE_AMOUNT = MOVE_SPEED * ((double) MOVE_DELAY / 20); // Blocks per tick

    private final Villager entity;
    private final List<Vector> pathList = new ArrayList<>();
    private BukkitTask moveTask;

    private Vector moveDirection;
    private Vector moveVelocity;
    private float rotationAngle;

    // --------------------------------

    public MovableVillager(Location spawnPoint) {
        entity = (Villager) spawnPoint.getWorld().spawnEntity(Utility.getBlockCentre(spawnPoint), EntityType.VILLAGER);
        entity.addScoreboardTag(Main.getPlugin().getName());
        entity.setAI(false);
        entity.setGravity(false);
        entity.setInvulnerable(true);
    }

    // --------------------------------

    public void moveTo(Vector position) {
        pathList.add(Utility.getBlockCentre(position));
        checkToMove();
    }

    public void rotate(float angle) {
        entity.setRotation(angle, 0);
    }

    // --------------------------------

    private void checkToMove() {
        if (pathList.size() > 0) {
            if (!isMoving()) {
                startMoving();
            }
        }
    }

    private boolean isMoving() {
        return moveTask != null;
    }

    private void startMoving() {
        Vector currentPos = entity.getLocation().toVector();
        Vector destPos = pathList.get(0);
        moveDirection = new Vector(destPos.getX() - currentPos.getX(), destPos.getY() - currentPos.getY(), destPos.getZ() - currentPos.getZ()).normalize();
        moveVelocity = new Vector().copy(moveDirection);
        moveVelocity.multiply(MOVE_AMOUNT);

        rotationAngle = (float) Math.toDegrees(new Vector(0,0,1).angle(moveDirection));
        if (currentPos.getX() < destPos.getX()) {
            rotationAngle = -rotationAngle;
        }
        rotate(rotationAngle);

        entity.setAI(true);
        moveTask = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(), this::moveTick, 0, MOVE_DELAY);
    }

    private void moveTick() {
        if (entity.getLocation().toVector().distance(pathList.get(0)) < MOVE_AMOUNT) {
            finishMoving();
        }

        entity.setVelocity(moveVelocity);
    }

    private void finishMoving() {
        entity.setVelocity(new Vector());
        entity.teleport(pathList.get(0).toLocation(entity.getWorld()));
        pathList.remove(0);
        moveTask.cancel();
        moveTask = null;
        entity.setAI(false);
        rotate(rotationAngle);

        if (pathList.size() > 0) {
            startMoving();
        }
    }

}
