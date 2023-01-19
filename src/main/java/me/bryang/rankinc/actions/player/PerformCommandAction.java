package me.bryang.rankinc.actions.player;

import me.bryang.rankinc.actions.Action;
import org.bukkit.entity.Player;

public class PerformCommandAction extends Action {

    private final String messageData = "";

    @Override
    public void start(Player player) {
        player.performCommand(messageData);
    }

    @Override
    public String getLine(){
        return messageData;
    }
}
