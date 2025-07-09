package com.themrsung.mc.economy;

/**
 * Sub-interface of {@link VXAccountHolder} used by other plugins.
 */
public interface VXPluginUser extends VXAccountHolder {
    /**
     * Called when an {@link EconomyX economy} modifies the balance of this user.
     *
     * @param economy The economy which modified the balance
     * @param impact  The net impact to this user's balance
     */
    void onBalanceModified(EconomyX economy, double impact);

    /**
     * Called when an {@link EconomyX economy} modifies the premium balance of this user.
     *
     * @param economy The economy which modified the balance
     * @param impact  The net impact to this user's balance
     */
    void onPremiumBalanceModified(EconomyX economy, long impact);
}
