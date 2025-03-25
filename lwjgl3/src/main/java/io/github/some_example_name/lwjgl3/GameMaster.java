package io.github.some_example_name.lwjgl3;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.managers.*;

public class GameMaster extends ApplicationAdapter {
    // Volatile keyword ensures thread safety for double-checked locking
    private static volatile GameMaster instance;

    private CollisionManager collisionManager;
    private IOManager ioManager;
    private SceneManager sceneManager;
    private MovementManager movementManager;
    private EntityManager entityManager;
    private AudioManager audioManager;
    private DifficultyManager difficultyManager;


    // Private constructor to prevent instantiation
    private GameMaster() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
        initializeManagers();
    }

    // Thread-safe singleton implementation with double-checked locking
    public static GameMaster getInstance() {
        if (instance == null) {
            synchronized (GameMaster.class) {
                if (instance == null) {
                    instance = new GameMaster();
                }
            }
        }
        return instance;
    }

    // Prevent cloning of the instance
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning of this class is not allowed");
    }

    private void initializeManagers() {
        collisionManager = new CollisionManager();
        ioManager = new IOManager();
        movementManager = new MovementManager();
        entityManager = new EntityManager();
        audioManager = new AudioManager();
        difficultyManager = new DifficultyManager();
        sceneManager = new SceneManager(this); // Create last since it depends on other managers
    }

    public DifficultyManager getDifficultyManager() {
        return difficultyManager;
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
