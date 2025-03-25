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



        // Load background texture
        this.backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        Gdx.input.setInputProcessor(gameMaster.getIoManager());

        // Start background music when game screen starts
        audioManager.startBackgroundMusic();

        // Create Bucket entity with fresh health
        bucket = entityFactory.createBucket(new Texture("recyclebin.png"), 200f, 20f, 5f, 0.2f);
        bucket.resetHealth(); // Explicitly reset health when game starts
        entityManager.addEntity(bucket);// adds bucket to the entitylist

        // Create speedup entities
        for (int i = 0; i < 3; i++) {
            SpeedUp speedUp = entityFactory.createSpeedUp(
                new Texture("speedup.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 * 0.3f)),
                Gdx.graphics.getHeight(),
                1f + (float) Math.random() * 2,
                0.3f
            );
            entityManager.addEntity(speedUp);// adds speedup to the entitylist
            movementManager.addEntity(speedUp);
        }

        // Create battery entities
        for (int i = 0; i < 6; i++) {
            Entity battery = entityFactory.createEntity(
                "battery",
                new Texture("battery.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 - 0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f
            );
            entityManager.addEntity(battery);// adds battery to the entitylist
            movementManager.addEntity(battery);
        }

        // Create pizza entities
        for (int i = 0; i < 5; i++) {
            Entity pizza = entityFactory.createEntity(
                "pizza",
                new Texture("pizza.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 - 0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f
            );
            entityManager.addEntity(pizza);// adds pizza to the entitylist
            movementManager.addEntity(pizza);
        }

        // Create tissue entities
        for (int i = 0; i < 3; i++) {
            Entity tissue = entityFactory.createEntity(
                "tissue",
                new Texture("tissue.png"),
                (float) Math.random() * (Gdx.graphics.getWidth() - (50 - 0.1f)),
                Gdx.graphics.getHeight(),
                2f + (float) Math.random() * 2,
                0.1f
            );
            entityManager.addEntity(tissue);// adds tissue to the entitylist
            movementManager.addEntity(tissue);
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

        // Update difficulty level based on score
        difficultyManager.updateDifficulty(score);

        // Show difficulty transition message
        DifficultyManager.Difficulty current = difficultyManager.getCurrentDifficulty();
        if (current != lastDifficultyShown) {
            switch (current) {
                case MEDIUM:
                    difficultyMessage = "MEDIUM MODE";
                    difficultyMessageTimer = 1f;
                    break;
                case HARD:
                    difficultyMessage = "HARD MODE";
                    difficultyMessageTimer = 1f;
                    break;
            }
            lastDifficultyShown = current;
        }

        batch.begin();
        // Draw background first
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (Entity object : entityManager.getEntities()) {
            collisionManager.checkCollision(object, bucket);
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
        // Show temporary difficulty mode message
        if (difficultyMessage != null && difficultyMessageTimer > 0f) {
            font.getData().setScale(3.0f); // Bigger for emphasis

            GlyphLayout layout = new GlyphLayout(font, difficultyMessage);
            float centerX = (Gdx.graphics.getWidth() - layout.width) / 2f;
            float centerY = (Gdx.graphics.getHeight() + layout.height) / 2f;

            font.draw(batch, difficultyMessage, centerX, centerY);
            difficultyMessageTimer -= Gdx.graphics.getDeltaTime();

            difficultyMessageTimer -= Gdx.graphics.getDeltaTime();

            if (difficultyMessageTimer <= 0f) {
                difficultyMessage = null; // Clear message after 1 second
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
}
