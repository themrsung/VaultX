package com.themrsung.mc.economy;

import com.themrsung.mc.economy.property.AccountHolderProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Abstract class for account holder implementations.
 */
public abstract class VXAbstractAccountHolder implements VXAccountHolder {
    public VXAbstractAccountHolder() {
        this.properties = new HashSet<>();
    }

    protected final Set<AccountHolderProperty> properties;

    @Override
    public @NotNull Stream<AccountHolderProperty> getProperties() {
        return properties.stream();
    }

    @Override
    public @Nullable AccountHolderProperty getProperty(String name) {
        return properties.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean addProperty(AccountHolderProperty property) {
        if (properties.stream().anyMatch(p -> Objects.equals(p.getName(), property.getName()))) {
            return false;
        }

        return properties.add(property);
    }

    @Override
    public boolean removeProperty(AccountHolderProperty property) {
        return properties.remove(property);
    }

    @Override
    public boolean removeProperty(String name) {
        return properties.removeIf(p -> Objects.equals(p.getName(), name));
    }
}
