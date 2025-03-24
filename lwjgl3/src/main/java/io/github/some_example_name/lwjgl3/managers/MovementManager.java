package io.github.some_example_name.lwjgl3.managers;

import io.github.some_example_name.lwjgl3.entities.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovementManager {
    private final List<Entity> entities = new ArrayList<>();
    private final Map<Entity, Float> originalSpeeds = new HashMap<>(); // ✅ Store original speeds
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean speedBoostActive = false;

    // ✅ Add entity to be managed
    public void addEntity(Entity entity) {
        entities.add(entity);
        originalSpeeds.put(entity, entity.getSpeed()); // ✅ Save the original speed of each entity
    }

    // ✅ Update movement for AI-controlled entities
    public void updateAiEntitiesMovement() {
        for (Entity entity : entities) {
            if (speedBoostActive) {
                entity.setSpeed(originalSpeeds.get(entity) * 2.0f); // ✅ Temporarily double speed
            } else {
                entity.setSpeed(originalSpeeds.get(entity)); // ✅ Reset to original speed
            }
            entity.move();
        }
    }

    // ✅ Activate Speed Boost for 5 Seconds
    public void activateSpeedBoost() {
        if (!speedBoostActive) {
            speedBoostActive = true;
            System.out.println("AI Speed Boost Activated!");

            // ✅ Run a separate thread to revert speed after 5 seconds
            new Thread(() -> {
                try {
                    Thread.sleep(100); // ✅ Wait 5 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                speedBoostActive = false;
                System.out.println("AI Speed Boost Ended!");
            }).start();
        }
    }

    // ✅ Set movement state for input-controlled entities (Bucket)
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    // ✅ Get movement states (used by Bucket)
    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }
}
