package io.github.some_example_name.lwjgl3.Factory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.entities.*;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;
import io.github.some_example_name.lwjgl3.managers.DifficultyManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;
import io.github.some_example_name.lwjgl3.managers.IOManager;

public class EntityFactory {

    private final SpriteBatch batch;
    private final SceneManager sceneManager;
    private final AudioManager audioManager;
    private final DifficultyManager difficultyManager;
    private final MovementManager movementManager;
    private final IOManager ioManager;

    public EntityFactory(SpriteBatch batch, SceneManager sceneManager, AudioManager audioManager, DifficultyManager difficultyManager, MovementManager movementManager, IOManager ioManager) {
        this.batch = batch;
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
        this.difficultyManager = difficultyManager;
        this.movementManager = movementManager;
        this.ioManager = ioManager;
    }

    // ✅ Generic method for common entities
    public Entity createEntity(String type, Texture texture, float x, float y, float baseSpeed, float scale) {
        float adjustedSpeed = baseSpeed * difficultyManager.getSpeedMultiplier();

        switch (type.toLowerCase()) {
            case "pizza":
                return new Pizza(texture, x, y, adjustedSpeed, scale, batch, sceneManager, audioManager);
            case "battery":
                return new Battery(texture, x, y, adjustedSpeed, scale, batch, sceneManager, audioManager);
            case "tissue":
                return new Tissue(texture, x, y, adjustedSpeed, scale, batch, sceneManager, audioManager);
            default:
                throw new IllegalArgumentException("Unknown entity type: " + type);
        }
    }

    // ✅ Special method for SpeedUp (constructor is different)
    public SpeedUp createSpeedUp(Texture texture, float x, float y, float baseSpeed, float scale) {
        float adjustedSpeed = baseSpeed * difficultyManager.getSpeedMultiplier();
        return new SpeedUp(texture, x, y, adjustedSpeed, scale, batch, movementManager);
    }


    // ✅ Special method for Bucket (needs MovementManager & IOManager)
    public Bucket createBucket(Texture texture, float x, float y, float speed, float scale) {
        return new Bucket(texture, x, y, speed, scale, batch, movementManager, ioManager);
    }
}
