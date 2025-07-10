package com.themrsung.mc.economy.property;

import java.util.Arrays;

/**
 * {@code double} array property.
 */
public class DoubleArrayProperty extends ArrayProperty<Double> {
    /**
     * Local constructor.
     *
     * @param name  The name of this property
     * @param value The value of this property
     */
    DoubleArrayProperty(String name, double[] value) {
        super(name, Arrays.stream(value).boxed().toArray(Double[]::new));
    }

    @Override
    public AccountHolderPropertyType getType() {
        return AccountHolderPropertyType.DOUBLE_ARRAY;
    }
}
