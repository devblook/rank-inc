package me.bryang.rankinc.actions.plugin;

import me.bryang.rankinc.actions.Action;
import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.user.User;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Map;

public class RankupAction extends Action {

    private final String messageData = "";


    @Inject
    private FileManager configFile;

    @Inject @Named("ranks")
    private FileManager ranksFile;

    @Inject @Named("messages")
    private FileManager messagesFile;

    @Inject @Named("players")
    private FileManager playersFile;

    @Inject
    private Map<String, User> userMap;

    @Inject
    private List<Action> globalAction;

    @Inject
    private Map<String, List<Action>> ranksAction;

    @Inject
    private VaultManager vaultManager;


    @Override
    public void start(Player sender) {

        Bukkit.getLogger().info("rer " + userMap.values());

        User senderUser = userMap.get(sender.getUniqueId().toString());

        String oldRank = senderUser.getPlayerRank();

        String nextRank = ranksFile.getString(oldRank + ".next-rank");

        if (!configFile.getBoolean("settings.add-rank-on-rankup")) {
            vaultManager.getPermission().playerRemoveGroup(sender, oldRank);
        }


        vaultManager.getPermission().playerAddGroup(sender, nextRank);

        playersFile.set(sender.getUniqueId() + ".rank", nextRank);
        playersFile.save();

        senderUser.setPlayerRank(nextRank);

        sender.sendMessage(messagesFile.getString("plugin.rankup-message")
                .replace("%old-rank%", oldRank)
                .replace("%next-rank%", nextRank));

        globalAction
                .forEach(action -> action.start(sender));
        ranksAction.get(oldRank)
                .forEach(action -> action.start(sender));
    }

    @Override
    public String getLine(){
        return messageData;
    }
}
