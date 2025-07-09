package com.themrsung.mc.exception;

import com.themrsung.mc.economy.EconomyX;
import com.themrsung.mc.economy.VXAccountHolder;

/**
 * Thrown when an unknown implementation of {@link VXAccountHolder} is given to an {@link EconomyX economy}.
 *
 * @see EconomyX
 * @see VXAccountHolder
 */
public class UnknownAccountHolderImplementationException extends Exception {
    /**
     * Creates a new exception.
     *
     * @param economy The economy
     * @param account The account
     */
    public UnknownAccountHolderImplementationException(EconomyX economy, VXAccountHolder account) {
        this.economy = economy;
        this.account = account;
    }

    /**
     * Creates a new exception.
     *
     * @param economy The economy
     * @param account The account
     * @param message The error message
     */
    public UnknownAccountHolderImplementationException(EconomyX economy, VXAccountHolder account, String message) {
        super(message);

        this.economy = economy;
        this.account = account;
    }

    protected final EconomyX economy;
    protected final VXAccountHolder account;

    /**
     * Returns the {@link EconomyX economy} which threw this exception.
     *
     * @return The economy which threw this exception
     */
    public EconomyX getEconomy() {
        return economy;
    }

    /**
     * Returns the account which caused this exception.
     *
     * @return The account which caused this exception
     */
    public VXAccountHolder getAccount() {
        return account;
    }
}
