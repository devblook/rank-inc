package me.bryang.rankinc.modules;

import me.bryang.rankinc.RankInc;
import me.bryang.rankinc.manager.FileManager;
import team.unnamed.inject.AbstractModule;

public class FileModule extends AbstractModule {


    private final RankInc plugin;

    public FileModule(RankInc plugin){
        this.plugin = plugin;
    }


    @Override
    public void configure() {
        bind(FileManager.class)
                .toInstance(new FileManager(plugin, "config"));

        bind(FileManager.class)
                .named("messages")
                .toInstance(new FileManager(plugin, "messages"));

        bind(FileManager.class)
                .named("ranks")
                .toInstance(new FileManager(plugin, "ranks"));

    }
}
