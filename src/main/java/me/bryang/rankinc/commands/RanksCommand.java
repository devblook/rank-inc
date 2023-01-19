package me.bryang.rankinc.commands;

import me.bryang.rankinc.manager.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Set;

@InjectAll
@Command(names = "ranks")
public class RanksCommand implements CommandClass {


    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    @Named("ranks")
    private FileManager ranksFile;

    @Command(names = "")
    public void onMainSubCommand(@Sender Player sender) {
        sender.sendMessage(messagesFile.getString("plugin.ranks.title"));

        Set<String> ranksKeys = ranksFile.getKeys(false);
        ranksKeys.remove("last-rank");

        ranksKeys.forEach(oldRank -> {

            String nextRank = ranksFile.getString(oldRank + ".next-rank");
            int moneyRequirement = ranksFile.getInt(oldRank + ".money-requirement");

            sender.sendMessage(messagesFile.getString("plugin.ranks.format")
                    .replace("%old-rank%", oldRank)
                    .replace("%next-rank%", nextRank)
                    .replace("%money-requirement%", String.valueOf(moneyRequirement)));
        });
    }
    @Command(names = "reload", permission = "rank-inc.reload")
    public void onReloadSubCommand(@Sender Player sender){

        configFile.reload();
        messagesFile.reload();
        ranksFile.reload();

        sender.sendMessage(messagesFile.getString("admin.reload"));
    }

}
