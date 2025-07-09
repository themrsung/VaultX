package com.themrsung.mc.economy;

import com.themrsung.mc.VaultX;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * <h1>The economy class for VaultX.</h1>
 * EconomyX is designed as a closed system. Other than by the legacy Vault API, balance
 * cannot be created out of thin air, or removed in the same manner. Balance, both normal and
 * premium should be {@link #issueBalance(VXAccountHolder, double) issued} or
 * {@link #retireBalance(VXAccountHolder, double) retired}. Either method calls will trigger
 * an event, which listeners can hook to.
 * <h3>Usage by other plugins</h3>
 * EconomyX can be accessed by registering an account holder in the plugin's name, using an
 * implementation of {@link VXPluginUser}. It is strongly recommended that this is treated
 * equally to an ordinary user. This means even third party plugins should have limited balance.
 * (at least that's the way EconomyX is designed to work)
 */
public interface EconomyX {
    /**
     * Returns a new vanilla instance of {@link EconomyX}, which does NOT support legacy Vault API.
     *
     * @param vx The {@link VaultX plugin}
     * @return The new instance
     */
    static EconomyX newVanillaInstance(VaultX vx) {
        return new EconomyXImpl(vx);
    }

    /**
     * Returns a new {@link EconomyVX VX instance}, which supports legacy Vault API.
     *
     * @param vx The {@link VaultX plugin}
     * @return The new instance
     */
    static EconomyVX newVXInstance(VaultX vx) {
        return new EconomyVXImpl(vx);
    }

    /**
     * Returns a {@link Stream stream} of the accounts in this economy.
     *
     * @return A stream of the accounts in this economy
     */
    @NotNull Stream<VXAccountHolder> getAccounts();

    /**
     * Searches for accounts by the given name, then returns the resulting {@link Stream stream}.
     *
     * @param name The name to search for
     * @return The resulting stream
     */
    @NotNull Stream<VXAccountHolder> getAccountsByName(String name);

    /**
     * Returns the account holder with the given unique identifier.
     *
     * @param uniqueId The unique identifier
     * @return The account holder if found, {@code null} otherwise
     */
    @Nullable VXAccountHolder getAccount(UUID uniqueId);

    /**
     * Returns the account holder corresponding with the player if found. If an account holder is not found,
     * this returns a new account holder in the player's name.
     *
     * @param player The player
     * @return The resulting account holder
     * @see VXPlayer
     */
    @NotNull VXPlayer getAccountOrNew(OfflinePlayer player);

    /**
     * Adds an account holder to this economy.
     *
     * @param account The account holder to add
     * @return {@code true} if the account was successfully added
     */
    boolean addAccount(VXAccountHolder account);

    /**
     * Removes an account holder from this economy.
     *
     * @param account The account holder to remove
     * @return {@code true} if the account was successfully removed
     */
    boolean removeAccount(VXAccountHolder account);

    /**
     * Returns whether this economy contains the given account.
     *
     * @param account The account to query
     * @return {@code true} if the account is registered
     */
    boolean containsAccount(VXAccountHolder account);

    /**
     * Attempts to transfer balance between accounts. This operation will succeed if both accounts are valid,
     * are registered to this economy, and the sender has enough balance to cover the amount being sent.
     *
     * @param sender    The sender
     * @param recipient The recipient
     * @param amount    The amount being sent
     * @return The transaction result
     */
    @NotNull BalanceTransferResult transferBalance(VXAccountHolder sender, VXAccountHolder recipient, double amount);

    /**
     * Adds balance to the creditor's account.
     *
     * @param creditor The creditor
     * @param amount   The amount to add
     * @return {@code true} if the balance was added, {@code false} otherwise
     */
    boolean issueBalance(VXAccountHolder creditor, double amount);

    /**
     * Removes balance from the debtor's account. This operation does NOT check if the debtor has enough balance.
     *
     * @param debtor The debtor
     * @param amount The amount to remove
     * @return {@code true} if the balance was removed, {@code false} otherwise
     */
    boolean retireBalance(VXAccountHolder debtor, double amount);

    /**
     * Attempts to transfer premium balance between accounts. This operation will succeed if both accounts are valid,
     * are registered to this economy, and the sender has enough premium balance to cover the amount being sent.
     *
     * @param sender    The sender
     * @param recipient The recipient
     * @param amount    The amount being sent
     * @return The transaction result
     */
    @NotNull PremiumBalanceTransferResult transferPremiumBalance(VXAccountHolder sender, VXAccountHolder recipient, long amount);

    /**
     * Adds premium balance to the creditor's account.
     *
     * @param creditor The creditor
     * @param amount   The amount to add
     * @return {@code true} if the balance was added, {@code false} otherwise
     */
    boolean issuePremiumBalance(VXAccountHolder creditor, long amount);

    /**
     * Removes premium balance from the debtor's account. This operation does NOT check if the debtor has enough
     * premium balance.
     *
     * @param debtor The debtor
     * @param amount The amount to remove
     * @return {@code true} if the balance was removed, {@code false} otherwise
     */
    boolean retirePremiumBalance(VXAccountHolder debtor, long amount);
}
