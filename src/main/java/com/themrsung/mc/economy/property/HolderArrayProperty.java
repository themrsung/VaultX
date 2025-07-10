package com.themrsung.mc.economy.property;

import com.themrsung.mc.economy.VXAccountHolder;

/**
 * Holder array property.
 */
public class HolderArrayProperty extends ArrayProperty<VXAccountHolder> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    HolderArrayProperty(String name, VXAccountHolder[] value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.HOLDER_ARRAY;
    }
}
