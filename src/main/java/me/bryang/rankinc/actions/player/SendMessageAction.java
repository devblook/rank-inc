package me.bryang.rankinc.actions.player;

import me.bryang.rankinc.actions.Action;
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
