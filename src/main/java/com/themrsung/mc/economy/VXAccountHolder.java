package com.themrsung.mc.economy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * An entity which can hold a VaultX account.
 */
public interface VXAccountHolder {
    /**
     * Returns the unique identifier of this holder.
     *
     * @return The unique identifier of this holder
     */
    @NotNull UUID getUniqueId();

    /**
     * Returns the name of this holder.
     *
     * @return The name of this holder
     */
    @Nullable String getName();

    /**
     * Returns the balance of this holder.
     *
     * @return The balance of this holder
     */
    double getBalance();

    /**
     * Returns the withdrawable balance of this holder.
     *
     * @return The withdrawable balance of this holder
     */
    double getWithdrawableBalance();

    /**
     * Returns the premium currency balance of this holder.
     *
     * @return The premium currency balance of this holder
     */
    long getPremiumBalance();

    /**
     * Returns the withdrawable premium balance of this holder.
     *
     * @return The withdrawable premium balance of this holder
     */
    long getWithdrawablePremiumBalance();
}
