package io.github.some_example_name.lwjgl3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.interfaces.InputListener;
import io.github.some_example_name.lwjgl3.managers.IOManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;

// ✅ Bucket now listens for input and moves accordingly
public class Bucket extends Entity implements InputListener {
    private final MovementManager movementManager;
    private int health;  // Add health variable
    private static final int MAX_HEALTH = 5;  // Maximum health constant

    public Bucket(Texture texture, float x, float y, float speed,float scale, SpriteBatch batch, MovementManager movementManager, IOManager ioManager) {
        super(texture, x, y, speed,scale, batch);
        this.movementManager = movementManager;
        resetHealth();  // Use resetHealth in constructor
        ioManager.addListener(this); // ✅ Register bucket for input events
    }

    // Add health-related methods
    public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        if (health > 0) {
            health--;
        }
    }

    public void increaseHealth() {
        if (health < MAX_HEALTH) {
            health++;
        }
    }

    public void resetHealth() {
        health = MAX_HEALTH;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // ✅ Moves based on movement state in MovementManager
    public void move(float dx) {
        x += dx;
    }

    public void keepWithinBounds() {
        x = Math.max(0, Math.min(x, Gdx.graphics.getWidth() - texture.getWidth()*scale));
    }

    // ✅ Movement logic remains inside the entity (not in MovementManager)
    @Override
    public void update() {
        float moveSpeed = 300f * Gdx.graphics.getDeltaTime();
        if (movementManager.isMovingLeft()) move(-moveSpeed);
        if (movementManager.isMovingRight()) move(moveSpeed);
        keepWithinBounds();
    }

    // ✅ Called when a key is pressed
    @Override
    public void onKeyPressed(int keycode) {
        if (keycode == Input.Keys.LEFT) movementManager.setMovingLeft(true);
        if (keycode == Input.Keys.RIGHT) movementManager.setMovingRight(true);
    }

    // ✅ Called when a key is released
    @Override
    public void onKeyReleased(int keycode) {
        if (keycode == Input.Keys.LEFT) movementManager.setMovingLeft(false);
        if (keycode == Input.Keys.RIGHT) movementManager.setMovingRight(false);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (batch != null && texture != null) {
            batch.draw(texture, x, y, texture.getWidth()*scale, texture.getHeight()*scale);
        }
    }
}
