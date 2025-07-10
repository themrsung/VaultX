package com.themrsung.mc.economy.property;

import org.bukkit.OfflinePlayer;

/**
 * Player array property.
 */
public class PlayerArrayProperty extends ArrayProperty<OfflinePlayer> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    PlayerArrayProperty(String name, OfflinePlayer[] value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.PLAYER_ARRAY;
    }
}
