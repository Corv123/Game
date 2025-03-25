package io.github.some_example_name.lwjgl3.managers;

public class DifficultyManager {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    private static DifficultyManager instance; // ✅ static instance

    private Difficulty currentDifficulty = Difficulty.EASY;

    // ✅ Constructor assigns itself to instance
    public DifficultyManager() {
        instance = this;
    }

    // ✅ Static accessor
    public static DifficultyManager getInstance() {
        return instance;
    }

    public void updateDifficulty(int score) {
        if (score >= 20) {
            currentDifficulty = Difficulty.HARD;
        } else if (score >= 10) {
            currentDifficulty = Difficulty.MEDIUM;
        } else {
            currentDifficulty = Difficulty.EASY;
        }
    }

    public float getSpeedMultiplier() {
        switch (currentDifficulty) {
            case MEDIUM: return 2.0f;
            case HARD: return 3.0f;
            default: return 1.0f;
        }
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }
}
