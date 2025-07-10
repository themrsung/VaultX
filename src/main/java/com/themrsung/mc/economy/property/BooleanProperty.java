package com.themrsung.mc.economy.property;

/**
 * {@code boolean} property.
 */
public class BooleanProperty extends ObjectProperty<Boolean> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    BooleanProperty(String name, boolean value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.BOOLEAN;
    }
}
