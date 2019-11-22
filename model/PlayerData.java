package finalproject.Mia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerData
{
    private int playerNumber;
    private boolean isTurn;
    private int lives;
    private int currentScore;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean turn) {
        isTurn = turn;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public String toString()
    {
        return "PlayerData{\nplayerNumber=" + getPlayerNumber() + "\n" +
                "isTurn=" + isTurn() + "\n" +
                "lives=" + getLives() + "\n" +
                "currentScore=" + getCurrentScore() + "\n}";
    }

}
