package com.themrsung.mc.economy;

import com.themrsung.mc.economy.property.AccountHolderProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * An entity which can hold a VaultX account.
 */
public interface VXAccountHolder {
    /**
     * Returns the unique identifier of this holder.
     *
     * @return The unique identifier of this holder
     */
    @NotNull UUID getUniqueId();

    /**
     * Returns the name of this holder.
     *
     * @return The name of this holder
     */
    @Nullable String getName();

    /**
     * Returns the type of this holder.
     *
     * @return The type of this holder
     */
    @NotNull AccountHolderType getType();

    /**
     * Returns the balance of this holder.
     *
     * @return The balance of this holder
     */
    double getBalance();

    /**
     * Returns the withdrawable balance of this holder.
     *
     * @return The withdrawable balance of this holder
     */
    double getWithdrawableBalance();

    /**
     * Returns the premium currency balance of this holder.
     *
     * @return The premium currency balance of this holder
     */
    long getPremiumBalance();

    /**
     * Returns the withdrawable premium balance of this holder.
     *
     * @return The withdrawable premium balance of this holder
     */
    long getWithdrawablePremiumBalance();

    /**
     * Returns a {@link Stream stream} of the {@link AccountHolderProperty properties} of this holder.
     *
     * @return A stream of the properties of this holder
     */
    @NotNull Stream<AccountHolderProperty> getProperties();

    /**
     * Returns the property of matching name. This operation is case-sensitive.
     *
     * @param name The name of the property
     * @return The property if found, {@code null} otherwise
     */
    @Nullable AccountHolderProperty getProperty(String name);

    /**
     * Adds the property to this account holder. This operation will fail if there is already a property
     * of the same name.
     *
     * @param property The property to add
     * @return {@code true} if the property was added successfully
     */
    boolean addProperty(AccountHolderProperty property);

    /**
     * Removes the property from this account holder.
     *
     * @param property The property to remove
     * @return {@code true} if the property was removed successfully
     */
    boolean removeProperty(AccountHolderProperty property);

    /**
     * Removes the property from this account holder.
     *
     * @param name The name of the property
     * @return {@code true} if the property was removed successfully
     */
    boolean removeProperty(String name);
}
