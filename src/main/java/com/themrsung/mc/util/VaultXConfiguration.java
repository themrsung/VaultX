package com.themrsung.mc.util;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for VaultX.
 */
public class VaultXConfiguration implements ConfigurationSerializable {
    public VaultXConfiguration(long autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    private long autoSaveInterval = 5 * 60 * 20;

    @Override
    public @NotNull Map<String, Object> serialize() {
        var map = new HashMap<String, Object>();

        map.put("autoSaveInterval", autoSaveInterval);

        return Map.copyOf(map);
    }

    public static VaultXConfiguration deserialize(Map<String, Object> args) {
        return new VaultXConfiguration(
                (Long) args.get("autoSaveInterval")
        );
    }
}
