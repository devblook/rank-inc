package me.bryang.rankinc.services.command;

import me.bryang.rankinc.RankInc;
import me.bryang.rankinc.commands.RankupCommand;
import me.bryang.rankinc.services.Service;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import team.unnamed.inject.InjectAll;

@InjectAll
public class CommandService implements Service {

    private RankInc plugin;

    private RankupCommand rankupCommand;

    @Override
    public void init() {

        CommandManager commandManager = new BukkitCommandManager("rankinc");

        PartInjector partInjector = PartInjector.create();
        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        commandManager.registerCommands(builder.fromClass(rankupCommand));

        plugin.getLogger().info("Commands loaded!");
    }
}
