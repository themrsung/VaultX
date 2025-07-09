package com.themrsung.mc.event.premium;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Called when premium balance has been retired.
 */
public class PremiumBalanceRetiredEvent extends PremiumBalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param debtor The debtor
     * @param amount The amount retired
     */
    public PremiumBalanceRetiredEvent(VXAccountHolder debtor, long amount) {
        super();

        this.debtor = Objects.requireNonNull(debtor);
        this.amount = amount;
    }

    protected final VXAccountHolder debtor;
    protected final long amount;

    @Override
    public @Nullable VXAccountHolder getCreditor() {
        return null;
    }

    @Override
    public @NotNull VXAccountHolder getDebtor() {
        return debtor;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getImpactOnPremiumMoneySupply() {
        return -amount;
    }

    @Override
    public Reason getReason() {
        return Reason.PREMIUM_BALANCE_RETIRED;
    }
}
