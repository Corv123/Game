package io.github.some_example_name.lwjgl3.scenes;

import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.entities.Bucket;
import io.github.some_example_name.lwjgl3.entities.Entity;
import io.github.some_example_name.lwjgl3.entities.SpeedUp;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.CollisionManager;
import io.github.some_example_name.lwjgl3.managers.EntityManager;
import io.github.some_example_name.lwjgl3.managers.MovementManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;
import io.github.some_example_name.lwjgl3.Factory.EntityFactory;
import io.github.some_example_name.lwjgl3.managers.DifficultyManager;
import io.github.some_example_name.lwjgl3.managers.IOManager;

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
    private DifficultyManager difficultyManager;
    private EntityFactory entityFactory;
    private IOManager ioManager;
    private String difficultyMessage = null;
    private float difficultyMessageTimer = 0f;
    private DifficultyManager.Difficulty lastDifficultyShown = DifficultyManager.Difficulty.EASY;

    //Prepares: All references (batch, collisionManager, audioManager, etc.)
    //
    //Loads background image
    //
    //Starts the background music
    //
    //Spawns all the initial entities based on difficulty
    //
    //✅ The constructor is like level setup — it's not part of the loop.
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
        this.difficultyManager = gameMaster.getDifficultyManager();
        this.ioManager = gameMaster.getIoManager();
        this.entityFactory = new EntityFactory(batch, sceneManager, audioManager, difficultyManager, movementManager, ioManager);

        this.backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        setupInputAndAudio();
        spawnInitialEntities();
    }


    //loop and runs the game, showing the movement u see on the screen
    @Override
    public void show() {}

    //loop and runs the game, showing the movement u see on the screen
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.4f, 1);

        timeAccumulator += Gdx.graphics.getDeltaTime();
        if (timeAccumulator >= 1f) {
            score += 1;
            timeAccumulator -= 1f;
        }

        difficultyManager.updateDifficulty(score);

        DifficultyManager.Difficulty current = difficultyManager.getCurrentDifficulty();
        if (current != lastDifficultyShown) {
            switch (current) {
                case MEDIUM:
                    difficultyMessage = "MEDIUM MODE";
                    difficultyMessageTimer = 1f;
                    spawnFallingEntity("battery", "battery.png");
                    spawnFallingEntity("pizza", "pizza.png");
                    spawnFallingEntity("tissue", "tissue.png");
                    break;

                case HARD:
                    difficultyMessage = "HARD MODE";
                    difficultyMessageTimer = 1f;
                    spawnFallingEntity("battery", "battery.png");
                    spawnFallingEntity("pizza", "pizza.png");
                    spawnFallingEntity("tissue", "tissue.png");
                    break;
            }

            lastDifficultyShown = current;
        }


        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (Entity object : entityManager.getEntities()) {
            collisionManager.checkCollision(object, bucket);
        }

        movementManager.updateAiEntitiesMovement();
        entityManager.updateAll();
        entityManager.drawAll(batch);

        font.getData().setScale(1.5f);
        font.draw(batch, "Lives: " + bucket.getHealth(), 10, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Score: " + score, 8, Gdx.graphics.getHeight() - 50);

        if (difficultyMessage != null && difficultyMessageTimer > 0f) {
            font.getData().setScale(3.0f);
            GlyphLayout layout = new GlyphLayout(font, difficultyMessage);
            float centerX = (Gdx.graphics.getWidth() - layout.width) / 2f;
            float centerY = (Gdx.graphics.getHeight() + layout.height) / 2f;
            font.draw(batch, difficultyMessage, centerX, centerY);
            difficultyMessageTimer -= Gdx.graphics.getDeltaTime();

            if (difficultyMessageTimer <= 0f) {
                difficultyMessage = null;
            }
        }

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

    //methods
    private void setupInputAndAudio() {
        Gdx.input.setInputProcessor(gameMaster.getIoManager());
        audioManager.startBackgroundMusic();
    }

    private void spawnInitialEntities() {
        bucket = entityFactory.createBucket(new Texture("recyclebin.png"), 200f, 20f, 5f, 0.2f);
        bucket.resetHealth();
        entityManager.addEntity(bucket);

        for (int i = 0; i < 3; i++) {
            SpeedUp speedUp = entityFactory.createSpeedUp(
                new Texture("speedup.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 * 0.3f)),
                Gdx.graphics.getHeight(),
                1f + (float) Math.random() * 2,
                0.3f
            );
            entityManager.addEntity(speedUp);
            movementManager.addEntity(speedUp);
        }

        spawnFallingEntity("battery", "battery.png");
        spawnFallingEntity("pizza", "pizza.png");
        spawnFallingEntity("tissue", "tissue.png");
    }

    private void spawnFallingEntity(String type, String texturePath) {
        int count;
        switch (difficultyManager.getCurrentDifficulty()) {
            case MEDIUM: count = 3; break;
            case HARD: count = 4; break;
            default: count = 2; break;
        }

        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.createEntity(
                type,
                new Texture(texturePath),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 - 0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f
            );
            entityManager.addEntity(entity);
            movementManager.addEntity(entity);
        }
    }

}
