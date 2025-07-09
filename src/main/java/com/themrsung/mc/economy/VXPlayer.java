package com.themrsung.mc.economy;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An extension of {@link VXAccountHolder} made for {@link OfflinePlayer players}.
 */
public interface VXPlayer extends VXAccountHolder {
    /**
     * Returns a new {@link VXPlayer instance}.
     *
     * @param player The {@link OfflinePlayer player}
     * @return The new instance
     */
    static VXPlayer from(OfflinePlayer player) {
        return new VXPlayerImpl(player);
    }

    /**
     * Returns the {@link OfflinePlayer} which corresponds with this player.
     *
     * @return The {@link OfflinePlayer}
     */
    @NotNull OfflinePlayer getOfflinePlayer();

    /**
     * Returns the {@link Player online player} which corresponds with this player. May return {@code null}.
     *
     * @return The {@link Player online player}
     */
    @Nullable Player getPlayer();
}
