package com.github.imdabigboss.geyserplaceholders;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.Player;

import org.geysermc.geyser.GeyserImpl;
import org.geysermc.geyser.session.GeyserSession;
import org.geysermc.geyser.network.MinecraftProtocol;

import java.util.List;

public class Placeholder extends PlaceholderExpansion {

    private GeyserPlaceholders plugin;

    public Placeholder(GeyserPlaceholders plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "geyser";
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if(player == null) {
            return "";
        }

        switch (identifier) {
            case "java_version":
                return getJavaVersion();
            case "bedrock_version":
                return getBedrockVersion();
            case "version":
                return GeyserImpl.VERSION;
            case "git_version":
                return GeyserImpl.GIT_VERSION;
            case "platform":
                return getPlayerPlatform(player);
            case "game_version":
                return getPlayerGameVersion(player);
            case "input_mode":
                return getPlayerInputMode(player);
        }

        return null;
    }

    /**
     * Get the supported java version
     * @return The supported java version
     */
    public String getJavaVersion() {
        String javaVersions;
        List<String> supportedJavaVersions = MinecraftProtocol.getJavaVersions();
        if (supportedJavaVersions.size() > 1) {
            javaVersions = supportedJavaVersions.get(0) + " - " + supportedJavaVersions.get(supportedJavaVersions.size() - 1);
        } else {
            javaVersions = supportedJavaVersions.get(0);
        }

        return javaVersions;
    }
    
    /**
     * Get the supported bedrock version
     * @return The supported bedrock version
     */
    public String getBedrockVersion() {
        String bedrockVersions;
        List<BedrockPacketCodec> supportedCodecs = MinecraftProtocol.SUPPORTED_BEDROCK_CODECS;
        if (supportedCodecs.size() > 1) {
            bedrockVersions = supportedCodecs.get(0).getMinecraftVersion() + " - " + supportedCodecs.get(supportedCodecs.size() - 1).getMinecraftVersion();
        } else {
            bedrockVersions = MinecraftProtocol.SUPPORTED_BEDROCK_CODECS.get(0).getMinecraftVersion();
        }

        return bedrockVersions;
    }

    /**
     * Get the platform of the specified player
     * @param player The player
     * @return The player's platform version
     */
    public String getPlayerPlatform(Player player) {
        GeyserSession geyserPlayer = GeyserImpl.getInstance().connectionByUuid(player.getUniqueId());

        if (geyserPlayer != null) {
            return geyserPlayer.getClientData().getDeviceOs().toString();
        } else {
            return "";
        }
    }

    /**
     * Get the game version of the specified player
     * @param player The player
     * @return The player's game version
     */
    public String getPlayerGameVersion(Player player) {
        GeyserSession geyserPlayer = GeyserImpl.getInstance().connectionByUuid(player.getUniqueId());

        if (geyserPlayer != null) {
            return geyserPlayer.getClientData().getGameVersion();
        } else {
            return "";
        }
    }

    /**
     * Get the input mode of the specified player
     * @param player The player
     * @return The player's input mode
     */
    public String getPlayerInputMode(Player player) {
        GeyserSession geyserPlayer = GeyserImpl.getInstance().connectionByUuid(player.getUniqueId());

        if (geyserPlayer != null) {
            return geyserPlayer.getClientData().getCurrentInputMode().toString();
        } else {
            return "";
        }
    }
}