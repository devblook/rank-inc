package me.bryang.rankinc.commands;

import me.bryang.rankinc.actions.Action;
import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
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

    private Map<String, User> userMap;

    private List<Action> globalAction;
    private Map<String, List<Action>> ranksAction;

    private VaultManager vaultManager;

    @Command(names = "")
    public void onMainSubCommand(@Sender Player sender){

        User senderUser = userMap.get(sender.getUniqueId().toString());

        if (configFile.getBoolean("settings.allow-confirm")){

            if (!senderUser.isConfirmMode()){

                senderUser.setConfirmMode(true);
                sender.sendMessage(messagesFile.getString("plugin.send-confirm"));
                return;
            }
        }

        String[] playerRankList = vaultManager.getPermission().getPlayerGroups(sender);

        boolean rankExists = false;

        String playerRankName = "";

        for (String playerRank : playerRankList){

            if (ranksFile.getString("last-rank").equalsIgnoreCase(playerRank)){
                sender.sendMessage(messagesFile.getString("error.max-rank")
                        .replace("%rank%" , playerRank));

                if (senderUser.isConfirmMode()) {
                    senderUser.setConfirmMode(false);
                }

                return;
            }

            if (ranksFile.getConfigurationSection(playerRank) != null ||
                    ranksFile.getConfigurationSection(playerRank.toUpperCase()) != null   ){

                rankExists = true;
                playerRankName = playerRank.toLowerCase();

            }
        }

        if (!rankExists){
            sender.sendMessage(messagesFile.getString("error.no-rankup"));

            if (senderUser.isConfirmMode()) {
                senderUser.setConfirmMode(false);
            }

            return;
        }

        String nextRank = "";

        if (ranksFile.getString(playerRankName.toUpperCase() + ".next-rank") != null){
            nextRank = ranksFile.getString(playerRankName.toUpperCase() + ".next-rank");
        }else if (ranksFile.getString(playerRankName.toLowerCase() + ".next-rank") != null){
            nextRank = ranksFile.getString(playerRankName.toLowerCase() + ".next-rank");

        }

        if (!configFile.getBoolean("settings.add-rank-on-rankup")) {
            vaultManager.getPermission().playerRemoveGroup(sender, playerRankName);
        }

        vaultManager.getPermission().playerAddGroup(sender, nextRank);
        sender.sendMessage(messagesFile.getString("plugin.rankup-message")
                .replace("%old-rank%", playerRankName)
                .replace("%next-rank%", nextRank).toLowerCase());


        globalAction
                .forEach(action -> action.start(sender));
        ranksAction.get(playerRankName.toLowerCase())
                .forEach(action -> action.start(sender));

        if (senderUser.isConfirmMode()) {
            senderUser.setConfirmMode(false);
        }

    }

    @Command(names = "reload", permission = "rankup.reload")
    public void onReloadSubCommand(@Sender Player sender){

        configFile.reload();
        messagesFile.reload();

        sender.sendMessage(messagesFile.getString("admin.reload"));
    }
}
