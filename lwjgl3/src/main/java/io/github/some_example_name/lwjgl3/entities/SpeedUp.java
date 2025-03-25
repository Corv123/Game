package io.github.some_example_name.lwjgl3.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import io.github.some_example_name.lwjgl3.interfaces.CollisionHandler;
import io.github.some_example_name.lwjgl3.managers.DifficultyManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;

public class SpeedUp extends Entity implements CollisionHandler {
    private MovementManager movementManager;


    public SpeedUp(Texture texture, float x, float y, float speed,float scale, SpriteBatch batch, MovementManager movementManager) {
        super(texture, x, y, speed,scale, batch);
        this.movementManager = movementManager;
    }

    @Override
    public void move() {
        float multiplier = DifficultyManager.getInstance().getSpeedMultiplier(); // Real-time multiplier
        y -= speed * multiplier * Gdx.graphics.getDeltaTime() * 10;

        if (y <= -texture.getHeight() * scale) {
            resetSpeedUp();
        }
    }


    @Override
    public void update() {
        move();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (batch != null && texture != null) {
            batch.draw(texture, x, y, texture.getWidth() * scale, texture.getHeight() * scale);
        }
    }

    @Override
    public void onCollisionDetected(Entity other) {
        if (other instanceof Bucket) {
            System.out.println("Speedup hit bucket! Doubling AI speed for 5 seconds!");
            movementManager.activateSpeedBoost();
        }
    }

    public void resetSpeedUp() {
        this.x = (float) Math.random() * (Gdx.graphics.getWidth() - (texture.getWidth() * scale));
        this.y = Gdx.graphics.getHeight() + texture.getHeight() * scale;
    }
}
