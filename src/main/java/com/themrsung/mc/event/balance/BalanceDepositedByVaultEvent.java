package com.themrsung.mc.event.balance;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Called when balance is deposited by legacy Vault API.
 */
public class BalanceDepositedByVaultEvent extends BalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param creditor The creditor
     * @param amount   The amount
     */
    public BalanceDepositedByVaultEvent(VXAccountHolder creditor, double amount) {
        super();

        this.creditor = Objects.requireNonNull(creditor);
        this.amount = amount;
    }

    protected final VXAccountHolder creditor;
    protected final double amount;

    @Override
    public @NotNull VXAccountHolder getCreditor() {
        return creditor;
    }

    @Override
    public @Nullable VXAccountHolder getDebtor() {
        return null;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getImpactOnMoneySupply() {
        return amount;
    }

    @Override
    public Reason getReason() {
        return Reason.BALANCE_DEPOSITED_BY_VAULT;
    }
}
