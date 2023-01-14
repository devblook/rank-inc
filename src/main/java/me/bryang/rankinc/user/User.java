package me.bryang.rankinc.user;

public class User {

    boolean confirmMode = false;
    String playerRank = "";

    public boolean isConfirmMode() {
        return confirmMode;
    }

    public void setConfirmMode(boolean confirmMode) {
        this.confirmMode = confirmMode;
    }

    public String getPlayerRank() {
        return playerRank;
    }

    public void setPlayerRank(String playerRank) {
        this.playerRank = playerRank;
    }
}
