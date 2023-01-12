package me.bryang.rankinc.manager;

import me.bryang.rankinc.RankInc;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    private Permission permission;
    private final RankInc plugin;

    public VaultManager(RankInc plugin){
        this.plugin = plugin;
        start();
    }

    public void start(){
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        permission = rsp.getProvider();

        if (!permission.hasGroupSupport()){
            plugin.getLogger().severe("[!] Error: You need a perms plugin.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public Permission getPermission() {
        return permission;
    }
}
