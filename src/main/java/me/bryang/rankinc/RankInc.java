package me.bryang.rankinc;

import me.bryang.rankinc.modules.MainModule;
import me.bryang.rankinc.services.Service;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.List;

public class RankInc extends JavaPlugin {

    @Inject
    private List<Service> services;

    @Override
    public void onEnable() {

        Injector injector = Injector.create(new MainModule(this));
        injector.injectMembers(this);

        services.forEach(Service::init);
    }

    @Override
    public void onDisable() {
    }
}
