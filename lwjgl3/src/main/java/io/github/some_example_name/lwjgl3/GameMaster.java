package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.CollisionManager;
import io.github.some_example_name.lwjgl3.managers.EntityManager;
import io.github.some_example_name.lwjgl3.managers.IOManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class GameMaster extends ApplicationAdapter {
    private CollisionManager collisionManager;
    private IOManager ioManager;
    private SceneManager sceneManager;
    private MovementManager movementManager;
    private EntityManager entityManager;
    private AudioManager audioManager;

    // Constructor to handle instantiation
    public GameMaster() {
        initializeManagers();
    }

    private void initializeManagers() {
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        movementManager = new MovementManager();
        entityManager = new EntityManager();
        audioManager = new AudioManager();
        sceneManager = new SceneManager(this); // Create last since it depends on other managers
    }

    public void resetGame() {
        // Stop any ongoing audio
        audioManager.stopBackgroundMusic();
        
        // Dispose current managers
        entityManager.dispose();
        audioManager.dispose();
        
        // Reinitialize managers
        initializeManagers();
        
        // Initialize audio after Gdx is ready
        audioManager.initialize();
        
        // Start at the Start Screen
        sceneManager.setScene(SceneManager.SceneType.START);
    }

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public CollisionManager getCollisionManager() {
        return collisionManager;
    }

    public IOManager getIoManager() {
        return ioManager;
    }

    public MovementManager getMovementManager() {
        return movementManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void create() {
        // Initialize audio after Gdx is ready
        audioManager.initialize();
        // Start the game at the Start Screen
        sceneManager.setScene(SceneManager.SceneType.START);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.4f, 1); // ✅ Clears screen
        sceneManager.update(); // ✅ Handles rendering & scene switching
    }

    @Override
    public void dispose() {
        sceneManager.dispose();
        audioManager.dispose();
        entityManager.dispose();
        collisionManager.dispose();
    }
}
