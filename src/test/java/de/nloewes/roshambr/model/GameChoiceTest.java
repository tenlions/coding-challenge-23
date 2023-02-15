package de.nloewes.roshambr.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class GameChoiceTest {

    @Test
    public void testFromString_valid_lowerCase() {
        // GIVEN
        String input = "rock";

        // WHEN
        GameChoice choice = GameChoice.fromString(input);

        // THEN
        assertEquals(GameChoice.ROCK, choice);
    }

    @Test
    public void testFromString_valid_upperCase() {
        // GIVEN
        String input = "ROCK";

        // WHEN
        GameChoice choice = GameChoice.fromString(input);

        // THEN
        assertEquals(GameChoice.ROCK, choice);
    }

    @Test
    public void testFromString_valid_mixedCase() {
        // GIVEN
        String input = "rOcK";

        // WHEN
        GameChoice choice = GameChoice.fromString(input);

        // THEN
        assertEquals(GameChoice.ROCK, choice);
    }

    @Test
    public void testFromString_valid_whitespace() {
        // GIVEN
        String input = "rOcK ";

        // WHEN
        GameChoice choice = GameChoice.fromString(input);

        // THEN
        assertEquals(GameChoice.ROCK, choice);
    }

    @Test
    public void testFromString_invalid() {
        // GIVEN
        String input = "foo";

        //WHEN
        assertThrowsExactly(IllegalArgumentException.class, () -> GameChoice.fromString(input));
    }
}
