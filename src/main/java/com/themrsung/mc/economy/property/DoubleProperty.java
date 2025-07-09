package com.themrsung.mc.economy.property;

/**
 * {@code double} property.
 */
public class DoubleProperty extends ObjectProperty<Double> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    DoubleProperty(String name, double value) {
        super(name, value);
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.DOUBLE;
    }
}
