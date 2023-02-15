package de.nloewes.roshambr.converter;

import de.nloewes.roshambr.exception.InvalidChoiceException;
import de.nloewes.roshambr.model.GameChoice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameChoiceConverterTest {

    @Test
    public void testToSource_valid_rock() {
        // GIVEN
        de.nloewes.roshambr.model.dto.GameChoice target = new de.nloewes.roshambr.model.dto.GameChoice();
        target.setPlayerChoice("ROCK");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.ROCK, source);
    }

    @Test
    public void testToSource_valid_paper() {
        // GIVEN
        de.nloewes.roshambr.model.dto.GameChoice target = new de.nloewes.roshambr.model.dto.GameChoice();
        target.setPlayerChoice("PAPER");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.PAPER, source);
    }

    @Test
    public void testToSource_valid_scissors() {
        // GIVEN
        de.nloewes.roshambr.model.dto.GameChoice target = new de.nloewes.roshambr.model.dto.GameChoice();
        target.setPlayerChoice("SCISSORS");

        // WHEN
        GameChoice source = GameChoiceConverter.toSource(target);

        // THEN
        assertEquals(GameChoice.SCISSORS, source);
    }

    @Test
    public void testToSource_invalid() {
        // GIVEN
        de.nloewes.roshambr.model.dto.GameChoice target = new de.nloewes.roshambr.model.dto.GameChoice();
        target.setPlayerChoice("foo");

        // WHEN
        assertThrowsExactly(InvalidChoiceException.class, () -> GameChoiceConverter.toSource(target));
    }
}