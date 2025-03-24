package io.github.some_example_name.lwjgl3.scenes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.github.some_example_name.lwjgl3.managers.SceneManager;

public abstract class SceneGenerator implements Screen {
    protected SceneManager sceneManager;
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;

    public SceneGenerator(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
    }

    // Abstract render method to be implemented in subclasses
    @Override
    public abstract void render(float delta);

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}

