package com.themrsung.mc.event.premium;

import com.themrsung.mc.economy.VXAccountHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Called when premium balance is transferred between account holders.
 */
public class PremiumBalanceTransferredEvent extends PremiumBalanceModifiedEvent {
    /**
     * Creates a new event.
     *
     * @param sender    The sender
     * @param recipient The recipient
     * @param amount    The amount
     */
    public PremiumBalanceTransferredEvent(VXAccountHolder sender, VXAccountHolder recipient, long amount) {
        super();

        this.sender = Objects.requireNonNull(sender);
        this.recipient = Objects.requireNonNull(recipient);
        this.amount = amount;
    }

    protected final VXAccountHolder sender;
    protected final VXAccountHolder recipient;
    protected final long amount;

    @Override
    public @NotNull VXAccountHolder getCreditor() {
        return recipient;
    }

    @Override
    public @NotNull VXAccountHolder getDebtor() {
        return sender;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public long getImpactOnPremiumMoneySupply() {
        return 0;
    }

    @Override
    public Reason getReason() {
        return Reason.PREMIUM_BALANCE_TRANSFERRED;
    }
}
