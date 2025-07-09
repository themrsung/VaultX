package com.themrsung.mc.economy;

import org.jetbrains.annotations.NotNull;

/**
 * Abstract subclass of {@link VXAccountHolder} used by other plugins. VaultX assumes that plugin
 * users' IO is handled by their responsible plugins, and ignores them when saving or loading users.
 */
public abstract class VXPluginUser implements VXAccountHolder {
    @Override
    public final @NotNull AccountHolderType getType() {
        return AccountHolderType.THIRD_PARTY_PLUGIN;
    }

    /**
     * Called when an {@link EconomyX economy} modifies the balance of this user.
     *
     * @param economy The economy which modified the balance
     * @param impact  The net impact to this user's balance
     */
    abstract void modifyBalance(EconomyX economy, double impact);

    /**
     * Called when an {@link EconomyX economy} modifies the premium balance of this user.
     *
     * @param economy The economy which modified the balance
     * @param impact  The net impact to this user's balance
     */
    abstract void modifyPremiumBalance(EconomyX economy, long impact);
}
