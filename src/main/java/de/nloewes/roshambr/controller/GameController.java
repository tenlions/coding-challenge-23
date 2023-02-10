package de.nloewes.roshambr.controller;

import de.nloewes.roshambr.converter.GameChoiceConverter;
import de.nloewes.roshambr.converter.GameResultConverter;
import de.nloewes.roshambr.model.GameChoiceResource;
import de.nloewes.roshambr.model.GameResultResource;
import de.nloewes.roshambr.service.GameService;
import de.nloewes.roshambr.model.GameChoice;
import de.nloewes.roshambr.model.GameResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class GameController {

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @RequestMapping("/cpu")
    public GameResultResource postMatch(GameChoiceResource choice) {
        GameChoice playerChoice = GameChoiceConverter.toSource(choice);

        GameResult result = gameService.playCpuMatch(playerChoice);

        GameResultResource resultResource = GameResultConverter.toTarget(result);

        return resultResource;
    }
}
