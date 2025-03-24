package io.github.some_example_name.lwjgl3.managers;

// Import necessary utilities
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.lwjgl3.entities.Entity;

import java.util.ArrayList;
import java.util.List;

// ✅ Fully generic EntityManager (works for any game, does not handle movement/collisions)
public class EntityManager {
    private List<Entity> entityList; // ✅ Stores all game entities

    // ✅ Constructor to initialize the list
    public EntityManager() {
        entityList = new ArrayList<>();
    }

    // ✅ Method to add an entity to the list
    public void addEntity(Entity entity) {
        if (entity != null) {
            entityList.add(entity); // ✅ Store all entities in the same list
        }
    }

    // ✅ Provides read-only access to the list of entities
    public List<Entity> getEntities() {
        return entityList;
    }

    // ✅ Method to remove an entity from the list
    public void removeEntity(Entity entity) {
        entityList.remove(entity);
    }

    // ✅ Calls update() on all entities
    public void updateAll() {
        for (Entity entity : entityList) {
            entity.update();
        }
    }

    // ✅ Ensures proper drawing order based on entity type
    public void drawAll(SpriteBatch batch) {
        for (Entity entity : entityList) {
            entity.draw(batch);
        }
    }

    // ✅ Dispose method to clean up resources
    public void dispose() {
        for (Entity entity : entityList) {
            entity.dispose();
        }
        entityList.clear();
    }
}
