package io.github.some_example_name.lwjgl3.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.lwjgl3.interfaces.CollisionHandler;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.DifficultyManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class Glass extends Entity implements CollisionHandler {
    private SceneManager sceneManager;
    private AudioManager audioManager;

    public Glass(Texture texture, float x, float y, float speed, float scale, SpriteBatch batch, SceneManager sceneManager, AudioManager audioManager) {
        super(texture, x, y, speed, scale, batch);
        this.sceneManager = sceneManager;
        this.audioManager = audioManager;
    }

    @Override
    public void move() {
        float multiplier = DifficultyManager.getInstance().getSpeedMultiplier(); // Real-time multiplier
        y -= speed * multiplier * Gdx.graphics.getDeltaTime() * 10;

        if (y <= -texture.getHeight() * scale) {
            resetGlass();
        }
    }


    @Override
    public void update() {
        move();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (batch != null && texture != null) {
            batch.draw(texture, x, y, texture.getWidth()*scale, texture.getHeight()*scale);
        }
    }

    // ✅ Define collision effect (Triggers End Screen)
    @Override
    public void onCollisionDetected(Entity other) {
        if (other instanceof Bucket) {
            Bucket bucket = (Bucket) other;
            bucket.decreaseHealth();
            audioManager.playDropCatchSound();

            if (!bucket.isAlive()) {
                System.out.println("Game Over! No more lives remaining!");
                audioManager.playGameOverSound();
                audioManager.stopBackgroundMusic();
                sceneManager.setScene(SceneManager.SceneType.END);
            } else {
                System.out.println("Hit! Lives remaining: " + bucket.getHealth());
            }
            // Reset position after collision
            resetGlass();
        }
    }

    public void resetGlass() {
        float scaledWidth = texture.getWidth() * scale;
        this.x = (float) Math.random() * (Gdx.graphics.getWidth() - scaledWidth);
        this.y = Gdx.graphics.getHeight() + texture.getHeight() * scale;
    }

    @Override
    public void dispose() {
        super.dispose();
        if (audioManager != null) {
            audioManager.dispose();
        }
    }
}
