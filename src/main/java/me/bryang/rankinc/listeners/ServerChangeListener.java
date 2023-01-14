package me.bryang.rankinc.listeners;

import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@InjectAll
public class ServerChangeListener implements Listener{

    private Map<String, User> userMap;

    private FileManager configFile;

    @Named("ranks")
    private FileManager ranksFile;

    @Named("players")
    private FileManager playersFile;

    private VaultManager vaultManager;


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userMap.put(event.getPlayer().getUniqueId().toString(), new User());

        System.out.println(Arrays.toString(ranksFile.getKeys(false).toArray()));
        String playerUniqueId = event.getPlayer().getUniqueId().toString();

        String firstRank = new ArrayList<>(ranksFile.getKeys(false)).get(1);

        userMap.get(playerUniqueId)
                .setPlayerRank(firstRank);
        playersFile.set(playerUniqueId + ".rank", firstRank);

        if (configFile.getBoolean("settings.give-rank-on-join")) {
            vaultManager.getPermission().playerAddGroup(event.getPlayer(), firstRank);

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        String playerUniqueId = event.getPlayer().getUniqueId().toString();

        if (userMap.get(playerUniqueId).isConfirmMode()){
            userMap.get(playerUniqueId).setConfirmMode(false);
        }

    }
}
