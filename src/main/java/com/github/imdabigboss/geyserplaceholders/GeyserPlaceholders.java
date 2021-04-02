package com.github.imdabigboss.geyserplaceholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GeyserPlaceholders extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder(this).register();
        } else {
            this.getLogger().warning("Geyser-Spigot or PlaceholderAPI not found! Disabling plugin.");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }
}
