package com.themrsung.mc.economy.property;

import com.themrsung.mc.economy.VXAccountHolder;

/**
 * {@link VXAccountHolder} property.
 */
public class HolderProperty extends ObjectProperty<VXAccountHolder> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    HolderProperty(String name, VXAccountHolder value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.HOLDER;
    }
}
