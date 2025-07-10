package com.themrsung.mc.economy.property;

import java.util.UUID;

/**
 * {@link UUID} property.
 */
public class UUIDProperty extends ObjectProperty<UUID> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    UUIDProperty(String name, UUID value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.UUID;
    }
}
