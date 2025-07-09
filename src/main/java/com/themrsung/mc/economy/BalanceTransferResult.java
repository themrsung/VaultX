package com.themrsung.mc.economy;

/**
 * The result of a balance transfer attempt in VaultX.
 */
public enum BalanceTransferResult {
    /**
     * Successfully transferred.
     */
    SUCCESS,

    /**
     * Failure due to insufficient funds.
     */
    FAILURE_INSUFFICIENT_FUNDS,

    /**
     * Failure due to an invalid sender.
     */
    FAILURE_INVALID_SENDER,

    /**
     * Failure due to an invalid recipient.
     */
    FAILURE_INVALID_RECIPIENT,

    /**
     * Failure due to an unknown account holder implementation.
     * (Economy cannot modify the balance of either the sender or recipient)
     */
    FAILURE_UNKNOWN_IMPLEMENTATION,

    /**
     * Failure due to an unknown reason.
     */
    FAILURE_OTHER
}
