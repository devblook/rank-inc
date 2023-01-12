package me.bryang.rankinc.manager;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultManager {

    private Permission permission;

    public VaultManager(){
        start();
    }

    public void start(){
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);

        permission = rsp.getProvider();

        if (!permission.hasGroupSupport()){
            Bukkit.getLogger().info("[!] Error: You need ap erms plugin.");
        }
    }

    public Permission getPermission() {
        return permission;
    }
}
