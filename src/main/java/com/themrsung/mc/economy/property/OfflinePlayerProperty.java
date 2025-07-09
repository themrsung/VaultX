package com.themrsung.mc.economy.property;

import org.bukkit.OfflinePlayer;

/**
 * {@link OfflinePlayer} property.
 */
public class OfflinePlayerProperty extends ObjectProperty<OfflinePlayer> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    OfflinePlayerProperty(String name, OfflinePlayer value) {
        super(name, value);
    }
}
