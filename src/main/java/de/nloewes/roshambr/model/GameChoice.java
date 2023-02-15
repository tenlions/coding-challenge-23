package de.nloewes.roshambr.model;

import java.util.Random;

/**
 * Enum to represent the possible turn choices available to either player of a game of rock, paper, scissors
 */
public enum GameChoice {
    ROCK,
    PAPER,
    SCISSORS;

    private static final Random random = new Random();

    /**
     * Returns a random entry of this enum
     * @return a random entry of this enum
     */
    public static GameChoice getRandomChoice() {
        int index = random.nextInt(GameChoice.class.getEnumConstants().length);
        return GameChoice.class.getEnumConstants()[index];
    }

    /**
     * Converts a given String to the corresponding GameChoice in case a corresponding entry exists.
     * @param choice the String to convert
     * @return the converted GameChoice
     *
     * @throws IllegalArgumentException if an invalid value was passed
     */
    public static GameChoice fromString(String choice) throws IllegalArgumentException {
        return valueOf(choice.replaceAll(" ", "").toUpperCase());
    }
}
