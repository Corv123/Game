package io.github.some_example_name.lwjgl3.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class StartScreen extends SceneGenerator {
    private Texture startButton;
    private Texture backgroundTexture;
    private float buttonX, buttonY, buttonWidth, buttonHeight;
    private SpriteBatch batch;
    private GameMaster gameMaster;

    public StartScreen(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        this.batch = new SpriteBatch();
        startButton = new Texture(Gdx.files.internal("start-button.png"));
        backgroundTexture = new Texture(Gdx.files.internal("menu-background.png"));

        buttonWidth = 200;
        buttonHeight = 80;
        buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;
    }

    @Override
    public void show() {
        // Called when this screen becomes the current screen
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(startButton, buttonX, buttonY, buttonWidth, buttonHeight);
        batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth &&
                mouseY >= buttonY && mouseY <= buttonY + buttonHeight) {

                System.out.println("Start button clicked! Switching to Game Screen...");
                sceneManager.setScene(SceneManager.SceneType.GAME);
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        // Called when another screen replaces this one
    }

    @Override
    public void dispose() {
        startButton.dispose();
        backgroundTexture.dispose();
        batch.dispose();
    }
}
