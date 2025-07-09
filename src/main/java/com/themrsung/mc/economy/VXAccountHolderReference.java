package com.themrsung.mc.economy;

import com.themrsung.mc.economy.property.AccountHolderProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.stream.Stream;

/**
 * Inert reference for an {@link VXAccountHolder account holder}. This should only be used during IO operations.
 */
public class VXAccountHolderReference implements VXAccountHolder {
    public VXAccountHolderReference(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    private final UUID uniqueId;

    @Override
    public @NotNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @NotNull AccountHolderType getType() {
        return AccountHolderType.INERT_REF;
    }

    // All methods below do not work

    @Override
    public @Nullable String getName() {
        return "";
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public double getWithdrawableBalance() {
        return 0;
    }

    @Override
    public long getPremiumBalance() {
        return 0;
    }

    @Override
    public long getWithdrawablePremiumBalance() {
        return 0;
    }

    @Override
    public @NotNull Stream<AccountHolderProperty<?>> getProperties() {
        return Stream.empty();
    }

    @Override
    public @Nullable AccountHolderProperty<?> getProperty(String name) {
        return null;
    }

    @Override
    public boolean addProperty(AccountHolderProperty<?> property) {
        return false;
    }

    @Override
    public boolean removeProperty(AccountHolderProperty<?> property) {
        return false;
    }

    @Override
    public boolean removeProperty(String name) {
        return false;
    }
}
