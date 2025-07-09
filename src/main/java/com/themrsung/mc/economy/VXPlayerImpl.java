package com.themrsung.mc.economy;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * Default implementation of {@link VXPlayer}.
 *
 * @see VXPlayer
 */
class VXPlayerImpl extends VXAbstractAccountHolder implements VXPlayer {
    /**
     * Creates a new player with no balance.
     *
     * @param player The {@link OfflinePlayer offline player}
     */
    VXPlayerImpl(OfflinePlayer player) {
        this(player, 0, 0);
    }

    /**
     * Local constructor. Starting balance should be zero unless absolutely necessary.
     *
     * @param player         The player
     * @param balance        The balance
     * @param premiumBalance The premium balance
     */
    VXPlayerImpl(OfflinePlayer player, double balance, long premiumBalance) {
        this.player = Objects.requireNonNull(player);
        this.balance = balance;
        this.premiumBalance = premiumBalance;
    }

    private final OfflinePlayer player;

    double balance;
    long premiumBalance;

    @Override
    public @NotNull UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public @Nullable String getName() {
        return player.getName();
    }

    @Override
    public @NotNull AccountHolderType getType() {
        return AccountHolderType.PLAYER;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double getWithdrawableBalance() {
        return balance;
    }

    @Override
    public long getPremiumBalance() {
        return premiumBalance;
    }

    @Override
    public long getWithdrawablePremiumBalance() {
        return premiumBalance;
    }

    @Override
    public @NotNull OfflinePlayer getOfflinePlayer() {
        return player;
    }

    @Override
    public @Nullable Player getPlayer() {
        return Bukkit.getPlayer(player.getUniqueId());
    }
}
