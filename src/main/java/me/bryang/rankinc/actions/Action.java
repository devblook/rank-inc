package me.bryang.rankinc.actions;

import org.bukkit.entity.Player;

public abstract class Action {

    private String messageData = "";

    public void start(Player player) {
    }

    public void setLine(String text){
       this.messageData = text;
    }

    public String getLine(){
        return messageData;
    }
}
