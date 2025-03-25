package io.github.some_example_name.lwjgl3.managers;

import java.util.List;

import io.github.some_example_name.lwjgl3.entities.Entity;
import io.github.some_example_name.lwjgl3.interfaces.CollisionHandler;

public class CollisionManager {

    public boolean checkCollision(Entity e1, Entity e2) {
        float e1Width = e1.getTexture().getWidth() * e1.getScale();
        float e1Height = e1.getTexture().getHeight() * e1.getScale();

        float e2Width = e2.getTexture().getWidth() * e2.getScale();
        float e2Height = e2.getTexture().getHeight() * e2.getScale();

        boolean collided = e1.getX() < e2.getX() + e2Width &&
                         e1.getX() + e1Width > e2.getX() &&
                         e1.getY() < e2.getY() + e2Height &&
                         e1.getY() + e1Height > e2.getY();

        if (collided) {
            if (e1 instanceof CollisionHandler) {
                ((CollisionHandler) e1).onCollisionDetected(e2);
            }

        }

        return collided;
    }

    public void dispose() {
        // Nothing to dispose in CollisionManager currently
        // But having this method allows for future cleanup if needed
    }
}
