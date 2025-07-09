package com.themrsung.mc.event.balance;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Called when balance is withdrawn by legacy Vault API.
 */
public class BalanceWithdrawnByVaultEvent extends BalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param debtor The creditor
     * @param amount The amount
     */
    public BalanceWithdrawnByVaultEvent(VXAccountHolder debtor, double amount) {
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
        return Reason.BALANCE_WITHDRAWN_BY_VAULT;
    }
}
