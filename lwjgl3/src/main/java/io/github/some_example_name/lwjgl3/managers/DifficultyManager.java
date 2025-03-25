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
        if (score >= 30) {
            currentDifficulty = Difficulty.HARD;
        } else if (score >= 15) {
            currentDifficulty = Difficulty.MEDIUM;
        } else {
            currentDifficulty = Difficulty.EASY;
        }
    }

    public float getSpeedMultiplier() {
        switch (currentDifficulty) {
            case MEDIUM: return 1.15f;
            case HARD: return 1.3f;
            default: return 1.0f;
        }
    }

    public Difficulty getCurrentDifficulty() {
        return currentDifficulty;
    }
}
