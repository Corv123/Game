package io.github.some_example_name.lwjgl3.managers;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.InputProcessor;

import io.github.some_example_name.lwjgl3.interfaces.InputListener;

// ✅ Handles player input and stores registered entities
public class IOManager implements InputProcessor {
    private final Set<InputListener> listeners = new HashSet<>(); // ✅ Tracks entities that need input

    // ✅ Entities (e.g., Bucket) register for input
    public void addListener(InputListener listener) {
        listeners.add(listener);
    }

    // ✅ Entities can also be removed if needed
    public void removeListener(InputListener listener) {
        listeners.remove(listener);
    }

    // ✅ Detects when a key is pressed and notifies registered entities
    @Override
    public boolean keyDown(int keycode) {
        for (InputListener listener : listeners) {
            listener.onKeyPressed(keycode);
        }
        return true;
    }

    // ✅ Detects when a key is released and notifies registered entities
    @Override
    public boolean keyUp(int keycode) {
        for (InputListener listener : listeners) {
            listener.onKeyReleased(keycode);
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    // ✅ Cleanup method for resources
    public void dispose() {
        listeners.clear();
    }
}
