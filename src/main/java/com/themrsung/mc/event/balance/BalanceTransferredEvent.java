package com.themrsung.mc.event.balance;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called when balance is transferred between account holders.
 */
public class BalanceTransferredEvent extends BalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param sender    The sender
     * @param recipient The recipient
     * @param amount    The amount
     */
    public BalanceTransferredEvent(VXAccountHolder sender, VXAccountHolder recipient, double amount) {
        super();

        this.sender = Objects.requireNonNull(sender);
        this.recipient = Objects.requireNonNull(recipient);
        this.amount = amount;
    }

    protected final VXAccountHolder sender;
    protected final VXAccountHolder recipient;
    protected final double amount;

    @Override
    public @NotNull VXAccountHolder getCreditor() {
        return recipient;
    }

    @Override
    public @NotNull VXAccountHolder getDebtor() {
        return sender;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public double getImpactOnMoneySupply() {
        return 0;
    }

    @Override
    public Reason getReason() {
        return Reason.BALANCE_TRANSFERRED;
    }
}
