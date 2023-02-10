package de.nloewes.roshambr.model;

/**
 * DTO class for REST API representation of a player's turn choice during match of rock, paper, scissors
 */
public class GameChoiceResource {

    private String playerChoice;

    public String getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(String playerChoice) {
        this.playerChoice = playerChoice;
    }
}
