package io.github.some_example_name.lwjgl3.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.managers.AudioManager;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public class EndScreen extends SceneGenerator {
    private Texture gameOverImage;
    private SpriteBatch batch;
    private AudioManager audioManager;
    private GameMaster gameMaster;

    // ✅ New textures for educational images
    private Texture batteryTexture, pizzaTexture, tissueTexture, canTexture, glassTexture;
    private BitmapFont font;

    public EndScreen(SceneManager sceneManager, GameMaster gameMaster) {
        super(sceneManager);
        this.gameMaster = gameMaster;
        batch = new SpriteBatch();
        gameOverImage = new Texture(Gdx.files.internal("game-over.png"));
        audioManager = gameMaster.getAudioManager();

        // ✅ Load textures for educational section
        batteryTexture = new Texture(Gdx.files.internal("battery.png"));
        pizzaTexture = new Texture(Gdx.files.internal("pizza.png"));
        tissueTexture = new Texture(Gdx.files.internal("tissue.png"));
        canTexture = new Texture(Gdx.files.internal("soft-drink.png"));
        glassTexture = new Texture(Gdx.files.internal("bottle.png"));

        font = new BitmapFont();
    }

    @Override
    public void show() {
        System.out.println("🔴 End Screen Loaded! Waiting for ENTER key...");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // ✅ Repositioned Game Over banner (now at ~700px)
        float gameOverX = (Gdx.graphics.getWidth() - gameOverImage.getWidth()) / 2f;
        float gameOverY = 450;
        batch.draw(gameOverImage, gameOverX, gameOverY);

        // ✅ Educational section starts just below (~520px)
        int startX =110;
        int y = 400; // Starting Y position
        int spacingY = 90;

        font.getData().setScale(1.2f);

        // Battery
        batch.draw(batteryTexture, startX, y, 64, 64);
        font.draw(batch, "❌ Not Recyclable", startX + 80, y + 45);
        font.draw(batch, "Dispose at e-waste collection points.", startX + 80, y + 25);
        y -= spacingY;

        // Pizza
        batch.draw(pizzaTexture, startX, y, 64, 64);
        font.draw(batch, "❌ Not Recyclable", startX + 80, y + 45);
        font.draw(batch, "Greasy food waste can't be recycled.", startX + 80, y + 25);
        y -= spacingY;

        // Tissue
        batch.draw(tissueTexture, startX, y, 64, 64);
        font.draw(batch, "✅ Recyclable", startX + 80, y + 45);
        font.draw(batch, "Can be composted or placed in blue bin.", startX + 80, y + 25);
        y -= spacingY;

        // Can
        batch.draw(canTexture, startX, y, 64, 64);
        font.draw(batch, "✅ Recyclable", startX + 80, y + 45);
        font.draw(batch, "Rinse and recycle in metal bin.", startX + 80, y + 25);
        y -= spacingY;

        // Glass
        batch.draw(glassTexture, startX, y, 64, 64);
        font.draw(batch, "❌ Not Recyclable", startX + 80, y + 45);
        font.draw(batch, "Use glass recycling drop-off points.", startX + 80, y + 25);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            System.out.println("↩️ Restarting Game... Resetting game state!");
            gameMaster.resetGame();
        }
    }



    @Override
    public void dispose() {
        System.out.println("🗑️ Disposing End Screen...");
        gameOverImage.dispose();
        batch.dispose();

        // ✅ Dispose educational assets
        batteryTexture.dispose();
        pizzaTexture.dispose();
        tissueTexture.dispose();
        canTexture.dispose();
        glassTexture.dispose();
        font.dispose();
    }
}
