package me.bryang.rankinc.commands;

import me.bryang.rankinc.actions.Action;
import me.bryang.rankinc.actions.plugin.RankupAction;
import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.*;

@InjectAll
@Command(names = "rankup")
public class RankupCommand implements CommandClass {

    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    @Named("ranks")
    private FileManager ranksFile;

    private VaultManager vaultManager;

    private Map<String, User> userMap;

    private RankupAction rankupAction;

    @Command(names = "")
    public void onMainSubCommand(@Sender Player sender){

        User senderUser = userMap.get(sender.getUniqueId().toString());

        if (!ranksFile.isConfigurationSection(senderUser.getPlayerRank())){
            sender.sendMessage(messagesFile.getString("error.no-rankup"));
            return;
        }

        if (ranksFile.getString("last-rank").equalsIgnoreCase(senderUser.getPlayerRank())){
            sender.sendMessage(messagesFile.getString("error.max-rank")
                    .replace("%group%" , senderUser.getPlayerRank()));

            return;
        }

        if (configFile.getBoolean("settings.allow-confirm")){

            if (!senderUser.isConfirmMode()){

                senderUser.setConfirmMode(true);
                sender.sendMessage(messagesFile.getString("plugin.send-confirm"));
                return;
            }
        }

        int moneyRequirement = ranksFile.getInt(senderUser.getPlayerRank() + ".money-requirement");
        Economy economy = vaultManager.getEconomy();

        if (!economy.has(sender, moneyRequirement)){
            sender.sendMessage(messagesFile.getString("error.insufficient-money")
                    .replace("%money%", String.valueOf(moneyRequirement)));

            return;
        }

        if (senderUser.isConfirmMode()) {
            senderUser.setConfirmMode(false);
        }
        rankupAction.start(sender);
    }

    @Command(names = "auto", permission = "rank-inc.auto")
    public void onAutoSubCommand(@Sender Player sender) {

        User senderUser = userMap.get(sender.getUniqueId().toString());

        if (ranksFile.getString("last-rank").equalsIgnoreCase(senderUser.getPlayerRank())){
            sender.sendMessage(messagesFile.getString("error.max-rank")
                    .replace("%group%" , senderUser.getPlayerRank()));

            return;
        }
        Set<String> ranksKeys = ranksFile.getKeys(false);
        ranksKeys.remove("last-rank");

        for (int id = 0; id < ranksKeys.size(); id++){

            if (ranksFile.getString("last-rank").equalsIgnoreCase(senderUser.getPlayerRank())){
                break;
            }

            rankupAction.start(sender);

        }
    }
}
