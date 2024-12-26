package herogame.model;

public class Player {
    private int playerId;
    private int nationalId;
    private String playerName;
    private int highScore;
    private int level;
    private String nationalName;

    public Player() {}

    public Player(int playerId, int nationalId, String playerName, int highScore, int level) {
        this.playerId = playerId;
        this.nationalId = nationalId;
        this.playerName = playerName;
        this.highScore = highScore;
        this.level = level;
    }

    // Getters and Setters
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNationalName() {
        return nationalName;
    }

    public void setNationalName(String nationalName) {
        this.nationalName = nationalName;
    }
}