package de.nloewes.roshambr.converter;

import de.nloewes.roshambr.model.GameResult;

/**
 * Converter utility class to provide conversion methods from service-layer objects and DAOs related to {@link GameResult} to DTOs and vice versa
 *
 * @author nloewes
 */
public class GameResultConverter {

    /**
     * Converts a given {@link GameResult} to an equivalent {@link de.nloewes.roshambr.model.dto.GameResult}
     * @param source the GameResult to convert
     * @return the converted GameResultResource
     */
    public static de.nloewes.roshambr.model.dto.GameResult toTarget(GameResult source) {
        de.nloewes.roshambr.model.dto.GameResult  target = new de.nloewes.roshambr.model.dto.GameResult();
        target.setResult(source.name());
        return target;
    }
}
