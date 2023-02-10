package de.nloewes.roshambr.model;

/**
 * DTO class for REST API representation of the result of a match of rock, paper, scissors
 */
public class GameResultResource {

    public String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
