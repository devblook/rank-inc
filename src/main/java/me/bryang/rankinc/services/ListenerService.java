package me.bryang.rankinc.services;

import me.bryang.rankinc.RankInc;
import me.bryang.rankinc.listeners.ServerChangeListener;
import org.bukkit.Bukkit;
import team.unnamed.inject.InjectAll;

@InjectAll
public class ListenerService implements Service{

    private RankInc plugin;
    private ServerChangeListener serverChangeListener;

    @Override
    public void init() {
        Bukkit.getPluginManager().registerEvents(serverChangeListener, plugin);
    }
}
