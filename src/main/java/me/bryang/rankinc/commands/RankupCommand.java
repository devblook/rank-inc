package me.bryang.rankinc.commands;

import me.bryang.rankinc.actions.Action;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@InjectAll
@Command(names = "rankup")
public class RankupCommand implements CommandClass {

    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    @Named("ranks")
    private FileManager ranksFile;

    @Named("players")
    private FileManager playersFile;


    private Map<String, User> userMap;

    private List<Action> globalAction;
    private Map<String, List<Action>> ranksAction;

    private VaultManager vaultManager;

    @Command(names = "")
    public void onMainSubCommand(@Sender Player sender){

        User senderUser = userMap.get(sender.getUniqueId().toString());

        List<String> playerRankList = new ArrayList<>(Arrays.asList(vaultManager.getPermission().getPlayerGroups(sender)));

        if (playerRankList.contains(senderUser.getPlayerRank())){
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

        String oldRank = senderUser.getPlayerRank();
        int moneyRequirement = ranksFile.getInt(oldRank + ".money-requirement");
        Economy economy = vaultManager.getEconomy();

        if (!economy.has(sender, moneyRequirement)){
            sender.sendMessage(messagesFile.getString("error.insufficient-money")
                    .replace("%money%", String.valueOf(moneyRequirement)));

            return;
        }

        String nextRank = ranksFile.getString(oldRank + ".next-rank");

        if (!configFile.getBoolean("settings.add-rank-on-rankup")) {
            vaultManager.getPermission().playerRemoveGroup(sender, oldRank);
        }


        vaultManager.getPermission().playerAddGroup(sender, nextRank);
        playersFile.set(sender.getUniqueId() + ".rank", nextRank);
        senderUser.setPlayerRank(nextRank);

        sender.sendMessage(messagesFile.getString("plugin.rankup-message")
                .replace("%old-rank%", oldRank)
                .replace("%next-rank%", nextRank).toLowerCase());

        globalAction
                .forEach(action -> action.start(sender));
        ranksAction.get(oldRank)
                .forEach(action -> action.start(sender));

        if (senderUser.isConfirmMode()) {
            senderUser.setConfirmMode(false);
        }

    }

}
