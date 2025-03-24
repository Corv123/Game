package io.github.some_example_name.lwjgl3.interfaces;

import io.github.some_example_name.lwjgl3.entities.Entity;

public interface CollisionHandler {
    void onCollisionDetected(Entity other); // ✅ Each entity defines its own collision effect
}
