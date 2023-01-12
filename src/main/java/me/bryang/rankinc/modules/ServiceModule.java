package me.bryang.rankinc.modules;

import me.bryang.rankinc.services.ListenerService;
import me.bryang.rankinc.services.Service;
import me.bryang.rankinc.services.action.ActionService;
import me.bryang.rankinc.services.command.CommandService;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    public void configure() {

       multibind(Service.class)
               .asSet()
               .to(CommandService.class)
               .to(ListenerService.class)
               .to(ActionService.class);
    }
}
