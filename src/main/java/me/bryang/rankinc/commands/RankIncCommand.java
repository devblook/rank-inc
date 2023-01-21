package me.bryang.rankinc.commands;

import me.bryang.rankinc.manager.FileManager;
import me.bryang.rankinc.manager.VaultManager;
import me.bryang.rankinc.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@InjectAll
@Command(names = "rankinc")
public class RankIncCommand implements CommandClass {

    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    @Named("ranks")
    private FileManager ranksFile;

    private Map<String, User> userMap;

    private VaultManager vaultManager;

    @Command(names = "")
    public void onMainSubCommand(@Sender Player sender) {
        sender.sendMessage(messagesFile.getString("admin.help-format"));


    }
    @Command(names = "reload", permission = "rank-inc.reload")
    public void onReloadSubCommand(@Sender Player sender){

        configFile.reload();
        messagesFile.reload();
        ranksFile.reload();

        sender.sendMessage(messagesFile.getString("admin.reload"));
    }

    @Command(names = "set", permission = "rank-inc.set")
    public void onSetSubCommand(@Sender Player sender, Player target, String rank){

        if (!ranksFile.isConfigurationSection(rank) && !rank.equalsIgnoreCase(ranksFile.getString("last-rank"))){
            sender.sendMessage(messagesFile.getString("rank.no-exists")
                    .replace("%rank%", rank));
            return;
        }

        User senderUser = userMap.get(target.getUniqueId().toString());

        Permission permission = vaultManager.getPermission();

        List<String> ranks = new ArrayList<>(configFile.getKeys(false));
        ranks.remove(0);

        if (configFile.getBoolean("settings.add-rank-on-rankup")) {

            int rankId = ranks.indexOf(senderUser.getPlayerRank());

            for (int id = rankId; id >= 0; id--){
                permission.playerRemoveGroup(sender, ranks.get(id));
            }

        }else{
            permission.playerRemoveGroup(sender, senderUser.getPlayerRank());
        }

        senderUser.setPlayerRank(rank);
        sender.sendMessage(messagesFile.getString("admin.set")
                .replace("%player%", sender.getName())
                .replace("%rank%", rank));

    }

}
