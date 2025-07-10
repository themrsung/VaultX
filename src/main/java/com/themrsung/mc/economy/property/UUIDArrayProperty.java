package com.themrsung.mc.economy.property;

import java.util.UUID;

/**
 * Date array property.
 */
public class UUIDArrayProperty extends ArrayProperty<UUID> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    UUIDArrayProperty(String name, UUID[] value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.UUID_ARRAY;
    }
}
