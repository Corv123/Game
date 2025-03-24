package io.github.some_example_name.lwjgl3.managers;

import com.badlogic.gdx.Screen;
import io.github.some_example_name.lwjgl3.GameMaster;
import io.github.some_example_name.lwjgl3.scenes.EndScreen;
import io.github.some_example_name.lwjgl3.scenes.StartScreen;
import io.github.some_example_name.lwjgl3.scenes.GameScreen;

public class SceneManager {
    private Screen currentScene;
    private GameMaster gameMaster;

    public SceneManager(GameMaster gameMaster) {
        this.gameMaster = gameMaster;
    }

    public enum SceneType {
        START,
        GAME,
        END
    }

    public void setScene(SceneType sceneType) {
        if (currentScene != null) {
            currentScene.hide();
        }

        switch (sceneType) {
            case START:
                currentScene = new StartScreen(this, gameMaster);
                break;
            case GAME:
                currentScene = new GameScreen(this, gameMaster);
                break;
            case END:
                currentScene = new EndScreen(this, gameMaster);
                break;
        }
    }

    public void update() {
        if (currentScene != null) {
            currentScene.render(0);
        }
    }

    public void dispose() {
        if (currentScene != null) {
            currentScene.dispose();
        }
    }
}
