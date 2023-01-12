package me.bryang.rankinc.actions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ConsolePerformCommandAction extends Action{

    private final String messageData = "";

    @Override
    public void start(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), messageData
                .replace("%player%", player.getName()));
    }

    @Override
    public String getLine(){
        return messageData;
    }

}
