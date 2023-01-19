package me.bryang.rankinc.services;

import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.user.User;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Map;

@InjectAll
public class PlayersService implements Service {

    @Named("players")
    private FileManager playersFile;

    private Map<String, User> userMap;


    @Override
    public void init() {
        playersFile.
                getKeys(false)
                .forEach(keys -> {

                    userMap.put(keys, new User());
                    userMap.get(keys)
                            .setPlayerRank(playersFile.getString(keys + ".rank"));

                });
    }
}