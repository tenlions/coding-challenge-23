package de.nloewes.roshambr.converter;

import de.nloewes.roshambr.exception.InvalidChoiceException;
import de.nloewes.roshambr.model.GameChoice;
import de.nloewes.roshambr.model.GameChoiceResource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameChoiceConverterTest {

    @Test
    public void testToSource_valid_rock() {
        // GIVEN
        GameChoiceResource target = new GameChoiceResource();
        target.setPlayerChoice("ROCK");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.ROCK, source);
    }

    @Test
    public void testToSource_valid_paper() {
        // GIVEN
        GameChoiceResource target = new GameChoiceResource();
        target.setPlayerChoice("PAPER");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.PAPER, source);
    }

    @Test
    public void testToSource_valid_scissors() {
        // GIVEN
        GameChoiceResource target = new GameChoiceResource();
        target.setPlayerChoice("SCISSORS");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.SCISSORS, source);
    }

    @Test
    public void testToSource_invalid() {
        // GIVEN
        GameChoiceResource target = new GameChoiceResource();
        target.setPlayerChoice("foo");

        // WHEN
        assertThrowsExactly(InvalidChoiceException.class, () -> GameChoiceConverter.toSource(target));
    }
}