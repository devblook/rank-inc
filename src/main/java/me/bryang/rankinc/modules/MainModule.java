package me.bryang.rankinc.modules;

import me.bryang.rankinc.RankInc;
import me.bryang.rankinc.actions.Action;
import me.bryang.rankinc.commands.RankupCommand;
import me.bryang.rankinc.listeners.ServerChangeListener;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.services.action.ActionType;
import me.bryang.rankinc.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainModule extends AbstractModule {
    
    private final RankInc plugin;

    public MainModule(RankInc plugin){
        this.plugin = plugin;
    }

    @Override
    public void configure() {

        bind(RankInc.class).toInstance(plugin);

        install(new FileModule(plugin));

        bind(new TypeReference<Map<String, User>>(){})
                .toInstance(new HashMap<>());

        bind(new TypeReference<List<Action>>(){})
                .toInstance(new ArrayList<>());
        bind(new TypeReference<Map<String, List<Action>>>(){})
                .toInstance(new HashMap<>());

        bind(new TypeReference<Map<ActionType, Action>>(){})
                .toInstance(new HashMap<>());

        bind(RankupCommand.class)
                .singleton();
        bind(ServerChangeListener.class)
                .singleton();

        bind(VaultManager.class)
                .toInstance(new VaultManager(plugin));

        install(new ServiceModule());

    }
}
