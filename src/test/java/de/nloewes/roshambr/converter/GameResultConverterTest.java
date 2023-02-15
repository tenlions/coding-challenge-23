package de.nloewes.roshambr.converter;

import de.nloewes.roshambr.model.GameResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameResultConverterTest {

    @Test
    public void testToTarget() {
        // P1 win
        GameResult source = GameResult.PLAYER_1_WIN;
        de.nloewes.roshambr.model.dto.GameResult  target = GameResultConverter.toTarget(source);
        assertEquals(source.name(), target.getResult());

        // P2 win
        source = GameResult.PLAYER_2_WIN;
        target = GameResultConverter.toTarget(source);
        assertEquals(source.name(), target.getResult());

        // draw
        source = GameResult.DRAW;
        target = GameResultConverter.toTarget(source);
        assertEquals(source.name(), target.getResult());
    }
}