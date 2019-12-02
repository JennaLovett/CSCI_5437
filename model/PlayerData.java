package finalproject.Mia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerData
{
    private int playerNumber;
    private int turn;   //0 = false, 1 = true
    private int lives;
    private int currentScore;
    private String screen;
    private int flag; //just got done playing if 1, otherwise 0
    private int sentDiceValue;

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
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

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getSentDiceValue() {
        return sentDiceValue;
    }

    public void setSentDiceValue(int sentDiceValue) {
        this.sentDiceValue = sentDiceValue;
    }

    public String toString()
    {
        return "{\"playerNumber\":\"" + getPlayerNumber() + "\", \"turn\":\"" + getTurn() +
                "\", \"lives\":\"" + getLives() + "\", \"currentScore\":\"" + getCurrentScore() +
                "\", \"screen\":\"" + getScreen() +
                "\", \"flag\":\"" + getFlag() +
                "\", \"sentDiceValue\":\"" + getSentDiceValue() + "\"}";
    }

}
