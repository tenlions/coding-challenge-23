package de.nloewes.roshambr.service;

import de.nloewes.roshambr.model.GameChoice;
import de.nloewes.roshambr.model.GameResult;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Main service class to provide and represent the logic of a game of rock, paper, scissors.
 *
 * @author nloewes
 */
@Component
public class GameService {

    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

    /**
     * Plays a game of rock, paper, scissors based on a given {@link GameChoice} and returns the match result.
     * The CPU opponent's turn is chosen randomly.
     *
     * After choosing the opponent's turn, the result is calculated abiding the following rules:
     *      Rock beats Scissors.
     *      Scissors beat Paper.
     *      Paper beats Rock.
     *
     *      Identical choices result in a draw.
     *
     * @param playerChoice the given {@link GameChoice} as input by the player
     * @return the {@link GameResult} of the match
     */
    @Timed(value = "cpuMatch.time", description = "Time taken to play a match against the CPU")
    public GameResult playCpuMatch(GameChoice playerChoice) {
        LOG.info("Starting new match against CPU. Player choice: {}", playerChoice);

        GameChoice cpuChoice = getCpuChoice();
        LOG.info("CPU choice: {}", cpuChoice);

        GameResult result = calculateResult(playerChoice, cpuChoice);
        LOG.info("Game result: {} ({} x {})", result, playerChoice, cpuChoice);

        return result;
    }

    /**
     * Calculates the result of a game of rock, paper, scissors based on the given choices of two players.
     * The result is calculated abiding the following rules:
     *      Rock beats Scissors.
     *      Scissors beat Paper.
     *      Paper beats Rock.
     *      Identical choices result in a draw.
     *
     * Marked protected for testing purposes
     *
     * @param player1Choice the choice of the first player
     * @param player2Choice the choice of the second player
     * @return the {@link GameResult} of the match
     */
    @Timed(value = "calcResult.time", description = "Time taken to calculate the outcome of a match based on 2 choices")
    protected GameResult calculateResult(GameChoice player1Choice, GameChoice player2Choice) {
        switch (player1Choice) {
            case ROCK:
                if (GameChoice.ROCK.equals(player2Choice)) return GameResult.DRAW;
                if (GameChoice.SCISSORS.equals(player2Choice)) return GameResult.PLAYER_1_WIN;
                break;
            case PAPER:
                if (GameChoice.PAPER.equals(player2Choice)) return GameResult.DRAW;
                if (GameChoice.ROCK.equals(player2Choice)) return GameResult.PLAYER_1_WIN;
                break;
            case SCISSORS:
                if (GameChoice.SCISSORS.equals(player2Choice)) return GameResult.DRAW;
                if (GameChoice.PAPER.equals(player2Choice)) return GameResult.PLAYER_1_WIN;
                break;
        }

        return GameResult.PLAYER_2_WIN;
    }

    /**
     * Returns a single, randomly chosen {@link GameChoice} for the CPU player
     *
     * @return a random {@link GameChoice}
     */
    private GameChoice getCpuChoice() {
        return GameChoice.getRandomChoice();
    }
}
