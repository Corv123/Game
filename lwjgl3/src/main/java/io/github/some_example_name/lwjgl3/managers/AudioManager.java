package io.github.some_example_name.lwjgl3.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class AudioManager {
    private Music backgroundMusic;
    private Sound dropCatchSound;
    private Sound gameOverSound;
    private boolean isMusicPlaying = false;
    private boolean isInitialized = false;

    public AudioManager() {
        // Don't load audio in constructor - wait for explicit initialization
    }

    public void initialize() {
        if (!isInitialized) {
            loadAudio();
            isInitialized = true;
        }
    }

    private void loadAudio() {
        try {
            // Check if files exist before loading
            FileHandle bgMusic = Gdx.files.internal("background.mp3");
            FileHandle dropSound = Gdx.files.internal("drop_catch.mp3");
            FileHandle gameOverSnd = Gdx.files.internal("game_over.mp3");

            if (bgMusic.exists()) {
                backgroundMusic = Gdx.audio.newMusic(bgMusic);
                backgroundMusic.setLooping(true);
                backgroundMusic.setVolume(0.5f);
            } else {
                System.err.println("Warning: background.mp3 not found");
            }

            if (dropSound.exists()) {
                dropCatchSound = Gdx.audio.newSound(dropSound);
            } else {
                System.err.println("Warning: drop_catch.mp3 not found");
            }

            if (gameOverSnd.exists()) {
                gameOverSound = Gdx.audio.newSound(gameOverSnd);
            } else {
                System.err.println("Warning: game_over.mp3 not found");
            }
        } catch (Exception e) {
            System.err.println("Error loading audio files: " + e.getMessage());
        }
    }

    public void startBackgroundMusic() {
        if (!isInitialized) {
            initialize();
        }
        if (!isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.play();
            isMusicPlaying = true;
        }
    }

    public void stopBackgroundMusic() {
        if (!isInitialized) {
            return;
        }
        if (isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.stop();
            isMusicPlaying = false;
        }
    }

    public void playDropCatchSound() {
        if (!isInitialized) {
            initialize();
        }
        if (dropCatchSound != null) {
            dropCatchSound.play(0.7f);
        }
    }

    public void playGameOverSound() {
        if (!isInitialized) {
            initialize();
        }
        if (gameOverSound != null) {
            gameOverSound.play(1.0f);
        }
    }

    public void dispose() {
        if (!isInitialized) {
            return;
        }
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
            backgroundMusic = null;
        }
        if (dropCatchSound != null) {
            dropCatchSound.dispose();
            dropCatchSound = null;
        }
        if (gameOverSound != null) {
            gameOverSound.dispose();
            gameOverSound = null;
        }
        isInitialized = false;
        isMusicPlaying = false;
    }
}
