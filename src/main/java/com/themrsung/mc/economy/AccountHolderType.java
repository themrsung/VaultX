package com.themrsung.mc.economy;

/**
 * Type of {@link VXAccountHolder account holder}.
 */
public enum AccountHolderType {
    /**
     * A player.
     */
    PLAYER,

    /**
     * An institution. (Company, town, bank, whatever...)
     */
    INSTITUTION,

    /**
     * A user or account added by another plugin.
     */
    THIRD_PARTY_PLUGIN,

    /**
     * Inert reference object. This only appears during IO operations.
     */
    INERT_REF;
}
