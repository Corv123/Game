package io.github.some_example_name.lwjgl3.scenes;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.entities.Battery;
import io.github.some_example_name.lwjgl3.entities.Bucket;
import io.github.some_example_name.lwjgl3.entities.Entity;
import io.github.some_example_name.lwjgl3.entities.Pizza;
import io.github.some_example_name.lwjgl3.entities.SpeedUp;
import io.github.some_example_name.lwjgl3.entities.Tissue;
import io.github.some_example_name.lwjgl3.interfaces.CollisionHandler;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.CollisionManager;
import io.github.some_example_name.lwjgl3.managers.EntityManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class GameScreen extends SceneGenerator {
    private EntityManager entityManager;
    private CollisionManager collisionManager;
    private AudioManager audioManager;
    private MovementManager movementManager;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Bucket bucket;
    private SceneManager sceneManager;
    private GameMaster gameMaster;
    private BitmapFont font;
    private Texture backgroundTexture;
    private int score = 0;
    private float timeAccumulator = 0f;


    public GameScreen(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.sceneManager = sceneManager;
        this.gameMaster = gameMaster;
        this.entityManager = gameMaster.getEntityManager();
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.audioManager = gameMaster.getAudioManager();
        this.movementManager = gameMaster.getMovementManager();
        this.collisionManager = gameMaster.getCollisionManager();
        this.font = new BitmapFont();

        // Load background texture
        this.backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        Gdx.input.setInputProcessor(gameMaster.getIoManager());

        // Start background music when game screen starts
        audioManager.startBackgroundMusic();

        // Create Bucket entity with fresh health
        bucket = new Bucket(new Texture("recyclebin.png"), 200f, 20f, 5f, 0.2f, batch, movementManager, gameMaster.getIoManager());
        bucket.resetHealth(); // Explicitly reset health when game starts
        entityManager.addEntity(bucket);

        // Create speedup entities
        for (int i = 0; i < 3; i++) {
            SpeedUp speedUp = new SpeedUp(new Texture("speedup.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50*0.3f)),
                Gdx.graphics.getHeight(),
                1f + (float) Math.random() * 2,
                0.3f,
                batch,
                movementManager);
            entityManager.addEntity(speedUp);
            movementManager.addEntity(speedUp);
        }

        // Create battery entities
        for (int i = 0; i < 10; i++) {
            Battery drop = new Battery(new Texture("battery.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50-0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f,
                batch,
                sceneManager,
                audioManager);
            entityManager.addEntity(drop);
            movementManager.addEntity(drop);
        }

        // Create pizza entities
        for (int i = 0; i < 10; i++) {
            Pizza drop = new Pizza(new Texture("pizza.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50-0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f,
                batch,
                sceneManager,
                audioManager);
            entityManager.addEntity(drop);
            movementManager.addEntity(drop);
        }

        // Create tissue entities
        for (int i = 0; i < 10; i++) {
            Tissue drop = new Tissue(new Texture("tissue.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50-0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f,
                batch,
                sceneManager,
                audioManager);
            entityManager.addEntity(drop);
            movementManager.addEntity(drop);
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);

        // Update score based on time
        timeAccumulator += Gdx.graphics.getDeltaTime();
        if (timeAccumulator >= 1f) {
            score += 1;
            timeAccumulator -= 1f; // subtract 1 to keep leftover time
        }


        batch.begin();
        // Draw background first
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        List<Entity> battery = entityManager.getEntities().stream()
            .filter(e -> e instanceof Battery)
            .collect(Collectors.toList());

        List<Entity> pizza = entityManager.getEntities().stream()
            .filter(e -> e instanceof Pizza)
            .collect(Collectors.toList());

        List<Entity> tissue = entityManager.getEntities().stream()
            .filter(e -> e instanceof Tissue)
            .collect(Collectors.toList());

        List<Entity> speedup = entityManager.getEntities().stream()
            .filter(e -> e instanceof SpeedUp)
            .collect(Collectors.toList());

        // Check collisions for all entities
        for (Entity drop : battery) {
             collisionManager.checkCollision(drop, bucket);
        }

        for (Entity drop : pizza) {
             collisionManager.checkCollision(drop, bucket);
        }

        for (Entity drop : tissue) {
             collisionManager.checkCollision(drop, bucket);
        }

        for (Entity drop : speedup) {
             collisionManager.checkCollision(drop, bucket);
        }

        // Update movement and entities
        movementManager.updateAiEntitiesMovement();
        entityManager.updateAll();

        // Draw everything

        entityManager.drawAll(batch);
        // Draw health counter with larger font size
        font.getData().setScale(1.5f);
        font.draw(batch, "Lives: " + bucket.getHealth(), 10, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Score: " + score, 8, Gdx.graphics.getHeight() - 50);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        backgroundTexture.dispose();
    }
}
