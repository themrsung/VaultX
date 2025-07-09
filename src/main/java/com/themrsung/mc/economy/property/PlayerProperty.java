package com.themrsung.mc.economy.property;

import org.bukkit.OfflinePlayer;

/**
 * {@link OfflinePlayer} property.
 */
public class PlayerProperty extends ObjectProperty<OfflinePlayer> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    PlayerProperty(String name, OfflinePlayer value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.PLAYER;
    }
}
