package com.github.imdabigboss.geyserplaceholders;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.geysermc.connector.GeyserConnector;
import org.geysermc.connector.network.BedrockProtocol;
import org.geysermc.connector.network.session.GeyserSession;

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
            case "minecraft_version":
                return GeyserConnector.MINECRAFT_VERSION;
            case "bedrock_version":
                return getBedrockVersion();
            case "version":
                return GeyserConnector.VERSION;
            case "git_version":
                return GeyserConnector.GIT_VERSION;
            case "platform":
                return getPlayerPlatform(player);
            case "game_version":
                return getPlayerGameVersion(player);
        }

        return null;
    }

    /**
     * Get the supported bedrock version
     * @return The supported bedrock version
     */
    public String getBedrockVersion() {
        String bedrockVersions;
        List<BedrockPacketCodec> supportedCodecs = BedrockProtocol.SUPPORTED_BEDROCK_CODECS;
        if (supportedCodecs.size() > 1) {
            bedrockVersions = supportedCodecs.get(0).getMinecraftVersion() + " - " + supportedCodecs.get(supportedCodecs.size() - 1).getMinecraftVersion();
        } else {
            bedrockVersions = BedrockProtocol.DEFAULT_BEDROCK_CODEC.getMinecraftVersion();
        }

        return bedrockVersions;
    }

    /**
     * Get the platform of the specified player
     * @param player The player to get the platform for
     * @return The player's platform version
     */
    public String getPlayerPlatform(Player player) {
        GeyserSession geyserPlayer = GeyserConnector.getInstance().getPlayerByUuid(player.getUniqueId());

        if (geyserPlayer != null) {
            return geyserPlayer.getClientData().getDeviceOS().toString();
        } else {
            return "";
        }
    }

    /**
     * Get the game version of the specified player
     * @param player The player to get the game version for
     * @return The player's game version
     */
    public String getPlayerGameVersion(Player player) {
        GeyserSession geyserPlayer = GeyserConnector.getInstance().getPlayerByUuid(player.getUniqueId());

        if (geyserPlayer != null) {
            return geyserPlayer.getClientData().getGameVersion();
        } else {
            return "";
        }
    }
}