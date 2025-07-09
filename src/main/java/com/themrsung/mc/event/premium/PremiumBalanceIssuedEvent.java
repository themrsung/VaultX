package com.themrsung.mc.event.premium;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Called when premium balance has been issued.
 */
public class PremiumBalanceIssuedEvent extends PremiumBalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param creditor The creditor
     * @param amount   The amount issued
     */
    public PremiumBalanceIssuedEvent(VXAccountHolder creditor, long amount) {
        super();

        this.creditor = Objects.requireNonNull(creditor);
        this.amount = amount;
    }

    protected final VXAccountHolder creditor;
    protected final long amount;

    @Override
    public @NotNull VXAccountHolder getCreditor() {
        return creditor;
    }

    @Override
    public @Nullable VXAccountHolder getDebtor() {
        return null;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getImpactOnPremiumMoneySupply() {
        return amount;
    }

    @Override
    public Reason getReason() {
        return Reason.PREMIUM_BALANCE_ISSUED;
    }
}
