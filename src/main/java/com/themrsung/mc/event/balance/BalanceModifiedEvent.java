package com.themrsung.mc.event.balance;

import com.themrsung.mc.economy.VXAccountHolder;
import com.themrsung.mc.event.VXEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.UUID;

/**
 * Called when the balance of a {@link VXAccountHolder account holder} is modified for any reason.
 */
public abstract class BalanceModifiedEvent extends VXEvent {
    /**
     * Creates a nwe event.
     */
    protected BalanceModifiedEvent() {
        super();
    }

    /**
     * Creates a new event.
     *
     * @param uniqueId The unique identifier
     */
    protected BalanceModifiedEvent(UUID uniqueId) {
        super(uniqueId);
    }

    /**
     * Creates a new event.
     *
     * @param uniqueId The unique identifier
     * @param date     The date
     */
    protected BalanceModifiedEvent(UUID uniqueId, Date date) {
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
    public abstract double getAmount();

    /**
     * Returns the net impact on the total money supply.
     *
     * @return The net impact on the total money supply
     */
    public abstract double getImpactOnMoneySupply();

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
        BALANCE_ISSUED,

        /**
         * Balance transferred between account holders.
         */
        BALANCE_TRANSFERRED,

        /**
         * Balance retired from circulation via VaultX.
         */
        BALANCE_RETIRED,

        /**
         * Balance deposited due to a legacy API call from Vault.
         */
        BALANCE_DEPOSITED_BY_VAULT,

        /**
         * Balance withdrawn due to a legacy API call from Vault.
         */
        BALANCE_WITHDRAWN_BY_VAULT,

        /**
         * An unspecified reason.
         */
        UNKNOWN;
    }
}
