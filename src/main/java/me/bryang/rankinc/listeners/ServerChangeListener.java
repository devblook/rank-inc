package me.bryang.rankinc.listeners;

import me.bryang.rankinc.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.Map;

public class ServerChangeListener implements Listener{
    
    @Inject
    private Map<String, User> userMap;


    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        userMap.put(event.getPlayer().getUniqueId().toString(), new User());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        userMap.remove(event.getPlayer().getUniqueId().toString());

    }
}
