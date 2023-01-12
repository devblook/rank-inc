package me.bryang.rankinc.actions;

import org.bukkit.entity.Player;

public class SendMessageAction extends Action {

    private final String messageData = "";

    @Override
    public void start(Player player) {
        player.sendMessage(messageData);
    }


    @Override
    public String getLine(){
        return messageData;
    }

}
