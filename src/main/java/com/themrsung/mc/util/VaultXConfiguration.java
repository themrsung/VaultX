package com.themrsung.mc.util;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for VaultX.
 */
public class VaultXConfiguration implements ConfigurationSerializable {
    /**
     * Default constructor.
     */
    public VaultXConfiguration() {
        this(6000, 100000, 5);
    }

    /**
     * Full constructor.
     *
     * @param autoSaveInterval       The autosave interval in ticks
     * @param startingBalance        The starting balance
     * @param startingPremiumBalance The starting premium balance
     */
    public VaultXConfiguration(
            long autoSaveInterval,
            double startingBalance,
            long startingPremiumBalance
    ) {
        this.autoSaveInterval = autoSaveInterval;
        this.startingBalance = startingBalance;
        this.startingPremiumBalance = startingPremiumBalance;
    }

    private final long autoSaveInterval;
    private final double startingBalance;
    private final long startingPremiumBalance;

    /**
     * Returns the autosave interval in ticks.
     *
     * @return The autosave interval
     */
    public long getAutoSaveInterval() {
        return autoSaveInterval;
    }

    /**
     * Returns the starting balance.
     *
     * @return The starting balance
     */
    public double getStartingBalance() {
        return startingBalance;
    }

    /**
     * Returns the starting premium balance.
     *
     * @return The starting premium balance
     */
    public long getStartingPremiumBalance() {
        return startingPremiumBalance;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();

        map.put("autoSaveInterval", autoSaveInterval);
        map.put("startingBalance", startingBalance);
        map.put("startingPremiumBalance", startingPremiumBalance);

        return Map.copyOf(map);
    }

    public static VaultXConfiguration deserialize(Map<String, Object> args) {
        return new VaultXConfiguration(
                (Long) args.get("autoSaveInterval"),
                (Double) args.get("startingBalance"),
                (Long) args.get("startingPremiumBalance")
        );
    }
}
