package io.github.some_example_name.lwjgl3.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class EndScreen extends SceneGenerator {
    private Texture gameOverImage;
    private SpriteBatch batch;
    private AudioManager audioManager;
    private GameMaster gameMaster;

    public EndScreen(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        batch = new SpriteBatch();
        gameOverImage = new Texture(Gdx.files.internal("game-over.png"));
        audioManager = gameMaster.getAudioManager();
    }

    @Override
    public void show() {
        System.out.println("🔴 End Screen Loaded! Waiting for ENTER key...");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(
            gameOverImage,
            (Gdx.graphics.getWidth() - gameOverImage.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - gameOverImage.getHeight()) / 2f
        );
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("�� Restarting Game... Resetting game state!");
            gameMaster.resetGame();
        }
    }

    @Override
    public void dispose() {
        System.out.println("🗑️ Disposing End Screen...");
        gameOverImage.dispose();
        batch.dispose();
    }
}
