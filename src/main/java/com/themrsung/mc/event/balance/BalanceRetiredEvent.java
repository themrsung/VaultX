package com.themrsung.mc.event.balance;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Called when balance has been retired.
 */
public class BalanceRetiredEvent extends BalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param debtor The debtor
     * @param amount The amount retired
     */
    public BalanceRetiredEvent(VXAccountHolder debtor, double amount) {
        super();

        this.debtor = Objects.requireNonNull(debtor);
        this.amount = amount;
    }

    protected final VXAccountHolder debtor;
    protected final double amount;

    @Override
    public @Nullable VXAccountHolder getCreditor() {
        return null;
    }

    @Override
    public @NotNull VXAccountHolder getDebtor() {
        return debtor;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getImpactOnMoneySupply() {
        return -amount;
    }

    @Override
    public Reason getReason() {
        return Reason.BALANCE_RETIRED;
    }
}
