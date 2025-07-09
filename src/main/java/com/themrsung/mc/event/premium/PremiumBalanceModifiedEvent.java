package com.themrsung.mc.event.premium;

import com.themrsung.mc.economy.VXAccountHolder;
import com.themrsung.mc.event.VXEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * Called when the premium balance of a {@link VXAccountHolder account holder} is modified for any reason.
 */
public abstract class PremiumBalanceModifiedEvent extends VXEvent {
    /**
     * Creates a nwe event.
     */
    protected PremiumBalanceModifiedEvent() {
        super();
    }

    /**
     * Creates a new event.
     *
     * @param uniqueId The unique identifier
     */
    protected PremiumBalanceModifiedEvent(UUID uniqueId) {
        super(uniqueId);
    }

    /**
     * Creates a new event.
     *
     * @param uniqueId The unique identifier
     * @param date     The date
     */
    protected PremiumBalanceModifiedEvent(UUID uniqueId, Date date) {
        super(uniqueId, date);
    }

    /**
     * Returns the account holder whose balance has been increased.
     *
     * @return The account holder if present, {@code null} otherwise
     */
    @Nullable
    public abstract VXAccountHolder getCreditor();

    /**
     * Returns the account holder whose balance has been decreased.
     *
     * @return The account holder if present, {@code null} otherwise
     */
    @Nullable
    public abstract VXAccountHolder getDebtor();

    /**
     * Returns the amount of balance which has been transferred or modified.
     *
     * @return The amount of balance which has been transferred or modified
     */
    public abstract long getAmount();

    /**
     * Returns the net impact on the total money supply.
     *
     * @return The net impact on the total money supply
     */
    public abstract long getImpactOnPremiumMoneySupply();

    /**
     * Returns the {@link Reason reason} why this event has occurred.
     *
     * @return The reason for this event
     */
    public abstract Reason getReason();

    /**
     * Possible reasons for modifying the balance of an account holder.
     */
    public enum Reason {
        /**
         * Balance issued via the VaultX API.
         */
        PREMIUM_BALANCE_ISSUED,

        /**
         * Balance transferred between account holders.
         */
        PREMIUM_BALANCE_TRANSFERRED,

        /**
         * Balance retired from circulation via VaultX.
         */
        PREMIUM_BALANCE_RETIRED,

        /**
         * An unspecified reason.
         */
        UNKNOWN;
    }
}
