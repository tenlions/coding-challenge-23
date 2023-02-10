package de.nloewes.roshambr.converter;

import de.nloewes.roshambr.model.GameResultResource;
import de.nloewes.roshambr.model.GameResult;

/**
 * Converter utility class to provide conversion methods from service-layer objects and DAOs related to {@link GameResult} to DTOs and vice versa
 *
 * @author nloewes
 */
public class GameResultConverter {

    /**
     * Converts a given {@link GameResult} to an equivalent {@link GameResultResource}
     * @param source the GameResult to convert
     * @return the converted GameResultResource
     */
    public static GameResultResource toTarget(GameResult source) {
        GameResultResource target = new GameResultResource();
        target.setResult(source.name());
        return target;
    }
}
