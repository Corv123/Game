package io.github.some_example_name.lwjgl3.interfaces;

// ✅ Custom interface for handling input events
public interface InputListener {
    void onKeyPressed(int keycode);
    void onKeyReleased(int keycode);
}
